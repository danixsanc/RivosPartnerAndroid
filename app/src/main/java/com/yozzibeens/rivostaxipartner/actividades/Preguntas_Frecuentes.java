package com.yozzibeens.rivostaxipartner.actividades;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.yozzibeens.rivostaxipartner.R;

public class Preguntas_Frecuentes extends Activity {

    TextView textView3,textView2;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preguntas_frecuentes);

        Typeface RobotoCondensed_Regular = Typeface.createFromAsset(getAssets(), "RobotoCondensed-Regular.ttf");

        textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setTypeface(RobotoCondensed_Regular);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView3.setTypeface(RobotoCondensed_Regular);
    }
}