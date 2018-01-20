package org.fundacionparaguaya.advisorapp.data.local;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.fundacionparaguaya.advisorapp.models.EconomicQuestion;
import org.fundacionparaguaya.advisorapp.models.IndicatorQuestion;
import org.fundacionparaguaya.advisorapp.models.PersonalQuestion;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Some converter utilities for serializing objects that need to be stored in the local database.
 */

public class Converters {
    @TypeConverter
    public static String fromPersonalQuestions(List<PersonalQuestion> questions) {
        return new Gson().toJson(questions);
    }

    @TypeConverter
    public static List<PersonalQuestion> toPersonalQuestions(String value) {
        Type listType = new TypeToken<ArrayList<PersonalQuestion>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromEconomicQuestions(List<EconomicQuestion> questions) {
        return new Gson().toJson(questions);
    }

    @TypeConverter
    public static List<EconomicQuestion> toEconomicQuestions(String value) {
        Type listType = new TypeToken<ArrayList<EconomicQuestion>>() {}.getType();
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
}
