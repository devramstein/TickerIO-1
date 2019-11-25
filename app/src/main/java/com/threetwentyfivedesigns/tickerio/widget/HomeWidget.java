package com.threetwentyfivedesigns.tickerio.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.threetwentyfivedesigns.tickerio.activities.MainActivity;
import com.threetwentyfivedesigns.tickerio.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Implementation of App Widget functionality.
 */
public class HomeWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat2 = new SimpleDateFormat("yyD");
        String timeString = "Local Julian Day  Is: " + mdformat2.format(calendar.getTime());

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.home_widget);

        views.setTextViewText(R.id.julian, timeString);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat mdformat2 = new SimpleDateFormat("yyD");
            String timeString = "Local Julian Day Is: " + mdformat2.format(calendar.getTime());

            // Launch Main App from Widget
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.home_widget);
            Intent configIntent = new Intent(context, MainActivity.class);
            PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);
            remoteViews.setOnClickPendingIntent(R.id.home_widget_layout, configPendingIntent);
            remoteViews.setTextViewText(R.id.julian, timeString);
            appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
        }
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

