package com.dam.proyecto.prct1broadcastreceiver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UploadDataService extends Service {

    private final String tag = "asdf";
    private ArrayList<Integer> codes;
    private int code;
    private String phone;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(tag, "On create");
        codes = new ArrayList<>();
        code = -1;
        phone = "";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 2º, se ejecuta un vez
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 3º, se ejecuta muchas veces
        // START_NOT_STICKY, START_REDELIVER_INTENT, START_STICKY
        String latestphone = intent.getStringExtra("phone");
        code = intent.getIntExtra("code", -1);
        if (code == 0){
            codes.add(code);
            Log.v(tag, "# END CALL # Phone: " + phone + " - codigo: " + code + " - CODES: " + codes.toString());
            if (shouldUploadInfo()){
                String fechaS = getDateF();
                String tipo = codes.contains(1) ? "Entrante" : "Saliente";
                Log.v(tag, "# SENDING DATA # Phone: " + phone + " - Fecha, hora: " + fechaS + " - Tipo:" + tipo + " - CODES: " + codes.toString());
                LlamadaInfo llinfo = new LlamadaInfo();
                llinfo.setFecha(fechaS);
                llinfo.setNumTlf(phone);
                llinfo.setTipo(tipo);
                sendLlamadaInfo(llinfo);
            }
            clearData();
        } else {
            Log.v(tag, "# ON CALL # Phone: " + phone + " - codigo: " + code + " - CODES: " + codes.toString());
            if((phone == "")){
                Log.v(tag, "# Setting phone number #");
                phone = latestphone;
            }
            if(!codes.contains(code)) codes.add(code);
        }
        return START_REDELIVER_INTENT;
    }

    private boolean shouldUploadInfo(){
        boolean ok;
        ok = codes.size() > 1 && (phone != "" && phone != null);
        return ok;
    }

    private void clearData(){
        phone = "";
        code = -1;
        codes = new ArrayList<>();
    }

    public String getDateF(){
        Date fecha = new Date();
        SimpleDateFormat writeDate = new SimpleDateFormat("dd.MM.yyyy, HH.mm");
        writeDate.setTimeZone(TimeZone.getTimeZone("GMT+01:00"));
        String sDate = writeDate.format(fecha);
        return sDate;
    }

    public void sendLlamadaInfo(LlamadaInfo llInfo){
        // Crea retrofit
        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:3000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

        // 2, creo el cliente interface
        ClienteRest cliente = retrofit.create(ClienteRest.class);

        // 3, objeto call para hacer la llamada
        Call<LlamadaInfo> call = cliente.postLLamadaInfo(llInfo);

        // 4, encolar la petición
        call.enqueue(new Callback<LlamadaInfo>() {
            @Override
            public void onResponse(Call<LlamadaInfo> call, Response<LlamadaInfo> response) {
                LlamadaInfo info = response.body();
                Log.v(tag, "LlamadaInfo --> " + info);
            }

            @Override
            public void onFailure(Call<LlamadaInfo> call, Throwable t) {
                Log.v(tag, "Fallo " + t.getLocalizedMessage());
            }
        });
    }
}