package travel.com.store;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mostafa_anter on 9/26/16.
 */

public class TravellawyPrefStore {
    private static final String PREFKEY = "ThePlacePrefStore";
    private SharedPreferences thePlacePrefStore;

    public TravellawyPrefStore(Context context){
        thePlacePrefStore = context.getSharedPreferences(PREFKEY, Context.MODE_PRIVATE);
    }

    public void clearPreference(){
        SharedPreferences.Editor editor = thePlacePrefStore.edit();
        editor.clear().apply();
    }

    public void addPreference(String key, String value){
        SharedPreferences.Editor editor = thePlacePrefStore.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void addPreference(String key, int value){
        SharedPreferences.Editor editor = thePlacePrefStore.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void removePreference(String key){
        SharedPreferences.Editor editor = thePlacePrefStore.edit();
        editor.remove(key);
        editor.apply();
    }

    public String getPreferenceValue(String key){
        return thePlacePrefStore.getString(key, "");
    }

    public int getIntPreferenceValue(String key){
        return thePlacePrefStore.getInt(key, 0);
    }
}
