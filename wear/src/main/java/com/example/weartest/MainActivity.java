package com.example.weartest;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.text.method.CharacterPickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends WearableActivity {

    private BoxInsetLayout mContainerView;
    private TextView mTextViewClock;
    private TextView mTextViewSets;
    private Button mButtonComplete;

    private CountDownTimer mCountDownTimer;
    private int mCurrentSet = 0;
    private int mTotalSets = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();

        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        mTextViewClock = (TextView) findViewById(R.id.clock);
        mTextViewSets = (TextView) findViewById(R.id.sets);
        mButtonComplete = (Button) findViewById(R.id.complete);

        mButtonComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mCurrentSet++;
                if (mCurrentSet >= 10) mTextViewClock.setText("ALL DONE!");
                mTextViewSets.setText(mCurrentSet + "/" + mTotalSets);

                long start = 10000;
                long step = 1000;

                mCountDownTimer = new CountDownTimer(start + 1000, step) {
                    @Override
                    public void onTick(long l) {
                        mTextViewClock.setText("" + (l / 1000));
                    }

                    @Override
                    public void onFinish() {
                        mTextViewClock.setText("GO GO GO");

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

            mTextViewClock.setText(getString(R.string.clock));
            mTextViewSets.setText(getString(R.string.sets));
        } else {
            mContainerView.setBackgroundColor(getResources().getColor(R.color.main_background));
            mTextViewClock.setTextColor(getResources().getColor(android.R.color.primary_text_dark));
            mTextViewSets.setTextColor(getResources().getColor(android.R.color.secondary_text_dark));
        }
    }
}
