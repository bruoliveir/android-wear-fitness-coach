package com.example.weartest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;

public class SetsActivity extends Activity implements WearableListView.ClickListener {

    public static final String NUMBER_OF_SETS = "NUMBER_OF_SETS";
    private static final String[] elements = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);

        mContext = this;

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                WearableListView listView = (WearableListView) findViewById(R.id.wearable_list);
                listView.setAdapter(new SetsAdapter(mContext, elements));
                listView.setClickListener((WearableListView.ClickListener) mContext);
            }
        });
    }

    @Override
    public void onClick(WearableListView.ViewHolder v) {
        Integer tag = (Integer) v.itemView.getTag();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(NUMBER_OF_SETS, elements[tag]);
        startActivity(intent);

        finish();
    }

    @Override
    public void onTopEmptyRegionClick() {
    }

}
