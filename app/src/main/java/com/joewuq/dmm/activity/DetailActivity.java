package com.joewuq.dmm.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

import com.joewuq.dmm.CountdownCardViewHolder;
import com.joewuq.dmm.CountdownModel;
import com.joewuq.dmm.R;
import com.joewuq.dmm.manager.PreferencesManager;
import com.joewuq.dmm.manager.SerializationManager;
import com.joewuq.dmm.utility.ThemeColor;
import com.joewuq.dmm.utility.Utility;
import com.melnykov.fab.FloatingActionButton;

/**
 * Created by Joe Wu on 1/11/15.
 */
public class DetailActivity extends ToolbarActivity {

    public static final String TAG = DetailActivity.class.getName();

    public static final String EXTRA_COUNTDOWN_UUID = "COUNTDOWN_UUID";
    public static final String EXTRA_COUNTDOWN_MODEL = "COUNTDOWN_MODEL";

    private static final String STATE_COUNTDOWN_MODEL = "COUNTDOWN_MODEL";

    private CountdownModel model;

    CardView cardView;
    CountdownCardViewHolder cardViewHolder;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (savedInstanceState != null && savedInstanceState.getString(STATE_COUNTDOWN_MODEL) != null) {
            // try recover countdown model from savedInstanceState first
            model = SerializationManager.getInstance().deserialize(savedInstanceState.getString(STATE_COUNTDOWN_MODEL), CountdownModel.class);
        } else if (extras != null && extras.getString(EXTRA_COUNTDOWN_MODEL) != null) {
            // try recover countdown model from the launching intent
            model = SerializationManager.getInstance().deserialize(extras.getString(EXTRA_COUNTDOWN_MODEL), CountdownModel.class);
        } else if (extras != null && extras.getString(EXTRA_COUNTDOWN_UUID) != null) {
            // try load from preference
            model = PreferencesManager.getInstance().loadCountdownModel(this, extras.getString(EXTRA_COUNTDOWN_UUID));
        }
        // if both have failed, create a new model
        if (model == null) {
            model = new CountdownModel();
        }

        // it's important to set the theme before any view is created
        setTheme(Utility.getThemeResourceId(model.getThemeColor()));
        super.onCreate(savedInstanceState);

        String title = model.getTitle().equals("") ? getResources().getString(R.string.app_name) : model.getTitle();
        // setup visual effects in Overview/Recent screen
        setTaskDescription(new ActivityManager.TaskDescription(
                title,
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_mono),
                getResources().getColor(Utility.getThemeColorResourceId(model.getThemeColor()))
        ));

        cardView = (CardView) findViewById(R.id.card);
        cardView.setClickable(false);
        cardViewHolder = new CountdownCardViewHolder(cardView);
        cardViewHolder.bind(this, model);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndExit();
            }
        });

        Button testButton = (Button) findViewById(R.id.btn_change_theme);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemeColor newColor = ThemeColor.values()[Utility.randInt(0, ThemeColor.values().length - 1)];
                while (newColor == model.getThemeColor()) {
                    newColor = ThemeColor.values()[Utility.randInt(0, ThemeColor.values().length - 1)];
                }
                model.setThemeColor(newColor);
                recreate();
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String serializedCountdown = SerializationManager.getInstance().serialize(model);
        outState.putString(STATE_COUNTDOWN_MODEL, serializedCountdown);
    }

    private void saveAndExit() {
        // TODO: save countdown model
        ActivityManager.AppTask task = getRunningTask(this, model.getUuid());
        if (task != null) {
            task.finishAndRemoveTask();
        } else {
            finish();
        }
    }

    private void deleteAndExit() {
        // TODO: delete countdown model
        ActivityManager.AppTask task = getRunningTask(this, model.getUuid());
        if (task != null) {
            task.finishAndRemoveTask();
        } else {
            finish();
        }
    }

    public static void startActivity(Context context, CountdownModel model) {
        String uuid = model == null ? null : model.getUuid();
        // start a new activity only when the task with this uuid does not exist
        ActivityManager.AppTask task = getRunningTask(context, uuid);
        if (task != null) {
            task.moveToFront();
        } else {
            Intent intent = new Intent(context.getApplicationContext(), DetailActivity.class);
            if (model != null) {
                intent.putExtra(EXTRA_COUNTDOWN_UUID, model.getUuid());
                intent.putExtra(EXTRA_COUNTDOWN_MODEL, SerializationManager.getInstance().serialize(model));
            }
            context.startActivity(intent);
        }
    }

    private static ActivityManager.AppTask getRunningTask(Context context, String uuid) {
        if (uuid == null || uuid.equals("") || Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // no document-centric activity management for pre-lollipop
            return null;
        } else {
            // find the task by the uuid in its intent
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.AppTask task : manager.getAppTasks()) {
                String taskUuid = task.getTaskInfo().baseIntent.getStringExtra(EXTRA_COUNTDOWN_UUID);
                if (uuid.equals(taskUuid)) {
                    return task;
                }
            }
            return null;
        }
    }

}
