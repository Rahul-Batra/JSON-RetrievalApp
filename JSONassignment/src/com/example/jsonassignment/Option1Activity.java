package com.example.jsonassignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Option1Activity extends ActionBarActivity {
	public Button submit, back;
	public EditText edittext1, edittext2;
	int input=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option1);
		submit = (Button) findViewById(R.id.button1);
		submit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				edittext1 = (EditText) findViewById(R.id.editText1);
				String input1 = edittext1.getText().toString();
				input = Integer.parseInt(input1);
				edittext2 = (EditText) findViewById(R.id.editText2);
				new AndroidHTTPclient()
						.execute("http://earthquake.usgs.gov/earthquakes/feed/geojsonp/2.5/week");
			}
		});
		back = (Button) findViewById(R.id.button2);
		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Option1Activity.this,
						MainActivity.class);
				startActivity(intent);
			}
		});

	}
	public static String fetch(String linkk) {
		InputStream data = null;
		String output = "";
		try {
			HttpClient androidhttpclient = new DefaultHttpClient();
			HttpResponse androidhttpResponse = androidhttpclient.execute(new HttpGet(linkk));
			data = androidhttpResponse.getEntity().getContent();
			if (data != null)
				output = changetostring(data);				
		} catch (Exception e) {}
		return output;
	}
	private static String changetostring(InputStream data)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(data));
		String line = "";
		String output = "";
		while ((line = bufferedReader.readLine()) != null)
			output += line;
		data.close();
		return output;
	}
	private class AndroidHTTPclient extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... linkks) {
			return fetch(linkks[0]);
		}
		@Override
		protected void onPostExecute(String result) {
			String regex = "eqfeed_callback";
			result = result.replaceAll(regex, "");
			regex="\\(";
			result=result.replaceAll(regex,"");
			regex="\\)";
			result=result.replaceAll(regex, "");
			regex=";";
			result=result.replaceAll(regex,"");	
			try {
				JSONObject json = new JSONObject(result);				
				JSONArray feature = json.getJSONArray("features");
				JSONObject c = feature.getJSONObject(input);
				edittext2.setText(c.toString(1));		
			} catch (JSONException e) {				
				e.printStackTrace();
			}				
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
