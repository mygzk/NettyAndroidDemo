package com.gzk.netty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class RecordAdapter extends BaseAdapter {
    private List<RecordBean> mDatas;
    private LayoutInflater mInflater;

    public RecordAdapter(Context context, List<RecordBean> data) {
        mDatas = data;
        mInflater = LayoutInflater.from(context);
    }

    public void addData(RecordBean bean){
        mDatas.add(bean);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HoldView holdView;
        if (convertView == null) {
            holdView = new HoldView();
            convertView = mInflater.inflate(R.layout.list_item_record, null);
            holdView.tvClient = convertView.findViewById(R.id.item_client);
            holdView.tvServer = convertView.findViewById(R.id.item_server);
            convertView.setTag(holdView);
        } else {
            holdView = (HoldView) convertView.getTag();
        }
        holdView.tvClient.setText(mDatas.get(position).res);
        holdView.tvServer.setText(mDatas.get(position).reply);
        return convertView;
    }

    static class HoldView {
        TextView tvClient;
        TextView tvServer;
    }
}
