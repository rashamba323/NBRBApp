package com.example.model

import android.os.Parcel
import android.os.Parcelable
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import java.io.Serializable

@Root(name = "Currency")
class CurrencyItem @JvmOverloads constructor () : Parcelable {

    @get:Attribute(name = "Id")
    @set:Attribute(name = "Id")
    var id: String? = null

    @set:Element(name = "NumCode")
    @get:Element(name = "NumCode")
    var numCode: String? = null

    @set:Element(name = "CharCode")
    @get:Element(name = "CharCode")
    var charCode: String? = null

    @set:Element(name = "Scale")
    @get:Element(name = "Scale")
    var scale: String? = null

    @set:Element(name = "Name")
    @get:Element(name = "Name")
    var name: String? = null

    @set:Element(name = "Rate")
    @get:Element(name = "Rate")
    var rate: String? = ""

    var rateSecond: String? = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        numCode = parcel.readString()
        charCode = parcel.readString()
        scale = parcel.readString()
        name = parcel.readString()
        rate = parcel.readString()
        rateSecond = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(numCode)
        parcel.writeString(charCode)
        parcel.writeString(scale)
        parcel.writeString(name)
        parcel.writeString(rate)
        parcel.writeString(rateSecond)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CurrencyItem> {
        override fun createFromParcel(parcel: Parcel): CurrencyItem {
            return CurrencyItem(parcel)
        }

        override fun newArray(size: Int): Array<CurrencyItem?> {
            return arrayOfNulls(size)
        }
    }

}