package com.example.speakforme;

import java.util.Locale;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.speech.tts.TextToSpeech;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract.PhoneLookup;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SMSReceiver extends BroadcastReceiver{
   
	TextToSpeech tts;
    public void onReceive(Context context, Intent intent) {
       
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                
                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                   if(pdusObj.length > 0){
                	   
                	   SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdusObj[0]);
                       String phoneNumber = sms.getDisplayOriginatingAddress();
                       String sender = getContactName(context,phoneNumber);
                       String message = sms.getDisplayMessageBody();
                       
                       String speaker_text = "Hello! A new text from "+ sender +
                    		   				"!says!"+message;
                                   
                       SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
                       SharedPreferences.Editor editor = settings.edit();
       				editor.putString("speaker_text",speaker_text);
       				editor.commit();
       				
       				if(settings.getBoolean("state_sms_speak",false)){
       					
       					Intent speechIntent = new Intent();
                        speechIntent.setClass(context, SMSBroadcaster.class);
                        speechIntent.putExtra("speaker_text", speaker_text);
                        speechIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |  Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        context.startService(speechIntent);
       					
       				}
                       
 
                   }
                   
                   
              }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static String getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri, new String[]{PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;
        if(cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME));
        }

        if(cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return contactName;
    }
}