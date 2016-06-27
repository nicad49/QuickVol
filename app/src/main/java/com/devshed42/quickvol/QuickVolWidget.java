package com.devshed42.quickvol;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class QuickVolWidget extends AppWidgetProvider {

    private static final String LOG_TAG = QuickVolWidget.class.getSimpleName();
    private static final String MY_ON_CLICK_TAG = "myOnClickTag";

    private static final int NORMAL = 2;
    private static final int VIBRATE = 1;
    private static final int SILENT = 0;


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.quick_vol_widget);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        Log.d(LOG_TAG, "onUpdate called");

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.quick_vol_widget);
            updateIcon(context, views);
            views.setOnClickPendingIntent(R.id.img_widget, getPendingSelfIntent(context, MY_ON_CLICK_TAG));
            appWidgetManager.updateAppWidget(appWidgetId, views);
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public void updateIcon(Context context, RemoteViews remoteViews) {
        int currentRingMode = QuickVolAudio.getRingerMode(context);
        int iconId = 0;
        switch (currentRingMode) {
            case SILENT:
                iconId = R.drawable.ic_vol_off;
                break;
            case VIBRATE:
                iconId = R.drawable.ic_vol_vibe;
                break;
            case NORMAL:
                iconId = R.drawable.ic_vol_up;
                break;
        }
        Log.d(LOG_TAG, "Setting Icon Image to : " + iconId);
        setImage(remoteViews, R.id.img_widget, iconId);

    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void updateWidgets(Context context) {
        Log.d(LOG_TAG, "updateWidgets called");
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context, QuickVolWidget.class);
        Intent intent = new Intent(context, QuickVolWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetManager.getAppWidgetIds(componentName));
        context.sendBroadcast(intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (MY_ON_CLICK_TAG.equals(intent.getAction())) {
            Log.d(LOG_TAG, "Widget button clicked");
            setRingMode(context);
        }
        Log.d(LOG_TAG, "Something received");

    }

    private void setRingMode(Context context) {
        int currentRingMode = QuickVolAudio.getRingerMode(context);
        switch (currentRingMode) {
            case SILENT:
                Log.d(LOG_TAG, "Setting Ringer Mode: " + NORMAL);
                QuickVolAudio.setRingerMode(context, NORMAL);
                break;
            case VIBRATE:
                Log.d(LOG_TAG, "Setting Ringer Mode: " + SILENT);
                QuickVolAudio.setRingerMode(context, SILENT);
                break;
            case NORMAL:
                Log.d(LOG_TAG, "Setting Ringer Mode: " + VIBRATE);
                QuickVolAudio.setRingerMode(context, VIBRATE);
                break;
        }
        updateWidgets(context);
    }

    public static void setImage(RemoteViews rv, int viewId, int resId) {
        rv.setImageViewResource(viewId, resId);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

