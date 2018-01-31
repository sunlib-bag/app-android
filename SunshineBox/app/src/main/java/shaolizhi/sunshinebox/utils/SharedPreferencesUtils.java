package shaolizhi.sunshinebox.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * 由邵励治于2017/12/3创造.
 */

public class SharedPreferencesUtils {

    private static final String FILE_NAME_SAVED_IN_THE_PHONE = "cache_files";

    public static void put(Context context, String key, Object value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME_SAVED_IN_THE_PHONE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else {
            editor.putString(key, value.toString());
        }
        editor.apply();
    }

    public static String get(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME_SAVED_IN_THE_PHONE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

    public static void remove(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME_SAVED_IN_THE_PHONE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public static void clear(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME_SAVED_IN_THE_PHONE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public static boolean contains(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME_SAVED_IN_THE_PHONE, Context.MODE_PRIVATE);
        return sharedPreferences.contains(key);
    }

    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME_SAVED_IN_THE_PHONE, Context.MODE_PRIVATE);
        return sharedPreferences.getAll();
    }
}
