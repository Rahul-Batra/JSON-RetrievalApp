/*package com.example.jsonassignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Option2Activity extends ActionBarActivity{
	public Button submit,back;
	public EditText edittext1,edittext2;
	int input;
	@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_option2);
	        submit=(Button)findViewById(R.id.button1);
	        submit.setOnClickListener(new View.OnClickListener(){
	        	 public void onClick(View v){
	 		        edittext1=(EditText)findViewById(R.id.editText1);
	 		        String input1=edittext1.getText().toString();
	 		        input=Integer.parseInt(input1);
	 		        //edittext1.setText("");
	 		        edittext2=(EditText)findViewById(R.id.editText2);
	 		      // edittext2.setText("this is a socket...");
					  JSONArray user = null; JSONParser jParser = new JSONParser();
					  JSONObject json = jParser.getJSONFromUrl("http://earthquake.usgs.gov/earthquakes/feed/geojsonp/2.5/week"); 
					  try { user = json.getJSONArray("features");
					  if(input>=0&&input<=user.length()){ JSONObject c =
					  user.getJSONObject(input);
					  edittext2.setText(c.toString(1)); } else
					  edittext2.setText(""); } catch (JSONException e) {
					  e.printStackTrace(); }
					 
	 		       JSONArray contacts = null;
	 		       ServiceHandler sh = new ServiceHandler();
	 		       String jsonStr = sh.makeServiceCall("http://earthquake.usgs.gov/earthquakes/feed/geojsonp/2.5/week", ServiceHandler.GET);
	 		       if (jsonStr != null) {
	 	                try {
	 	                    JSONObject jsonObj = new JSONObject(jsonStr);
	 	                    contacts = jsonObj.getJSONArray("features");
	 	                   if(input>=0&&input<=contacts.length()){
	 	  		            JSONObject c = contacts.getJSONObject(input);		                 
	 	  		            edittext2.setText(c.toString(1));
	 	  		            }
	 	  		            else
	 	  		            	 edittext2.setText("");
	                } catch (JSONException e) {
	                    e.printStackTrace();
	                }
	 		       }
	             String page = null;
				try {
					page = new Communicator().executeHttpGet("http://earthquake.usgs.gov/earthquakes/feed/geojsonp/2.5/week");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	             edittext2.setText(page);
	               String page = null;
	 				try {
	 					page = new Communicator().executeHttpGet("http://earthquake.usgs.gov/earthquakes/feed/geojsonp/2.5/week");
	 				} catch (Exception e1) {
	 					// TODO Auto-generated catch block
	 					e1.printStackTrace();
	 				}
	 				JSONObject json = null;
	 				try {
	 					json = new JSONObject(page);
	 				} catch (JSONException e1) {
	 					// TODO Auto-generated catch block
	 					e1.printStackTrace();
	 				}
	 				JSONArray earthquakes = null;
	 				try {
	 					earthquakes = json.getJSONArray("features");
	 				} catch (JSONException e1) {
	 					// TODO Auto-generated catch block
	 					e1.printStackTrace();
	 				}
	 			                         
	 				 if(input>=1&&input<=earthquakes.length()+1){
	 			        JSONObject e = null;
	 					try {
	 						e = earthquakes.getJSONObject(input-1);
	 					} catch (JSONException e1) {
	 						// TODO Auto-generated catch block
	 						e1.printStackTrace();
	 					}
	 			        
	 						try {
	 							edittext2.setText(e.toString(1));
	 						} catch (JSONException e1) {
	 							// TODO Auto-generated catch block
	 							e1.printStackTrace();
	 						}
	 				 }
	 				else
		            	 edittext2.setText("");
	 		        
	       if (isConnected()) {
				} else {
					edittext2.setText("You are NOT connected");
				}
				new HttpAsyncTask()
						.execute("http://earthquake.usgs.gov/earthquakes/feed/geojsonp/2.5/week");
						new NetworkTask().execute("http://earthquake.usgs.gov/earthquakes/feed/geojsonp/2.5/week");
			}
		});
		back = (Button) findViewById(R.id.button2);
		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Option2Activity.this,
						MainActivity.class);
				startActivity(intent);
			}
		});

	}
private class NetworkTask extends AsyncTask<String, Void, HttpResponse> {
    @Override
    protected HttpResponse doInBackground(String... params) {
        String link = params[0];
        HttpGet request = new HttpGet(link);
        AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
        try {
            return client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
        client.close();
    }
    }

    @Override
    protected void onPostExecute(HttpResponse result) {
	InputStream inputStream = null;
		String result1 = "";
        //Do something with result
       // if (result != null)
         //   result.getEntity().writeTo(new FileOutputStream(f));
		try {
			inputStream = result.getEntity().getContent();
		} catch (IllegalStateException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		if (inputStream != null)
			try {
				result1 = convertInputStreamToString(inputStream);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String regex = "eqfeed_callback";
			result1 = result1.replaceAll(regex, "");
			regex="\\(";
			result1=result1.replaceAll(regex,"");
			regex="\\)";
			result1=result1.replaceAll(regex, "");
			regex=";";
			result1=result1.replaceAll(regex,"");
		
			
			try {
				JSONObject json = new JSONObject(result1);
				
				JSONArray feature = json.getJSONArray("features");
				JSONObject c = feature.getJSONObject(input);
				edittext2.setText(c.toString(1));
				} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
    }
}
	public static String GET(String url) {
		InputStream inputStream = null;
		String result = "";
		try {

			// create HttpClient
			HttpClient httpclient = new DefaultHttpClient();

			// make GET request to the given URL
			HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

			// receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();

			// convert inputstream to string
			if (inputStream != null)
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";

		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}

		return result;
	}

	private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}

	public boolean isConnected() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected())
			return true;
		else
			return false;
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {

			return GET(urls[0]);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG)
					.show();
			//edittext2.setText(result);

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
		String id= c.getString("id");
		JSONObject prop = c.getJSONObject("properties");
		String place = prop.getString("place");
		String url = prop.getString("url");
		String alert = prop.get("alert").toString();
		String status = prop.getString("status");
		String net = prop.getString("net");
		String code = prop.getString("code");
		String ids = prop.getString("ids");
		String sources = prop.getString("sources");
		String types = prop.getString("types");
		String magType = prop.getString("magnitudeType");
		long time = prop.getLong("time");
		long updated = prop.getLong("updated");
		int tz = prop.getInt("tz");
		int felt = prop.getInt("felt");
		String tsunami = prop.get("tsunami").toString();
		int sig = prop.getInt("sig");
		int nst = prop.getInt("nst");
		double mag =prop.getDouble("mag");
		double cdi =prop.getDouble("cdi");
		String mmi =prop.get("mmi").toString();
		double dmin =prop.getDouble("dmin");
		double rms =prop.getDouble("rms");
		double gap =prop.getDouble("gap");
		JSONObject geomtry = c.getJSONObject("geometry");
		JSONArray coordinate = geomtry.getJSONArray("coordinates");
		double longitude = Double.parseDouble(coordinate.get(0).toString());
		double  latitude = Double.parseDouble(coordinate.get(1).toString());
		double depth = Double.parseDouble(coordinate.get(2).toString());
		
		
		String str = "{type:Feature,properties:{mag:"+mag+",place:"+place+",time:"+time+",updated:"+updated+",tz:"+tz+",url:"+url+",felt:"+felt+",cdi:"+cdi+",mmi:"+mmi+",alert:"+alert+",status:"+status+",tsunami:"+tsunami+",sig:"+sig+",net:"+net+",code:"+code+",ids:"+ids+",sources:"+sources+",types:"+types+",nst:"+nst+",dmin:"+dmin+",rms:"+rms+",gap:"+gap+",magnitudeType:"+magType+"},geometry:{type:Point,coordinates:["+longitude+","+latitude+","+depth+"]},id:"+id+"},";
		edittext2.setText(str);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
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
*/
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

public class Option2Activity extends ActionBarActivity {
	public Button submit, back;
	public EditText edittext1, edittext2;
	int input=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option2);
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
				Intent intent = new Intent(Option2Activity.this,
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
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
