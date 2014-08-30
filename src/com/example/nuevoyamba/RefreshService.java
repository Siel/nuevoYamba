package com.example.nuevoyamba;

import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Twitter.Status;
import winterwell.jtwitter.TwitterException;
import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import java.util.List;



public class RefreshService extends IntentService {

	static final String TAG = "RefreshService";
	Twitter twitter;

	public RefreshService() {
		super(TAG);
	}
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "OnCreated");
	}
	@Override
	protected void onHandleIntent(Intent intent) { 
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this); // 
		final String username = prefs.getString("username", "");
		final String password = prefs.getString("password", "");
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			Toast.makeText(this,
					"Please update your username and password",
					Toast.LENGTH_LONG).show();
			return;
		}
		Log.d(TAG, "onStarted");
		String apiroot = prefs.getString("apiroot","http://yamba.marakana.com/api");
		twitter=new Twitter(username,password);
		twitter.setAPIRootUrl(apiroot); 
		try {
			 List<Status> timeline = twitter.getUserTimeline(); // 
			 for (Status status : timeline) { // 
			 Log.d(TAG,
			 String.format("%s: %s", status.getUser(),
			 status.getText())); // 
			 }
			 } catch (TwitterException e) { // 
			 Log.e(TAG, "Failed to fetch the timeline", e);
			 e.printStackTrace();
			 }
			 return;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "OnDestroyed");
	}

}
