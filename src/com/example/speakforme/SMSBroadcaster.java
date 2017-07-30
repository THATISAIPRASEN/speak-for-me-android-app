package com.example.speakforme;


import java.util.Locale;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


@SuppressWarnings("deprecation")
public class SMSBroadcaster extends Service implements TextToSpeech.OnInitListener, OnUtteranceCompletedListener {
    private TextToSpeech tts;
    private String speaker_text = "Hello Speaking";

    @Override
    public void onCreate() {
       //tts = new TextToSpeech(this, this);
//       Intent startingIntent = this.getIntent();
//       speaker_text = startingIntent.getStringExtra("speech_text");
    }
    
    public int onStartCommand (Intent intent, int flags, int startId) {
        //speaker_text = intent.getStringExtra("speaker_text");
    	tts = new TextToSpeech(this, this);
        return START_STICKY;
    }

    @SuppressWarnings("deprecation")
	@Override
    public void onInit(int status) {
    	 SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
    	 speaker_text = settings.getString("speaker_text", "error");
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
                tts.speak(speaker_text, TextToSpeech.QUEUE_ADD, null);
            }
        }
    }

    @Override
    public void onUtteranceCompleted(String uttId) {
        stopSelf();
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}