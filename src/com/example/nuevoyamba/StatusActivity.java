package com.example.nuevoyamba;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
public class StatusActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(savedInstanceState == null){
			StatusFragment fragment = new StatusFragment();
			getFragmentManager().beginTransaction().add(android.R.id.content,fragment, fragment.getClass().getSimpleName()).commit();
		}
		//setContentView(R.layout.new_activity_status);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.status, menu);
		return true;
	}
}
