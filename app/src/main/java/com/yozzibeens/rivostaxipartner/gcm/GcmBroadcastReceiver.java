package com.yozzibeens.rivostaxipartner.gcm;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.google.gson.Gson;
import com.yozzibeens.rivostaxipartner.*;
import com.yozzibeens.rivostaxipartner.actividades.Splash;

public class GcmBroadcastReceiver  extends WakefulBroadcastReceiver {

	/*@Override
	public void onReceive(Context context, Intent intent) {
        Log.e("Broadcast", "----------------------- GCM Broad cast");
		ComponentName comp = new ComponentName(context.getPackageName(),
                GCMNotificationIntentService.class.getName());
		startWakefulService(context, (intent.setComponent(comp)));
		setResultCode(Activity.RESULT_OK);
	}*/

	private String TAG = "Broadcast receiver";
	private int NOTIFICATION_ID = 1234;
	private Gson gson;
	//private Notificacion notificacion;

	int NotificacionPedido;
	int NotificacionCotizacion;
	boolean Procesado;
	String FolioCotizacionSuc;
	String Mensaje;

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public void onReceive(Context context, Intent intent)
	{
		ComponentName comp = new ComponentName(context.getPackageName(), GCMService.class.getName());
		startWakefulService(context, (intent.setComponent(comp)));
		setResultCode(Activity.RESULT_OK);

		this.gson = new Gson();

		byte a = 0;
		Log.i(TAG, "Notificacion recibida.");
		String msg = intent.getStringExtra("message");
		String val = intent.getStringExtra("val");


		if (intent.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) {
		}


		setResultCode(Activity.RESULT_OK);
		long[] pattern = {300,300,300,300,300,300};
		Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		//Toast.makeText(context, "Notificacion recibida. " + intent.getExtras().toString(), Toast.LENGTH_LONG).show();
		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(context)
						.setSmallIcon(R.mipmap.ic_launcher)
						.setContentTitle("Rivos")
						.setSound(alarmSound)
						.setVibrate(pattern)
						.setTicker("Notificacion de Rivos.")
						.setLights(Color.YELLOW, 500, 500)
						.setAutoCancel(true)
						.setContentText(msg);
		if (val != null)
		{
			if (val.equals("A")){
				Intent resultIntent = new Intent(context, Splash.class);
				TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
				stackBuilder.addParentStack(Splash.class);
				stackBuilder.addNextIntent(resultIntent);
				PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
				mBuilder.setContentIntent(resultPendingIntent);
				NotificationManager mNotificationManager =
						(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
				// mId allows you to update the notification later on.
				mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
			}

		}



/*		resultIntent.putExtra("NotificacionPedido", NotificacionPedido);
		resultIntent.putExtra("NotificacionCotizacion", NotificacionCotizacion);
		resultIntent.putExtra("Procesado", Procesado);
		resultIntent.putExtra("FolioCotizacionSuc", FolioCotizacionSuc);
		resultIntent.putExtra("Mensaje", Mensaje);*/


	}



}

/*
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
        Log.e("Broadcast", "----------------------- GCM Broad cast");
		ComponentName comp = new ComponentName(context.getPackageName(),
                GCMNotificationIntentService.class.getName());
		startWakefulService(context, (intent.setComponent(comp)));
		setResultCode(Activity.RESULT_OK);
	}
}
*/