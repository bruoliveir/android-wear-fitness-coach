package com.example.weartest;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends WearableActivity {

    private BoxInsetLayout mContainerView;
    private TextView mTextViewClock;
    private TextView mTextViewSets;
    private Button mButtonFinishSet;

    private CountDownTimer mCountDownTimer;
    private int mCurrentSet = 0;
    private int mTotalSets = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();

        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        mTextViewClock = (TextView) findViewById(R.id.clock);
        mTextViewSets = (TextView) findViewById(R.id.sets);
        mButtonFinishSet = (Button) findViewById(R.id.finish_set);

        mTextViewClock.setText(getString(R.string.clock_ready));
        mTextViewSets.setText(mCurrentSet + "/" + mTotalSets);

        mButtonFinishSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCountDownTimer != null) mCountDownTimer.cancel();

                mCurrentSet++;
                mTextViewSets.setText(mCurrentSet + "/" + mTotalSets);
                if (mCurrentSet >= mTotalSets) {
                    mTextViewClock.setText(getString(R.string.clock_done));
                    return;
                }

                long start = 15000;
                long step = 1000;

                mCountDownTimer = new CountDownTimer(start + 1000, step) {
                    @Override
                    public void onTick(long l) {
                        mTextViewClock.setText("" + (l / 1000));
                    }

                    @Override
                    public void onFinish() {
                        mTextViewClock.setText(getString(R.string.clock_go));

                        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                        long[] vibrationPattern = {0, 500, 50, 500, 50, 500};
                        final int indexInPatternToRepeat = -1;
                        vibrator.vibrate(vibrationPattern, indexInPatternToRepeat);
                    }
                };

                mCountDownTimer.start();
            }
        });
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
