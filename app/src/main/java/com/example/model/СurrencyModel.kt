package com.example.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.util.*

@Root(name = "DailyExRates")
class СurrencyModel @JvmOverloads constructor (){

    @get:Attribute(name = "Date")
    @set:Attribute(name = "Date")
    var date: String? = null

    @get:ElementList(name="Currency", inline=true)
    @set:ElementList(name="Currency", inline=true)
    var currencyItems: ArrayList<CurrencyItem>? = null

}

