package com.example.api


import com.example.model.СurrencyModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header

interface Api {

    @GET("XmlExRates.aspx?ondate=")
    fun getCurrency(@Header("date")date: String) : Observable<СurrencyModel>

    @GET("XmlExRates.aspx?ondate=")
    fun getCurrencyYesterday(@Header("date")date: String) : Observable<СurrencyModel>

    @GET("XmlExRates.aspx?ondate=")
    fun getCurrencyTomorrow(@Header("date")date: String) : Observable<СurrencyModel>

}