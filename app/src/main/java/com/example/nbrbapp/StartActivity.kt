package com.example.nbrbapp

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager

class StartActivity : Activity() {
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        if(sharedPreferences.getBoolean("first_lounch", true)){
            sharedPreferences.edit().putBoolean("USD", true).apply()
            sharedPreferences.edit().putBoolean("EUR", true).apply()
            sharedPreferences.edit().putBoolean("RUB", true).apply()
        }


        var intent = Intent(this, MainActivity:: class.java)
        startActivity(intent)
    }
}
