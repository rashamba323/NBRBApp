package com.example.nbrbapp

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import android.text.format.DateFormat
import android.text.format.Time
import android.util.Log
import com.example.adapter.CurrencyListAdapter
import com.example.api.Api
import com.example.model.CurrencyItem
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.text.SimpleDateFormat
import java.util.*


class App : Application() {

    lateinit var api : Api

    override fun onCreate() {
        super.onCreate()

        val retrofit = Retrofit.Builder()
                .baseUrl("http://www.nbrb.by/Services/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        api = retrofit.create(Api::class.java)

    }

    fun getDate() : String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        val date = dateFormat.format(Calendar.getInstance().time).toString()
        return date
    }

    fun getDateYesterday() : String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        val calendar  = Calendar.getInstance()
        calendar.add(Calendar.DATE, -1)
        val date = dateFormat.format(calendar.time).toString()
        return date
    }

    fun getDateTomorrow() : String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        val calendar  = Calendar.getInstance()
        calendar.add(Calendar.DATE, +1)
        var date = dateFormat.format(calendar.time).toString()
        return date
    }

}