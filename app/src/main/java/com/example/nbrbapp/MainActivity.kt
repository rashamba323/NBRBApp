package com.example.nbrbapp

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.adapter.CurrencyListAdapter
import com.example.api.Api
import com.example.model.CurrencyItem
import com.example.model.СurrencyModel
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var currencyListAdapter = CurrencyListAdapter()
    var listItems = ArrayList<CurrencyItem>()
    lateinit var dateFirst: TextView
    lateinit var dateSecond: TextView
    lateinit var sharedPreferences: SharedPreferences
    lateinit var api: Api
    lateinit var menuItem: MenuItem
    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        api =  (application as App).api

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        sharedPreferences.edit().putBoolean("first_lounch", false).apply()

        recycler = findViewById(R.id.list_item)
        recycler.adapter = currencyListAdapter
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)

        dateFirst = findViewById(R.id.first_date)
        dateSecond = findViewById(R.id.second_date)

        loadData()
    }

    override fun onResume() {
        super.onResume()
        loadData()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        menuItem = menu.findItem(R.id.action_settings)
        menuItem.setVisible(false)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_settings){
            val intent = Intent(this, SettingsActivity::class.java)
            intent.putParcelableArrayListExtra("list", listItems)
            startActivity(intent)
        }
        return true
    }

    fun sortList(list: ArrayList<CurrencyItem>): ArrayList<CurrencyItem>{
        var sortedList = ArrayList<CurrencyItem>()
        for(i in list.indices){
            val check = sharedPreferences.getBoolean(list[i].charCode.toString(), false)
            if(check == true){
                sortedList.add(list[i])
            }
        }
        return sortedList
    }

//    fun swapItems(list: ArrayList<CurrencyItem>): ArrayList<CurrencyItem>{
//        for(i in list.indices){
//            Collections.swap(list, sharedPreferences.getInt("${listItems[i].charCode.toString()}_position", 0), i)
//        }
//        return list
//    }

//    fun setDefultItemsPosition(list: ArrayList<CurrencyItem>){
//        for(i in list.indices){
//            sharedPreferences.edit().putInt("${listItems[i].charCode.toString()}_position", i).apply()
//        }
//    }

    @SuppressLint("CheckResult")
    fun loadData(){

        api.getCurrency(App().getDate())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                dateFirst.text = App().getDate()
                listItems = it.currencyItems!!
                currencyListAdapter.setItems(sortList(it.currencyItems!!))

            },{

                Log.e("LOAD DATA ERROR", "OnError ${it.toString()}")
                val toast = Toast.makeText(this, "Error ${it.toString()}", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER,0,0)
                toast.show()

            },{

                api.getCurrencyTomorrow(App().getDateTomorrow())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({

                        dateSecond.text = App().getDateTomorrow()
                        currencyListAdapter.addItems(sortList(it.currencyItems!!))
                        menuItem.setVisible(true)

                    },{

                        Log.e("LOAD DATA TOMORROW", "OnError ${it.toString()}")
                        api.getCurrencyYesterday(App().getDateYesterday())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({

                                dateSecond.text = App().getDateYesterday()
                                currencyListAdapter.addItems(sortList(it.currencyItems!!))

                            },{

                                Log.e("LOAD DATA YESTERDAY", "OnError ${it.toString()}")

                            },{

                            })
                    }) })
    }
}
