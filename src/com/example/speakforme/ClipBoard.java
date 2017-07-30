package com.example.speakforme;

import java.util.Locale;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.ClipboardManager;
import android.content.ClipboardManager.OnPrimaryClipChangedListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;




public class ClipBoard extends Service implements OnInitListener {
private ClipboardManager mCM;
IBinder mBinder;
int mStartMode;
TextToSpeech tts ;
String speaker_text;
 @SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@Override
 public int onStartCommand(Intent intent, int flags, int startId) {
    mCM = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
    mCM.addPrimaryClipChangedListener(new OnPrimaryClipChangedListener() {

        @SuppressWarnings("deprecation")
		public void onPrimaryClipChanged() {
			String newClip = mCM.getText().toString();
            
           
            speaker_text = "Copied!!"+ newClip.toString();
           
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			
			if(settings.getBoolean("state_clipboard_speak",false)){
				Toast.makeText(getApplicationContext(),"Wait",  Toast.LENGTH_SHORT).show();
				tts = new TextToSpeech(ClipBoard.this,ClipBoard.this);	
				
			}


        }
    });
    return mStartMode;
 }


@Override
public IBinder onBind(Intent intent) {
    // TODO Auto-generated method stub
    return null;
  }


@SuppressWarnings("deprecation")
@Override
public void onInit(int status) {
    if (status == TextToSpeech.SUCCESS) {
        int result = tts.setLanguage(Locale.US);
        if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
            tts.speak(speaker_text, TextToSpeech.QUEUE_ADD, null);
        }
    }
}



@Override
public void onDestroy() {
    if (tts != null) {
        tts.stop();
        tts.shutdown();
    }
    super.onDestroy();
}
 }