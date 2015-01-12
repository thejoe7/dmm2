package com.joewuq.dmm.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.joewuq.dmm.CountdownModel;
import com.joewuq.dmm.R;
import com.joewuq.dmm.SerializationManager;

/**
 * Created by Joe Wu on 1/11/15.
 */
public class DetailActivity extends ToolbarActivity {

    public static final String TAG = DetailActivity.class.getName();

    public static final String EXTRA_COUNTDOWN_UUID = "COUNTDOWN_UUID";
    public static final String EXTRA_COUNTDOWN_DELETED = "COUNTDOWN_DELETED";

    private static final String STATE_COUNTDOWN_MODEL = "COUNTDOWN_MODEL";

    private CountdownModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getString(EXTRA_COUNTDOWN_UUID) != null) {
            // TODO: load countdown model from preference
        } else if (savedInstanceState != null && savedInstanceState.getString(STATE_COUNTDOWN_MODEL) != null) {
            model = SerializationManager.getInstance().deserialize(savedInstanceState.getString(STATE_COUNTDOWN_MODEL), CountdownModel.class);
        } else {
            model = new CountdownModel();
        }

        String title = model.getTitle().equals("") ? getResources().getString(R.string.app_name) : model.getTitle();
        // setup visual effects in Overview/Recent screen
        setTaskDescription(new ActivityManager.TaskDescription(
                title,
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_mono),
                getResources().getColor(R.color.dmm_primary)
        ));
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_detail;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        String serializedCountdown = SerializationManager.getInstance().serialize(model);
        outState.putString(STATE_COUNTDOWN_MODEL, serializedCountdown);
        outPersistentState.putString(STATE_COUNTDOWN_MODEL, serializedCountdown);
    }

    public static void startActivityForResult(Activity activity, int requestCode, CountdownModel model) {
        Intent intent = new Intent(activity.getApplicationContext(), DetailActivity.class);
        if (model != null) {
            intent.putExtra(EXTRA_COUNTDOWN_UUID, model.getUuid());
        }
        activity.startActivityForResult(intent, requestCode);
    }

}
