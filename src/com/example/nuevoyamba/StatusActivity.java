package com.example.nuevoyamba;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class StatusActivity extends Activity implements OnClickListener {

	private EditText editStatus;
	private Button buttonTweet;
	private static final String TAG = "StatusActivity";
	Twitter twitter;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status);
		editStatus = (EditText) findViewById(R.id.editStatus);
		 buttonTweet = (Button) findViewById(R.id.buttonTweet);
		buttonTweet.setOnClickListener(this);
		
		twitter = new Twitter("student","password");
		twitter.setAPIRootUrl("http://yamba.marakana.com/api");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String status = editStatus.getText().toString(); // 
		 Log.d(TAG, "onClicked with status: " + status);
		 new PostTask().execute(status);
	}
	
	private final class PostTask extends AsyncTask<String,Void,String>{

		@Override
		protected String doInBackground(String... params) {
			
			try{
				Twitter.Status status = twitter.updateStatus(params[0]);
				return status.text;
			}catch(TwitterException e){
				e.printStackTrace();
				return "Failed to post to yamba";
			}
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		editStatus = (EditText) findViewById(R.id.editStatus);
		buttonTweet = (Button) findViewById(R.id.buttonTweet);

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
