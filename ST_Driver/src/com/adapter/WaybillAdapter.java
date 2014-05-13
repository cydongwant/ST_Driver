package com.adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.constant.Constant;
import com.model.Waybill;
import com.ry.st.driver.R;
import com.util.Connect;

public class WaybillAdapter extends BaseAdapter {
	LayoutInflater inflater;
	int COUNT = 10;
	List<Waybill> list;
	String URI_Send = "/driver_apps/:uuid/way_bills/:way_bill_id/out";
	SharedPreferences sp;
	TextView currentView;

	public WaybillAdapter(Context context, List<Waybill> list) {
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(context);
		this.list = list;
		sp = context.getSharedPreferences("app", Context.MODE_PRIVATE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		Waybill w = list.get(position);
		ViewHoldler holder = null;
		if (view == null) {
			holder = new ViewHoldler();
			view = inflater.inflate(R.layout.waybill_item, null);
			holder.codeTx = (TextView) view.findViewById(R.id.code);
			holder.typeTx = (TextView) view.findViewById(R.id.type);
			holder.clientTx = (TextView) view.findViewById(R.id.client);
			holder.tellTx = (TextView) view.findViewById(R.id.tell);
			holder.timeTx = (TextView) view.findViewById(R.id.time);
			holder.placeTx = (TextView) view.findViewById(R.id.place);
			holder.statuTx = (TextView) view.findViewById(R.id.statu_send);
			view.setTag(holder);
		} else {
			holder = (ViewHoldler) view.getTag();
		}
		holder.codeTx.setText(w.getWaybilCode());
		holder.typeTx.setText(w.getBussinessType());
		holder.clientTx.setText(w.getClientName());
		holder.tellTx.setText(w.getTellNum());
		holder.timeTx.setText(w.getUpTime());
		holder.placeTx.setText(w.getUpPlace());
		holder.statuTx.setOnClickListener(ocl);
		holder.statuTx.setTag(w);
		return view;
	}

	class ViewHoldler {
		TextView codeTx;
		TextView typeTx;
		TextView clientTx;
		TextView tellTx;
		TextView timeTx;
		TextView placeTx;
		TextView statuTx;

	}

	private View.OnClickListener ocl = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			currentView = (TextView) v;
			Waybill b = (Waybill) v.getTag();
			new SendTask().execute(b);

		}
	};

	public class SendTask extends AsyncTask<Waybill, Void, String> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			Constant.uuid = sp.getString("uuid", "");

		}

		@Override
		protected String doInBackground(Waybill... params) {
			// TODO Auto-generated method stub
			String result = null;
			ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
			HttpClient client = Connect.getHttpClient();
			String request = Constant.BASE_URL
					+ Constant.Statu_URI.get(Constant.Statu_Next.get(""));
			param.add(new BasicNameValuePair("out_code", "1234"));
			HttpPost post = new HttpPost(request
					.replace(":uuid", Constant.uuid).replace(":way_bill_id",
							params[0].getId().toString()));
			try {
				post.setEntity(new UrlEncodedFormEntity(param, "UTF-8"));
				HttpResponse response = client.execute(post);
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
				try {
					JSONObject obj = new JSONObject(result);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Constant.CURRENT_STATU = "out";
				currentView.setText("");

			}
		}
	}
}
