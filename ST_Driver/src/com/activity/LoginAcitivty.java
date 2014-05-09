package com.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.constant.Constant;
import com.ry.st.driver.R;
import com.util.Connect;

public class LoginAcitivty extends Activity {
	Button loginBut;
	Button forgetTx;
	Context context;
	String Login_Uri = "/driver_apps/signin";
	SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		sp = getSharedPreferences("app", Context.MODE_APPEND);
		context = this;
		loginBut = (Button) findViewById(R.id.login_bt);
		forgetTx = (Button) findViewById(R.id.forget_password);
		loginBut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				new LoginTask().execute();
			}
		});
		forgetTx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent tent = new Intent();
				tent.setClass(context, ForgetPassActivity.class);
				startActivity(tent);
			}
		});

	}

	public class LoginTask extends AsyncTask<Void, Void, String> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String result = null;
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("bound_phone", "13891892613"));
			params.add(new BasicNameValuePair("password", "333333"));
			HttpClient client = Connect.getHttpClient();
			HttpPost post = new HttpPost(Constant.BASE_URL + Login_Uri);

			try {
				post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

				HttpResponse response = client.execute(post);
				Log.d("cyd", "jalksdlkflskd" + "");
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity entity = response.getEntity();
					result = EntityUtils.toString(entity);

				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

				try {
					JSONObject obj = new JSONObject(result);
					if (obj.has("uuid")) {
						Constant.uuid = obj.getString("uuid");
						sp.edit().putString("uuid", Constant.uuid).commit();
						Intent it = new Intent();
						it.setClass(context, MainActivity.class);
						startActivity(it);
					} else {
						Toast.makeText(context, "用户名或密码错误", Toast.LENGTH_SHORT)
								.show();
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
