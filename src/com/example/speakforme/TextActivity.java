package com.example.speakforme;


import android.os.Bundle;
import java.util.Locale;


import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class TextActivity extends Activity implements TextToSpeech.OnInitListener {

	private TextToSpeech tts;
	Button speak_btn,clr_btn,stp_btn;
	EditText txt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text);
		
		tts = new TextToSpeech(this, this);
		
		speak_btn = (Button) findViewById(R.id.button1);
		
		speak_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				speakOut();
			}
			
		});
		
		txt = (EditText) findViewById(R.id.editText1);
		
		clr_btn = (Button) findViewById(R.id.button2);

		clr_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				txt.setText("");
			}
			
		});
		
		stp_btn = (Button) findViewById(R.id.button3);

		stp_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if(tts!=null){
					tts.stop();
				}
			}
			
		});
		
		
		
	}
	
	@Override  
	public void onDestroy() {  
	// Don't forget to shutdown tts!  
	if (tts != null) {  
	    tts.stop();  
	    tts.shutdown();  
	}  
	super.onDestroy();  
	}

	@Override
	public void onInit(int status) {
		
		if (status == TextToSpeech.SUCCESS) {  
			  
		    int result = tts.setLanguage(Locale.US);  
		  
		    if (result == TextToSpeech.LANG_MISSING_DATA  
		            || result == TextToSpeech.LANG_NOT_SUPPORTED) {  
		        Log.e("TTS", "This Language is not supported");  
		    } else {  
		        speak_btn.setEnabled(true);  
		        speakOut();  
		    }  
		  
		} else {  
		    Log.e("TTS", "Initilization Failed!");  
		}  
		
	}
	
	
	@SuppressWarnings("deprecation")
	private void speakOut() {  
		String text = txt.getText().toString();  
		tts.speak(text, TextToSpeech.QUEUE_ADD, null);  
		}  	

}
