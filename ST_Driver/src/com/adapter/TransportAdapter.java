package com.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.model.Transport;
import com.ry.st.driver.R;

public class TransportAdapter extends BaseAdapter {
	LayoutInflater inflater;
	int COUNT = 10;
	List<Transport> list;

	public TransportAdapter(Context context, List<Transport> list) {
		inflater = LayoutInflater.from(context);
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub

		Transport t = list.get(position);
		ViewHolder holder = null;
		if (view == null) {
			view = inflater.inflate(R.layout.transport_item, null);
			holder = new ViewHolder();
			holder.positionTx = (TextView) view.findViewById(R.id.position);
			holder.pubtimeTx = (TextView) view.findViewById(R.id.pubtime);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.positionTx.setText("运政信息:" + t.getPosition());
		holder.pubtimeTx.setText("发布时间:" + t.getPubtime());

		return view;
	}

	class ViewHolder {
		public TextView positionTx;
		public TextView pubtimeTx;
	}
}
