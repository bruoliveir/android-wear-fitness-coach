package com.example.weartest;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

final class WearableListViewAdapter extends WearableListView.Adapter {
    private String[] mItems;
    private final LayoutInflater mInflater;

    public WearableListViewAdapter(Context context, String[] items) {
        mInflater = LayoutInflater.from(context);
        mItems = items;
    }

    public static class ItemViewHolder extends WearableListView.ViewHolder {
        private TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.settings_wearablelistview_item_textview);
        }
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.activity_settings_wearablelistview_item, null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

        TextView textView = itemViewHolder.textView;
        textView.setText(mItems[position]);

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mItems.length;
    }
}
