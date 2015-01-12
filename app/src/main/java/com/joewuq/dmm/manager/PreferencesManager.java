package com.joewuq.dmm.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.joewuq.dmm.CountdownModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe Wu on 1/11/15.
 */
public class PreferencesManager {
    public static final String TAG = PreferencesManager.class.getName();

    private static final String PREFS_SETTINGS = "settings";
    private static final String PREFS_APP_DATA = "app_data";

    private static final String FIELD_COUNTDOWN_PREFIX = "COUNTDOWN_";

    private static PreferencesManager instance = new PreferencesManager();

    private PreferencesManager() {
        // private empty contructor
    }

    public static PreferencesManager getInstance() {
        return instance;
    }

    public CountdownModel loadCountdownModel(Context context, String uuid) {
        if (uuid == null || uuid.equals("")) {
            return null;
        } else {
            SharedPreferences data = context.getSharedPreferences(PREFS_APP_DATA, Context.MODE_PRIVATE);
            String json = data.getString(FIELD_COUNTDOWN_PREFIX + uuid.toUpperCase(), "");
            return SerializationManager.getInstance().deserialize(json, CountdownModel.class);
        }
    }

    public void saveCountdownModel(Context context, CountdownModel model) {
        SharedPreferences data = context.getSharedPreferences(PREFS_APP_DATA, Context.MODE_PRIVATE);
        String json = SerializationManager.getInstance().serialize(model);
        SharedPreferences.Editor editor = data.edit();
        editor.putString(FIELD_COUNTDOWN_PREFIX + model.getUuid().toUpperCase(), json);
        // use apply() instead of commit() for its asynchronicity
        editor.apply();
    }

    public List<CountdownModel> loadAllCountdownModels(Context context) {
        List<CountdownModel> list = new ArrayList<CountdownModel>();
        SharedPreferences data = context.getSharedPreferences(PREFS_APP_DATA, Context.MODE_PRIVATE);
        SerializationManager serializationManager = SerializationManager.getInstance();
        for (Object o : data.getAll().values()) {
            String json = o.toString();
            CountdownModel model = serializationManager.deserialize(json, CountdownModel.class);
            if (model != null) {
                list.add(model);
            }
        }
        return list;
    }

}
