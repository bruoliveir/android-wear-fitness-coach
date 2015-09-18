package com.example.weartest;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

final class SetsAdapter extends WearableListView.Adapter {
    private String[] mItems;
    private final Context mContext;
    private final LayoutInflater mInflater;

    public SetsAdapter(Context context, String[] items) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mItems = items;
    }

    public static class ItemViewHolder extends WearableListView.ViewHolder {
        private TextView textViewNumber;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textViewNumber = (TextView) itemView.findViewById(R.id.number);
        }
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.list_item_sets, null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        ItemViewHolder itemHolder = (ItemViewHolder) holder;

        TextView textViewNumber = itemHolder.textViewNumber;
        textViewNumber.setText(mItems[position]);

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mItems.length;
    }
}
