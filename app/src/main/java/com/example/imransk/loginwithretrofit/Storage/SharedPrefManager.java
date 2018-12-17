package com.example.imransk.loginwithretrofit.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.imransk.loginwithretrofit.ModelClass.User;

public class SharedPrefManager {

    private  static final String SHARED_PREF_NAME= "my_shared_preff";
    private  static SharedPrefManager mInstance;
    private Context context;

    public SharedPrefManager(Context context) {
        this.context = context;
    }
    public static synchronized SharedPrefManager getInstance(Context context){

        if (mInstance==null){
            mInstance=new SharedPrefManager(context);
        }
        return mInstance;
    }
    public void saveUser(User user){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id",user.getId());
        editor.putString("email",user.getEmail());
        editor.putString("name",user.getName());
        editor.putString("school",user.getSchool());
        editor.apply();
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,context.MODE_PRIVATE);
        if (sharedPreferences.getInt("id",-1) !=-1){
            return true;
        }else{
            return false;
        }

    }

    public User getUser(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,context.MODE_PRIVATE);
        User user=new User(
                sharedPreferences.getInt("id",-1)
                ,sharedPreferences.getString("email",null)
                ,sharedPreferences.getString("name",null)
                ,sharedPreferences.getString("school",null)
        );
        return user;
    }

    public void clear(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

    }
}
