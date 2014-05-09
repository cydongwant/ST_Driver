package com.fragment;

import com.constant.Constant;
import com.ry.st.driver.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BFragment extends Fragment {
	String TAG = "B";
       int tag = 1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.pager_item, null);
		TextView t = (TextView) v.findViewById(R.id.item_title);
		t.setText("B");
		return v;
	}


	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (Constant.PAGE_INDEX == tag) {
//			loadData();
		}
		super.onViewCreated(view, savedInstanceState);
	}
	
	private void loadData() {
		// TODO Auto-generated method stub
		Log.d("cyd",TAG + "loadData");
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			loadData();
		} else {
			// 不可见时不执行操作
		}

	}
}
