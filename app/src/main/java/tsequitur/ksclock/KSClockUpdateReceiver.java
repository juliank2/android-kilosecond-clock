package tsequitur.ksclock;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.appwidget.AppWidgetManager;
import android.widget.RemoteViews;
import java.util.Calendar;
import java.text.DecimalFormat;
import java.util.TimeZone;

import android.util.Log;

public class KSClockUpdateReceiver extends BroadcastReceiver {

    public static void updateText(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d("----", "update");
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ksclock_appwidget);
            views.setTextViewText(R.id.textView, currentDayKilosecondsString());
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    public static long startOfDayMilliseconds(long milliseconds) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(milliseconds);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTimeInMillis();
    }

    public static String currentDayKilosecondsString() {
        long currentTime = System.currentTimeMillis();
        long elapsed = currentTime - startOfDayMilliseconds(currentTime);
        return new DecimalFormat("00.00").format(elapsed / 1000000.0);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisComponent = new ComponentName(context, KSClockAppWidgetProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisComponent);
        updateText(context, appWidgetManager, appWidgetIds);
    }
}

