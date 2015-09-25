package com.example.weartest;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WearableListViewItem extends LinearLayout implements WearableListView.OnCenterProximityListener {

    private final static int TEXT_SIZE_ANIMATION_DURATION = 100;

    private TextView mTextView;

    private final int mCenterPositionTextColor;
    private final int mNonCenterPositionTextColor;

    private final float mTextSizeAnimationRatio;

    public WearableListViewItem(Context context) {
        this(context, null);
    }

    public WearableListViewItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WearableListViewItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mCenterPositionTextColor = getResources().getColor(android.R.color.black);
        mNonCenterPositionTextColor = getResources().getColor(android.R.color.darker_gray);

        int mCenterPositionTextSize = 44;
        int mNonCenterPositionTextSize = 24;
        mTextSizeAnimationRatio = (float) mCenterPositionTextSize / mNonCenterPositionTextSize;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mTextView = (TextView) findViewById(R.id.settings_wearablelistview_item_textview);
    }

    @Override
    public void onCenterPosition(boolean animate) {
        mTextView.animate().scaleX(mTextSizeAnimationRatio).scaleY(mTextSizeAnimationRatio).setDuration(TEXT_SIZE_ANIMATION_DURATION);
        mTextView.setTextColor(mCenterPositionTextColor);
    }

    @Override
    public void onNonCenterPosition(boolean animate) {
        mTextView.animate().scaleX(1f).scaleY(1f).setDuration(50);
        mTextView.setTextColor(mNonCenterPositionTextColor);
    }
}