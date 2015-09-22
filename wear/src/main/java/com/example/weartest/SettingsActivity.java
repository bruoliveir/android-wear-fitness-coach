package com.example.weartest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.GridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingsActivity extends Activity {

    public static final String NUMBER_OF_SETS = "NUMBER_OF_SETS";
    public static final String REST_TIME = "REST_TIME";
    private static final String[] ARRAY_NUMBER_OF_SETS = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private static final String[] ARRAY_REST_TIME = { "5", "10", "15", "20", "25", "30", "35", "40", "45", "50",
            "50", "55", "60", "65", "70", "75", "80", "85", "90"};

    private Context mContext;

    private GridViewPager mGridViewPager;

    private int mSelectedNumberOfSets;
    private int mSelectedRestTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mContext = this;

        mGridViewPager = (GridViewPager) findViewById(R.id.settings_gridviewpager);
        mGridViewPager.setAdapter(new SettingsGridPagerAdapter());
    }

    public class SettingsGridPagerAdapter extends GridPagerAdapter {

        @Override
        public int getRowCount() {
            return 1;
        }

        @Override
        public int getColumnCount(int i) {
            return 3;
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup, int row, final int column) {

            View view = null;

            switch (column) {
                case 0:
                    view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_settings_gridviewpager_item_sets, viewGroup, false);

                    WearableListView wearableListViewSets = (WearableListView) view.findViewById(R.id.settings_gridviewpager_item_wearablelistview_sets);
                    wearableListViewSets.setGreedyTouchMode(true);
                    wearableListViewSets.setAdapter(new WearableListViewAdapter(mContext, ARRAY_NUMBER_OF_SETS));
                    wearableListViewSets.setClickListener(new WearableListView.ClickListener() {
                        @Override
                        public void onClick(WearableListView.ViewHolder viewHolder) {
                            mGridViewPager.setCurrentItem(0, column + 1, true);
                        }

                        @Override
                        public void onTopEmptyRegionClick() {

                        }
                    });
                    wearableListViewSets.addOnCentralPositionChangedListener(new WearableListView.OnCentralPositionChangedListener() {
                        @Override
                        public void onCentralPositionChanged(int i) {
                            mSelectedNumberOfSets = i;
                        }
                    });
                    wearableListViewSets.scrollToPosition(Utils.getSharedPreferences(mContext)
                            .getInt(getString(R.string.shared_preferences_settings_number_of_sets), (ARRAY_NUMBER_OF_SETS.length / 2) - 1));
                    viewGroup.addView(view);
                    break;
                case 1:
                    view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_settings_gridviewpager_item_rest, viewGroup, false);

                    WearableListView wearableListViewRest = (WearableListView) view.findViewById(R.id.settings_gridviewpager_item_wearablelistview_rest);
                    wearableListViewRest.setGreedyTouchMode(true);
                    wearableListViewRest.setAdapter(new WearableListViewAdapter(mContext, ARRAY_REST_TIME));
                    wearableListViewRest.setClickListener(new WearableListView.ClickListener() {
                        @Override
                        public void onClick(WearableListView.ViewHolder viewHolder) {
                            mGridViewPager.setCurrentItem(0, column + 1, true);
                        }

                        @Override
                        public void onTopEmptyRegionClick() {

                        }
                    });
                    wearableListViewRest.addOnCentralPositionChangedListener(new WearableListView.OnCentralPositionChangedListener() {
                        @Override
                        public void onCentralPositionChanged(int i) {
                            mSelectedRestTime = i;
                        }
                    });
                    wearableListViewRest.scrollToPosition(Utils.getSharedPreferences(mContext)
                            .getInt(getString(R.string.shared_preferences_settings_rest_time), (ARRAY_NUMBER_OF_SETS.length / 2) - 1));
                    viewGroup.addView(view);
                    break;
                case 2:
                    view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_settings_gridviewpager_item_done, viewGroup, false);
                    CircledImageView circledImageView = (CircledImageView) view.findViewById(R.id.settings_circledimageview_done);
                    circledImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Utils.getSharedPreferencesEditor(mContext)
                                    .putInt(getString(R.string.shared_preferences_settings_number_of_sets), mSelectedNumberOfSets)
                                    .putInt(getString(R.string.shared_preferences_settings_rest_time), mSelectedRestTime)
                                    .commit();

                            Intent intent = new Intent(mContext, MainActivity.class);
                            intent.putExtra(NUMBER_OF_SETS, ARRAY_NUMBER_OF_SETS[mSelectedNumberOfSets]);
                            intent.putExtra(REST_TIME, ARRAY_REST_TIME[mSelectedRestTime]);
                            startActivity(intent);

                            finish();
                        }
                    });
                    viewGroup.addView(view);
                    break;
            }
            return view;
        }

        @Override
        public void destroyItem(ViewGroup viewGroup, int i, int i1, Object o) {
            viewGroup.removeView((View) o);
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }
    }
}
