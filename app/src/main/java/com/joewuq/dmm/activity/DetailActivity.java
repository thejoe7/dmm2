package com.joewuq.dmm.activity;

import android.animation.Animator;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
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

import at.markushi.ui.RevealColorView;

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
    RevealColorView revealColorView;

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


        // setup visual effects in Overview/Recent screen
        setTaskDescription(createTaskDescription(this, model));

        cardView = (CardView) findViewById(R.id.card);
        cardView.setClickable(false);
        cardViewHolder = new CountdownCardViewHolder(cardView);
        cardViewHolder.bind(this, model, false);

        revealColorView = (RevealColorView) findViewById(R.id.rcv_reveal);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndExit();
            }
        });
        fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int action = event.getAction();
                float normal_elevation = getResources().getDimension(R.dimen.fab_elevation_lollipop_normal);
                float lifted_elevation = getResources().getDimension(R.dimen.fab_elevation_lollipop_lifted);
                float from_elevation = action == MotionEvent.ACTION_UP ? lifted_elevation : normal_elevation;
                float to_elevation = action == MotionEvent.ACTION_UP ? normal_elevation : lifted_elevation;
                if (v.getElevation() != to_elevation) {
                    Utility.getElevationAnimator(DetailActivity.this, v, from_elevation, to_elevation).start();
                }
                return false;
            }
        });

        final Button testButton = (Button) findViewById(R.id.btn_change_theme);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemeColor newColor = ThemeColor.values()[Utility.randInt(0, ThemeColor.values().length - 1)];
                while (newColor == model.getThemeColor()) {
                    newColor = ThemeColor.values()[Utility.randInt(0, ThemeColor.values().length - 1)];
                }
                changeTheme(newColor, testButton);
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_detail;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_about:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String serializedCountdown = SerializationManager.getInstance().serialize(model);
        outState.putString(STATE_COUNTDOWN_MODEL, serializedCountdown);
    }

    private void saveAndExit() {
        // TODO: save countdown model
        finishAndRemoveTask();
    }

    private void deleteAndExit() {
        // TODO: delete countdown model
        finishAndRemoveTask();
    }

    private void changeTheme(ThemeColor themeColor, View v) {
        model.setThemeColor(themeColor);

        // get the original intent and store the current countdown object
        final Intent intent = getIntent();
        intent.putExtra(EXTRA_COUNTDOWN_MODEL, SerializationManager.getInstance().serialize(model));

        // compute the origin of the reveal
        final int[] p1 = new int[2];
        revealColorView.getLocationOnScreen(p1);
        final int[] p2 = new int[2];
        v.getLocationOnScreen(p2);
        int x = p2[0] - p1[0] + v.getWidth() / 2;
        int y = p2[1] - p1[1] + v.getHeight() / 2;

        // initiate reveal
        revealColorView.reveal(x, y, getResources().getColor(R.color.white), 0, getResources().getInteger(android.R.integer.config_mediumAnimTime),
                new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) { }

            @Override
            public void onAnimationEnd(Animator animation) {
                // recreate activity after the animation
                getWindow().setStatusBarColor(getResources().getColor(R.color.white));

                finishAndRemoveTask();

                startActivity(intent);
                overridePendingTransition(0, 0);

            }

            @Override
            public void onAnimationCancel(Animator animation) { }

            @Override
            public void onAnimationRepeat(Animator animation) { }
        });
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

    private static ActivityManager.TaskDescription createTaskDescription(Context context, CountdownModel model) {
        String title = model.getTitle().equals("") ? context.getResources().getString(R.string.app_name) : model.getTitle();
        return new ActivityManager.TaskDescription(
                title,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_mono),
                context.getResources().getColor(Utility.getThemeColorResourceId(model.getThemeColor()))
        );
    }

}
