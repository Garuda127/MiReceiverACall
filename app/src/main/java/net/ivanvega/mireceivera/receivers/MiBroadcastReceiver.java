package net.ivanvega.mireceivera.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class MiBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "MiBroadcastReceiver";
    private String strMensaje="";

    @Override
    public void onReceive(Context context,
                          Intent intent) {


        String action = intent.getAction();
        //Uri uri = intent.getData();


        if(action.equals(Intent.ACTION_BOOT_COMPLETED)){

        }
        if(Intent.ACTION_INPUT_METHOD_CHANGED== action){

        }
        if(action.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
            Toast.makeText(context, "LLamando", Toast.LENGTH_SHORT).show();
        }
        if(action.equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)){
            Bundle bndSMS = intent.getExtras();
            Object[]  pdus = (Object[]) bndSMS.get("pdus");
            SmsMessage[] smms = new SmsMessage[pdus.length];
            for(int i=0; i< smms.length  ; i++ ){
                smms[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                strMensaje = "Mensaje: " + smms[i].getOriginatingAddress() + "\n" +
                        smms[i].getMessageBody().toString();
            }
            Log.d("MiBroadcast", strMensaje);

        }



        Log.d("MiBroadcast", action);
        //Log.d("MiBroadcast", uri.toString());
        Toast.makeText(context.getApplicationContext(),
                action, Toast.LENGTH_SHORT).show();

        Toast.makeText(context.getApplicationContext(),
                strMensaje, Toast.LENGTH_SHORT).show();

        StringBuilder sb = new StringBuilder();
        sb.append("Action: " + intent.getAction() + "\n");
        sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
        String log = sb.toString();
        Log.d(TAG, log);
        Toast.makeText(context, log, Toast.LENGTH_LONG).show();


    }
}
