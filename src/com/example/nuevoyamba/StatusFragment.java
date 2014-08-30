package com.example.nuevoyamba;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StatusFragment extends Fragment implements OnClickListener {

	private EditText editStatus;
	private Button buttonTweet;
	private static final String TAG = "StatusActivity";
	Twitter twitter;
	private TextView textCount;  
	private int defaultTextColor; 

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
		//super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_status, container, false);
		//setContentView(R.layout.activity_status);
		editStatus = (EditText) view.findViewById(R.id.editStatus);
		buttonTweet = (Button) view.findViewById(R.id.buttonTweet);
		textCount = (TextView) view.findViewById(R.id.textCount);
		defaultTextColor = textCount.getTextColors().getDefaultColor();
		editStatus.addTextChangedListener(new TextWatcher() { 
			@Override
			public void afterTextChanged(final Editable s) { 
				final int count = 140 - editStatus.length();  
				textCount.setText(Integer.toString(count));
				textCount.setTextColor(Color.GREEN); 
				if (count < 10)
					textCount.setTextColor(Color.RED);
				else
					textCount.setTextColor(defaultTextColor);
			}
			@Override
			public void beforeTextChanged(CharSequence s,int start, int count,int after) {
			}
			@Override
			public void onTextChanged(CharSequence s,int start, int before,int count) { 
			}
		});

		buttonTweet.setOnClickListener(this);

		twitter = new Twitter("student","password");
		twitter.setAPIRootUrl("http://yamba.marakana.com/api");
		return view;
	}

	@Override
	public void onClick(View v) {
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
