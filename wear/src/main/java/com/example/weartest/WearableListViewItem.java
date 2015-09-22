package com.example.weartest;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WearableListViewItem extends LinearLayout implements WearableListView.OnCenterProximityListener {

    private TextView mTextView;

    private final int mCenterPositionTextColor;
    private final int mNonCenterPositionTextColor;

    private final int mCenterPositionTextSize;
    private final int mNonCenterPositionTextSize;

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

        mCenterPositionTextSize = 44;
        mNonCenterPositionTextSize = 24;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mTextView = (TextView) findViewById(R.id.settings_wearablelistview_item_textview);
    }

    @Override
    public void onCenterPosition(boolean animate) {
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mCenterPositionTextSize);
        mTextView.setTextColor(mCenterPositionTextColor);
    }

    @Override
    public void onNonCenterPosition(boolean animate) {
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mNonCenterPositionTextSize);
        mTextView.setTextColor(mNonCenterPositionTextColor);
    }
}