package com.dam.proyecto.prct1broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.ArrayList;

public class PhoneStatusReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        final TelephonyManager tm =
                (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        PhoneStateListener phl = new PhoneStateListener() {
            public void onCallStateChanged(int state, String phoneNumber){
                Intent i = new Intent(context, UploadDataService.class);
                i.putExtra("code", state);
                i.putExtra("phone", phoneNumber);
                context.startService(i);
            }
        };
        tm.listen(phl , PhoneStateListener.LISTEN_CALL_STATE);
    }
}
