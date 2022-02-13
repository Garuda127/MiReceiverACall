package net.ivanvega.mireceivera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Telephony;
import android.telecom.TelecomManager;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.EditText;

import net.ivanvega.mireceivera.receivers.MiBroadcastReceiver;

public class MainActivity extends AppCompatActivity {

    MiBroadcastReceiver mbr;

    EditText txtN, txtS;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //RECEPTORES REGISTRADOS A NIVEL DE CONTEXTO
        IntentFilter ifil = new IntentFilter();
        ifil.addAction(Intent.ACTION_BOOT_COMPLETED);
        //ifil.addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        ifil.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);

        ifil.addAction("net.ivanvega.mireceivera.MIDIFUSION");
         mbr = new MiBroadcastReceiver();
        registerReceiver(mbr, ifil);


        txtN = findViewById(R.id.txtNumber);
        txtS = findViewById(R.id.txtMensaje);


        findViewById(R.id.btnEnviar).setOnClickListener(
            v -> enviarSMS()
        );
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},1);
        }
    }

    private void enviarSMS() {
          SmsManager smsManager = SmsManager.getDefault();
          smsManager.sendTextMessage(txtN.getText().toString(),
                  null,
                  txtS.getText().toString(),
                  null, null);

          Intent miIntent = new Intent("net.ivanvega.mireceivera.MIDIFUSION");
          sendBroadcast(miIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mbr);
    }
}