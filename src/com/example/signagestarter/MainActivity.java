package com.example.signagestarter;


import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	  
	 
	String[] textArray = new String[10];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		AsyncGet asyncGet = new AsyncGet(new AsyncCallback() {
            public void onPreExecute() {
                // do something
            }
            public void onProgressUpdate(int progress) {
                // do something
            }
            public void onPostExecute(String result) {
                // do something
                //Log.d("onPostExecute", result);
            }
            public void onCancelled() {
                // do something
            }
        },
        textArray
        );
        asyncGet.execute("http://rss.dailynews.yahoo.co.jp/fc/rss.xml");
        
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	
	
}
