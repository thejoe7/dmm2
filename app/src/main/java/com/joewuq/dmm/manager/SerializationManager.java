package com.joewuq.dmm.manager;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import org.joda.time.DateTime;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by Joe Wu on 1/3/15.
 */
public class SerializationManager {
    public static final String TAG = SerializationManager.class.getName();

    private static SerializationManager instance = new SerializationManager();

    private Gson gson;

    private SerializationManager() {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeTypeAdapter());
        gson = gsonBuilder.create();
    }

    public static SerializationManager getInstance() {
        return instance;
    }

    public <T> T deserialize(String jsonString, Class<T> classType) {
        try {
            return gson.fromJson(jsonString, classType);
        } catch (JsonSyntaxException e) {
            Log.w(TAG, String.format("deserialize(String, Class<%s>) failed on %s", classType.getName(), jsonString));
            return null;
        }
    }

    public <T> String serialize(T object) {
        return gson.toJson(object);
    }

    private static class DateTimeTypeAdapter implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {

        @Override
        public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                return new DateTime(json.getAsString());
            } catch (IllegalArgumentException e) {
                // it might come in formatted as a java.util.Date
                // give it a try
                Date date = context.deserialize(json, Date.class);
                return new DateTime(date);
            }
        }

        @Override
        public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }
    }
}
