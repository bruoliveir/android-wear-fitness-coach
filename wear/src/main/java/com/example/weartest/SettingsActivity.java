package com.example.weartest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SettingsActivity extends Activity {

    public static final String NUMBER_OF_SETS = "NUMBER_OF_SETS";
    public static final String REST_TIME = "REST_TIME";
    private static final String[] ARRAY_NUMBER_OF_SETS = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private static final String[] ARRAY_REST_TIME = { "5", "10", "15", "20", "25", "30", "35", "40", "45", "50",
            "50", "55", "60", "65", "70", "75", "80", "85", "90"};

    private Context mContext;

    private ViewPager mViewPager;

    private int mSelectedNumberOfSets;
    private int mSelectedRestTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mContext = this;

        mViewPager = (ViewPager) findViewById(R.id.settings_viewpager);
        mViewPager.setAdapter(new SettingsPagerAdapter());
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.settings_viewpager_page_margin));
        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                final float normalizedPosition = Math.abs(Math.abs(position) - 1);
                page.setScaleX(normalizedPosition / 2 + 0.5f);
                page.setScaleY(normalizedPosition / 2 + 0.5f);
            }
        });
    }

    public class SettingsPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            View view = null;

            TextView textView;
            WearableListView wearableListView;
            CircledImageView circledImageView;

            switch (position) {
                case 0:
                    view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_settings_viewpager_page_list, container, false);

                    textView = (TextView) view.findViewById(R.id.settings_viewpager_page_textview);
                    textView.setText(getString(R.string.settings_gridviewpager_item_textview_sets));

                    wearableListView = (WearableListView) view.findViewById(R.id.settings_viewpager_page_wearablelistview);
                    wearableListView.setGreedyTouchMode(true);
                    wearableListView.setAdapter(new WearableListViewAdapter(mContext, ARRAY_NUMBER_OF_SETS));
                    wearableListView.setClickListener(new WearableListView.ClickListener() {
                        @Override
                        public void onClick(WearableListView.ViewHolder viewHolder) {
                            mViewPager.setCurrentItem(position + 1, true);
                        }

                        @Override
                        public void onTopEmptyRegionClick() {

                        }
                    });
                    wearableListView.addOnCentralPositionChangedListener(new WearableListView.OnCentralPositionChangedListener() {
                        @Override
                        public void onCentralPositionChanged(int i) {
                            mSelectedNumberOfSets = i;
                        }
                    });
                    wearableListView.scrollToPosition(Utils.getSharedPreferences(mContext)
                            .getInt(getString(R.string.shared_preferences_settings_number_of_sets), (ARRAY_NUMBER_OF_SETS.length / 2) - 1));
                    container.addView(view);
                    break;
                case 1:
                    view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_settings_viewpager_page_list, container, false);

                    textView = (TextView) view.findViewById(R.id.settings_viewpager_page_textview);
                    textView.setText(getString(R.string.settings_gridviewpager_item_textview_rest));

                    wearableListView = (WearableListView) view.findViewById(R.id.settings_viewpager_page_wearablelistview);
                    wearableListView.setGreedyTouchMode(true);
                    wearableListView.setAdapter(new WearableListViewAdapter(mContext, ARRAY_REST_TIME));
                    wearableListView.setClickListener(new WearableListView.ClickListener() {
                        @Override
                        public void onClick(WearableListView.ViewHolder viewHolder) {
                                mViewPager.setCurrentItem(position + 1, true);
                        }

                        @Override
                        public void onTopEmptyRegionClick() {

                        }
                    });
                    wearableListView.addOnCentralPositionChangedListener(new WearableListView.OnCentralPositionChangedListener() {
                        @Override
                        public void onCentralPositionChanged(int i) {
                            mSelectedRestTime = i;
                        }
                    });
                    wearableListView.scrollToPosition(Utils.getSharedPreferences(mContext)
                            .getInt(getString(R.string.shared_preferences_settings_rest_time), (ARRAY_NUMBER_OF_SETS.length / 2) - 1));
                    container.addView(view);
                    break;
                case 2:
                    view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_settings_viewpager_page_action, container, false);

                    circledImageView = (CircledImageView) view.findViewById(R.id.settings_viewpager_page_circledimageview);
                    circledImageView.setImageResource(R.mipmap.ic_arrow_forward);
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
                    container.addView(view);
                    break;
            }
            return view;
        }
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
