package com.example.jsonassignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

import android.net.http.AndroidHttpClient;
import android.util.Log;

public class AndroidHttp {
static InputStream is = null;
static String json = "";
 
public AndroidHttp() {}
public String getDataFromUrl(String url) {	   
	    try {	 
	      HttpGet request = new HttpGet(url);
	      AndroidHttpClient client = AndroidHttpClient.newInstance("");	      
	      HttpResponse httpResponse = client.execute(request);
	      HttpEntity httpEntity = httpResponse.getEntity();
	      is = httpEntity.getContent();
	    } catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	    } catch (ClientProtocolException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    try {
	      BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
	      StringBuilder sb = new StringBuilder();
	      String line = null;
	      while ((line = reader.readLine()) != null) {
	        sb.append(line + "n");
	      }
	      is.close();
	      json = sb.toString();
	    } catch (Exception e) {
	      Log.e("Buffer Error", "Error converting result " + e.toString());
	    }	    
	   return json;
	  }
}