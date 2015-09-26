package com.example.jsonassignment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {
	public Button choice1,choice2,choice3,choice4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		choice1=(Button)findViewById(R.id.option1);
		choice1.setOnClickListener(new View.OnClickListener(){
	        public void onClick(View v){
	        	Intent intent = new Intent(MainActivity.this, Option1Activity.class);
	        	startActivity(intent);	       
	        }
		});
		choice2=(Button)findViewById(R.id.option2);
		choice2.setOnClickListener(new View.OnClickListener(){
	        public void onClick(View v){
	        	Intent intent = new Intent(MainActivity.this, Option2Activity.class);
	        	startActivity(intent);	 
	        }
		});
		choice3=(Button)findViewById(R.id.option3);
		choice3.setOnClickListener(new View.OnClickListener(){
	        public void onClick(View v){
	        	Uri uri = Uri.parse("http://earthquake.usgs.gov/earthquakes/feed/geojsonp/2.5/week");
	        	Intent intent = new Intent(Intent.ACTION_VIEW, uri);
	        	startActivity(intent);
	        }
		});
		choice4=(Button)findViewById(R.id.option4);
		choice4.setOnClickListener(new View.OnClickListener(){
	        public void onClick(View v){
	        	Intent intent = new Intent(MainActivity.this, Option4Activity.class);
	        	startActivity(intent);	 
	        }
		});
	        
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
