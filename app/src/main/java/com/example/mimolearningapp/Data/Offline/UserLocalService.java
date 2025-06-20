package com.example.mimolearningapp.Data.Offline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mimolearningapp.Model.LoginResponse;

public class UserLocalService {

    private SQLiteDatabase db;

    public UserLocalService(Context context) {
        db = new SQLiteHelper(context).getWritableDatabase();
    }

    public void saveUser(LoginResponse user) {
        db.delete("user", null, null);
        ContentValues values = new ContentValues();
        values.put("id", user.getUserId());
        values.put("name", user.getName());
        values.put("email", user.getEmail());
        values.put("role", user.getRole());
        db.insert("user", null, values);
    }

    public LoginResponse getUser() {
        Cursor cursor = db.rawQuery("SELECT * FROM user", null);
        if (cursor.moveToFirst()) {
            LoginResponse user = new LoginResponse();
            user.setUserId(cursor.getInt(0));
            user.setName(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setRole(cursor.getString(3));
            cursor.close();
            return user;
        }
        cursor.close();
        return null;
    }

    public void clearUser() {
        db.delete("user", null, null);
    }


}