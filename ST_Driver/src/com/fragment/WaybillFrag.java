package com.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.adapter.TransportAdapter;
import com.adapter.WaybillAdapter;
import com.constant.Constant;
import com.model.Waybill;
import com.ry.st.driver.R;
import com.util.Connect;
import com.util.Network;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class WaybillFrag extends Fragment {
	ListView listView;
	WaybillAdapter adapter;
	String URI_Waybill = "/driver_apps/:uuid/way_bills";
	SharedPreferences sp;
	boolean loaded = false;
	List<Waybill> list = new ArrayList<Waybill>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp = getActivity().getSharedPreferences("app", Context.MODE_PRIVATE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.waybill, null);
		listView = (ListView) v.findViewById(R.id.list);
		adapter = new WaybillAdapter(getActivity(), list);
		listView.setAdapter(adapter);
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			if (!loaded)
				if (Network.isNetActive(getActivity()))
					new LoadDataTask().execute();
		}

	}

	public class LoadDataTask extends AsyncTask<Void, Void, String> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			Constant.uuid = sp.getString("uuid", "");
		}

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String result = null;
			HttpClient client = Connect.getHttpClient();
			String request = Constant.BASE_URL + URI_Waybill;
			HttpGet get = new HttpGet(request.replace(":uuid", Constant.uuid));
			try {
				HttpResponse response = client.execute(get);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity entity = response.getEntity();
					result = EntityUtils.toString(entity);
				}

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.d("cyd", "post");
			if (result != null) {
				loaded = true;
				// Log.d("cyd", result);
				try {
					JSONArray array = new JSONArray(result);
					for (int i = 0; i < array.length(); i++) {
						Waybill w = new Waybill();
						JSONObject obj = array.getJSONObject(i);
						w.setId(obj.getInt("id"));
						w.setWaybilCode(obj.getString("way_number"));
						w.setBussinessType(obj.getString("service_type"));
						w.setClientName(obj.getString("use_name"));
						w.setTellNum(obj.getString("use_phone"));
						w.setUpTime(obj.getString("up_time"));
						w.setUpPlace(obj.getString("up_place"));
						list.add(w);
					}
					adapter.notifyDataSetChanged();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}
}
