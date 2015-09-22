package com.example.weartest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends WearableActivity {

    private Context mContext;

    private BoxInsetLayout mContainerView;
    private TextView mTextViewClock;
    private TextView mTextViewSets;
    private Button mButtonFinishSet;

    private CountDownTimer mCountDownTimer;
    private static final long mCountDownStep = 1000;
    private long mRestTime;
    private int mCurrentSet = 0;
    private int mTotalSets;

    private static final long[] mVibrationPatternFinishSet = {0, 400, 100, 200, 100, 200, 100, 200};
    private static final long[] mVibrationPatternReminder = {0, 400, 50, 200};
    private static final int mVibrationReminderDelay = 45000;
    private static final int mVibrationReminderInterval = 10000;
    private static final int mIndexInPatternToRepeat = -1;
    private static final int mTimerRepeatCount = 3;

    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();

        mContext = this;

        Intent intent = getIntent();
        String numberOfSets = intent.getStringExtra(SettingsActivity.NUMBER_OF_SETS);
        String restTime = intent.getStringExtra(SettingsActivity.REST_TIME);
        mTotalSets = Integer.parseInt(numberOfSets);
        mRestTime = Integer.parseInt(restTime) * 1000;

        mContainerView = (BoxInsetLayout) findViewById(R.id.main_boxinsetlayout);
        mTextViewClock = (TextView) findViewById(R.id.main_textview_clock);
        mTextViewSets = (TextView) findViewById(R.id.sets);
        mButtonFinishSet = (Button) findViewById(R.id.main_button_finish);

        mTextViewClock.setText(getString(R.string.main_textview_clock_ready));
        mTextViewSets.setText(mCurrentSet + "/" + mTotalSets);

        mButtonFinishSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCountDownTimer != null) mCountDownTimer.cancel();
                if (mTimer != null) mTimer.cancel();

                mCurrentSet++;
                mTextViewSets.setText(mCurrentSet + "/" + mTotalSets);
                if (mCurrentSet >= mTotalSets) {
                    mTextViewClock.setText(getString(R.string.main_textview_clock_done));
                    return;
                }

                mCountDownTimer = new CountDownTimer(mRestTime + 1000, mCountDownStep) {
                    @Override
                    public void onTick(long l) {
                        mTextViewClock.setText(String.valueOf(l / 1000));
                    }

                    @Override
                    public void onFinish() {
                        mTextViewClock.setText(getString(R.string.main_textview_clock_go));

                        Utils.vibrate(mContext, mVibrationPatternFinishSet, mIndexInPatternToRepeat);

                        Utils.turnScreenOn(mContext);

                        mTimer = new Timer();
                        mTimer.scheduleAtFixedRate(new TimerTask() {
                            int repeatCount = mTimerRepeatCount;

                            @Override
                            public void run() {
                                if (repeatCount > 0) {
                                    Utils.vibrate(mContext, mVibrationPatternReminder, mIndexInPatternToRepeat);
                                    repeatCount--;
                                } else {
                                    this.cancel();
                                }
                            }
                        }, mVibrationReminderDelay, mVibrationReminderInterval);
                    }
                };

                mCountDownTimer.start();
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mCountDownTimer != null) mCountDownTimer.cancel();
        if (mTimer != null) mTimer.cancel();

        super.onDestroy();
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay() {
        if (isAmbient()) {
            mContainerView.setBackgroundColor(getResources().getColor(android.R.color.black));
            mTextViewClock.setTextColor(getResources().getColor(android.R.color.white));
            mTextViewSets.setTextColor(getResources().getColor(android.R.color.white));
            mButtonFinishSet.setVisibility(View.GONE);

            //mTextViewClock.setText();
            mTextViewSets.setText(mCurrentSet + "/" + mTotalSets);
        } else {
            mContainerView.setBackgroundColor(getResources().getColor(R.color.main_background));
            mTextViewClock.setTextColor(getResources().getColor(android.R.color.primary_text_dark));
            mTextViewSets.setTextColor(getResources().getColor(android.R.color.secondary_text_dark));
            mButtonFinishSet.setVisibility(View.VISIBLE);
        }
    }
}
