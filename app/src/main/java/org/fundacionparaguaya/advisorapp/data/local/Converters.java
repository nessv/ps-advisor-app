package org.fundacionparaguaya.advisorapp.data.local;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.fundacionparaguaya.advisorapp.models.IndicatorQuestion;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Some converter utilities for serializing objects that need to be stored in the local database.
 */

public class Converters {
    @TypeConverter
    public static String fromIndicatorQuestions(List<IndicatorQuestion> questions) {
        return new Gson().toJson(questions);
    }

    @TypeConverter
    public static List<IndicatorQuestion> toIndicatorQuestions(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
}
