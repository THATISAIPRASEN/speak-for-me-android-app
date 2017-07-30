package com.example.speakforme;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	Button sms_btn,txt_btn,exit_btn,cm_btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		startService(new Intent(this, ClipBoard.class));
		
		sms_btn = (Button) findViewById(R.id.button1);
		sms_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent sms_int = new Intent(getApplicationContext(),SMSReader.class);
				startActivity(sms_int);
			}
			
		});
		
		txt_btn = (Button) findViewById(R.id.button2);
		txt_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent txt_int = new Intent(getApplicationContext(),TextActivity.class);
				startActivity(txt_int);
			}
			
		});
		
		cm_btn = (Button) findViewById(R.id.button3);
		cm_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent sms_int = new Intent(getApplicationContext(),ClipBoardManager.class);
				startActivity(sms_int);
			}
			
		});
		
		exit_btn = (Button) findViewById(R.id.button4);
		exit_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				finish();
			}
			
		});
		
		
	}


}
