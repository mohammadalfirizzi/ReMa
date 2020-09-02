package com.example.osmaini.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.osmaini.Login;

import java.util.HashMap;

public class SessionManager {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public static final String KEY_ID = "id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_ROLE = "role";
    public static final String KEY_BIDANG = "bidang_kode";
    private static final String is_login = "logginstatus";
    private final String SHARE_NAME = "logginsession";
    private final int MODE_PRIVATE = 0;
    private final Context _context;

    public SessionManager(Context context) {
        this._context = context;
        sp = context.getSharedPreferences(SHARE_NAME, MODE_PRIVATE);
        editor = sp.edit();
    }

    public void storeLogin(String id, String username, String role, String bidang_kode) {
        editor.putBoolean(is_login, true);
        editor.putString(KEY_ID, id);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_ROLE, role);
        editor.putString(KEY_BIDANG, bidang_kode);
        editor.commit();
    }

    public HashMap getDetailLogin() {
        HashMap<String, String> map = new HashMap<>();
        map.put(KEY_ID, sp.getString(KEY_ID, null));
        map.put(KEY_USERNAME, sp.getString(KEY_USERNAME, null));
        map.put(KEY_ROLE, sp.getString(KEY_ROLE, null));
        map.put(KEY_BIDANG, sp.getString(KEY_BIDANG, null));
        return map;
    }
    public void checkLogin(){
        if (!this.Loggin()){
            Intent Login = new Intent(_context, com.example.osmaini.Login.class);
            Login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(Login);
        }
    }
    public void logout(){
        editor.clear();
        editor.commit();
        SharedPreferences preferences = _context.getSharedPreferences("checkbox", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("remember","false");
        editor.apply();
    }

    public Boolean Loggin(){
        return sp.getBoolean(is_login,false);
    }
}
