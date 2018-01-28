package org.fundacionparaguaya.advisorapp.data.local;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.fundacionparaguaya.advisorapp.models.BackgroundQuestion;
import org.fundacionparaguaya.advisorapp.models.IndicatorOption;
import org.fundacionparaguaya.advisorapp.models.IndicatorQuestion;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Some converter utilities for serializing objects that need to be stored in the local database.
 */

public class Converters {
    @TypeConverter
    public static String fromBackgroundQuestions(List<BackgroundQuestion> questions) {
        return new Gson().toJson(questions);
    }

    @TypeConverter
    public static List<BackgroundQuestion> toBackgroundQuestions(String value) {
        Type listType = new TypeToken<ArrayList<BackgroundQuestion>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromIndicatorQuestions(List<IndicatorQuestion> questions) {
        return new Gson().toJson(questions);
    }

    @TypeConverter
    public static List<IndicatorQuestion> toIndicatorQuestions(String value) {
        Type listType = new TypeToken<ArrayList<IndicatorQuestion>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromBackgroundResponses(Map<BackgroundQuestion, String> responses) {
        return new Gson().toJson(responses);
    }

    @TypeConverter
    public static Map<BackgroundQuestion, String> toBackgroundResponses(String value) {
        Type listType = new TypeToken<Map<BackgroundQuestion, String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromIndicatorResponse(Map<IndicatorQuestion, IndicatorOption> responses) {
        return new Gson().toJson(responses);
    }

    @TypeConverter
    public static Map<IndicatorQuestion, IndicatorOption> toIndicatorResponse(String value) {
        Type listType = new TypeToken<Map<IndicatorQuestion, String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
}
