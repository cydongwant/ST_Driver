package com.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.adapter.TransportAdapter;
import com.constant.Constant;
import com.model.Transport;
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

public class TransportFrag extends Fragment {
	ListView listView;
	TransportAdapter adapter;
	String URI_Transport = "/driver_apps/:uuid/transportation";
	SharedPreferences sp;
	boolean loaded;
	List<Transport> list = new ArrayList<Transport>();

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
		View v = inflater.inflate(R.layout.transport, null);
		listView = (ListView) v.findViewById(R.id.list);
		adapter = new TransportAdapter(getActivity(), list);
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
			if (Network.isNetActive(getActivity())) {
				new LoadDataTask().execute();
			}
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
			String request = Constant.BASE_URL + URI_Transport;
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
			if (result != null) {
				Log.d("cyd", result);
				loaded = true;
				try {
					JSONArray array = new JSONArray(result);
					for (int i = 0; i < array.length(); i++) {
						Transport t = new Transport();
						JSONObject obj = array.getJSONObject(i);
						t.setId(obj.getJSONObject("transportation_info")
								.getInt("id"));
						t.setPosition(obj.getJSONObject("transportation_info")
								.getString("position"));
						t.setPubtime(obj.getJSONObject("transportation_info")
								.getString("updated_at"));
						list.add(t);
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
