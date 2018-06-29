
package com.mafdy.udacity_bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.mafdy.udacity_bakingapp.R;
import com.mafdy.udacity_bakingapp.RecipeDetailActivity;

import java.util.ArrayList;
import java.util.List;

import static com.mafdy.udacity_bakingapp.widget.UpdateBakingService.FROM_ACTIVITY_INGREDIENTS_LIST;


/**
 * Created by SBP on 6/13/2018.
 */

public class BakingWidgetProvider extends AppWidgetProvider {



    static List<String> ingredientsList = new ArrayList<>();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_grid_view);


        Intent intent = new Intent(context, RecipeDetailActivity.class);

        intent.addCategory(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT|Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_grid_view, appPendingIntent);


        Intent intent2 = new Intent(context, GridWidgetService.class);
        views.setRemoteAdapter(R.id.widget_grid_view, intent2);

        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

         for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager,  appWidgetId);
        }
    }

    public static void updateBakingWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {

    }

    @Override
    public void onDisabled(Context context) {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, BakingWidgetProvider.class));

        final String action = intent.getAction();

           if (action.equals("android.appwidget.action.APPWIDGET_UPDATE2")) {
               ingredientsList = intent.getExtras().getStringArrayList(FROM_ACTIVITY_INGREDIENTS_LIST);
               appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);

               BakingWidgetProvider.updateBakingWidgets(context, appWidgetManager, appWidgetIds);
               super.onReceive(context, intent);
           }
    }

}

