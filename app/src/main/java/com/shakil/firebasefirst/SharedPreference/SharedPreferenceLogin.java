package com.shakil.firebasefirst.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.shakil.firebasefirst.R;

public class SharedPreferenceLogin {
    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPreferenceLogin(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences(context.getResources().getString(R.string.Registration_preference), Context.MODE_PRIVATE);
    }
    public void writeRegistrationinStatus(Boolean status){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.Registration_status_preference),status);
        editor.commit();
    }
    public boolean readRegistrationString(){
        boolean status=false;
        status=sharedPreferences.getBoolean(context.getResources().getString(R.string.Registration_status_preference),status);
        return status;
    }
}