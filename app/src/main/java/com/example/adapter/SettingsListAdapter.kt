package com.example.adapter

import android.app.Application
import android.content.SharedPreferences
import android.os.SharedMemory
import android.preference.PreferenceManager
import android.support.v7.view.menu.MenuView
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.model.CurrencyItem
import com.example.nbrbapp.App
import com.example.nbrbapp.MainActivity
import com.example.nbrbapp.R
import io.reactivex.subjects.PublishSubject
import java.util.*
import kotlin.coroutines.coroutineContext

class SettingsListAdapter : RecyclerView.Adapter<SettingsListAdapter.SettingsViewHolder>() {

    private var listItems = ArrayList<CurrencyItem>()
    lateinit var sharedPreferences: SharedPreferences

    fun setList(list: ArrayList<CurrencyItem>){
        this.listItems = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): SettingsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_settings, parent, false)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(parent.context)
        return SettingsListAdapter.SettingsViewHolder(view)
    }

    override fun getItemCount(): Int = listItems.size

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {

        holder.bind(listItems[position])
        sharedPreferences.edit().putInt(listItems[position].charCode.toString(), position)

        if(sharedPreferences.getBoolean(listItems[position].charCode.toString(), false)) {
            holder.switch.setChecked(true)
        }
    }

    class SettingsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(itemView.context)

        var charCode: TextView = itemView.findViewById(R.id.item_char_code)
        var name: TextView = itemView.findViewById(R.id.item_name)
        var switch: Switch = itemView.findViewById(R.id.item_settings_switch)

        fun bind(list: CurrencyItem){
            charCode.text = list.charCode.toString()
            name.text = list.name.toString()

            switch.setOnCheckedChangeListener{buttonView, isChecked ->
                if(isChecked){
                    sharedPreferences.edit().putBoolean(list.charCode.toString(), true).apply()
                } else {
                    sharedPreferences.edit().putBoolean(list.charCode.toString(), false).apply()
                }

            }

        }

    }

}