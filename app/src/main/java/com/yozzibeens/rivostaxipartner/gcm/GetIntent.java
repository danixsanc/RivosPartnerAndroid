package com.yozzibeens.rivostaxipartner.gcm;

import android.os.Bundle;

import com.yozzibeens.rivostaxipartner.R;



public class GetIntent extends com.yozzibeens.rivostaxipartner.Main {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        /*String get = getIntent().getStringExtra("Notif");

        Log.e("Msg", "---------------------------"+get);

        TextView txt = (TextView)findViewById(R.id.get);
        txt.setText(get);*/

    }
}
