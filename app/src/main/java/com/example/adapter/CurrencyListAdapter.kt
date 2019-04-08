package com.example.adapter

import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import com.example.model.CurrencyItem
import com.example.nbrbapp.R
import java.util.*


class CurrencyListAdapter : RecyclerView.Adapter<CurrencyListAdapter.CurrencyViewHolder>() {

    private var listItems = ArrayList<CurrencyItem>()
    var listItemsDelete = ArrayList<CurrencyItem>()
    lateinit var sharedPreferences: SharedPreferences


    fun setItems(list: ArrayList<CurrencyItem>){
        this.listItems = list

        Log.e("ITEMS", "size ${list.size}")
        for(i in listItems.indices){
        Log.e("LOAD DATA ADAPTER SET", "addItems rateSecond ${list[i].rate} ")
        }

        notifyDataSetChanged()
    }

    fun addItems(list: ArrayList<CurrencyItem>){
        for(i in listItems.indices){
//            val string : String = list[i].rate!!
            listItems[i].rateSecond = list[i].rate!!
            Log.e("LOAD DATA ADAPTER ADD", "addItems rateSecond ${list[i].rate} ")
        }
        notifyDataSetChanged()

    }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        Log.e("ITEMS", "size ${listItems.size}")
        return CurrencyViewHolder(view)
    }

    override fun getItemCount(): Int = listItems.size

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {

            Log.e("BIND", "${listItems[position].charCode}")
            holder.bind(listItems[position])

    }

    class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var charCode: TextView = itemView.findViewById(R.id.item_char_code)
        var name: TextView = itemView.findViewById(R.id.item_name)
        var rate: TextView = itemView.findViewById(R.id.item_rate_one)
        var rateSecond: TextView = itemView.findViewById(R.id.item_rate_second)

        fun bind(list: CurrencyItem){

                charCode.text = list.charCode.toString()
                name.text = list.name.toString()
                rate.text = list.rate.toString()
                rateSecond.text = list.rateSecond.toString()

        }

    }
}