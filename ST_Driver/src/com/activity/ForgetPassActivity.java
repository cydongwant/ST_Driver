package com.activity;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
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

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.activity.LoginAcitivty.LoginTask;
import com.constant.Constant;
import com.ry.st.driver.R;
import com.util.Connect;

public class ForgetPassActivity extends Activity {
	String Identify_Url = "/driver_apps/reset_request/13891892613";
	String Reset_Url = "/driver_apps/reset_password/13891892613";
	Button submitBt;
	EditText identifyEd;
	Context context;
    SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		sp = getSharedPreferences("app", Context.MODE_PRIVATE);
		setContentView(R.layout.reset_password);
		submitBt = (Button) findViewById(R.id.submit_bt);
		identifyEd = (EditText) findViewById(R.id.identify_code);
		identifyEd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new GetIdentifyCodeTask().execute();
			}
		});
		submitBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
               new SubmitTask().execute();
			}
		});
	}

	public class GetIdentifyCodeTask extends AsyncTask<Void, Void, Integer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			HttpURLConnection connection = null;
			int code = 0;
			try {
				URL url = new URL(Constant.BASE_URL + Identify_Url);
				connection = (HttpURLConnection) url.openConnection();
				connection.setConnectTimeout(5 * 1000);
				connection.setReadTimeout(10 * 1000);
				connection.setDoInput(true);
				// connection.setDoOutput(false);
				// connection.setRequestMethod("GET");
				connection.setUseCaches(false);
				connection.connect();
				 code = connection.getResponseCode();
				Log.d("cyd", code + "/");
				// connection.getInputStream();

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (connection != null) {
					connection.disconnect();
				}
			}
			// HttpClient client = Connect.getHttpClient();
			// HttpGet get = new HttpGet(Constant.BASE_URL + Identify_Url);
			// try {
			// client.execute(get);
			// } catch (ClientProtocolException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			return code;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Toast.makeText(context, "已发送请求,请注意短信",Toast.LENGTH_SHORT).show();
		}
	}

	public class SubmitTask extends AsyncTask<Void, Void, String> {
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
			params.add(new BasicNameValuePair(
					"password", "123456"));
			params.add(new BasicNameValuePair("token",
					"fdsa"));
			HttpClient client = Connect.getHttpClient();
			HttpPost post = new HttpPost(Constant.BASE_URL + Reset_Url);
			try {
				post.setEntity(new UrlEncodedFormEntity(params));
				HttpResponse response = client.execute(post);
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
			try {
				JSONObject obj = new JSONObject(result);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}
}
