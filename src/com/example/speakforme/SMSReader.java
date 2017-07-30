package com.example.speakforme;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SMSReader extends Activity {
	Button yes_btn,no_btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_smsreader);
		
		
		
		yes_btn = (Button) findViewById(R.id.button1);
		no_btn = (Button) findViewById(R.id.button2);
		
		yes_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
				SharedPreferences.Editor editor = settings.edit();
				editor.putBoolean("state_sms_speak",true);
				editor.commit();
				
				Toast toast=Toast.makeText(getApplicationContext(),"Thanks for allowing me to read",Toast.LENGTH_SHORT);  
				toast.setMargin(50,50);  
				toast.show(); 
				
				Intent home_int = new Intent(getApplicationContext(),MainActivity.class);
				startActivity(home_int);
				
			}
			
		});
		
		no_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
				SharedPreferences.Editor editor = settings.edit();
				editor.putBoolean("state_sms_speak",false);
				editor.commit();
				
				Toast toast=Toast.makeText(getApplicationContext(),"Good to hear you have time to read",Toast.LENGTH_SHORT);  
				toast.setMargin(50,50);  
				toast.show();
				
				Intent home_int = new Intent(getApplicationContext(),MainActivity.class);
				startActivity(home_int);
			}
			
		});
		
	}

}
