package com.example.nbrbapp

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import com.example.adapter.SettingsListAdapter
import com.example.model.CurrencyItem
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class SettingsActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var recyclerView: RecyclerView
    var settingsListAdapter = SettingsListAdapter()
    private var listItems = ArrayList<CurrencyItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        listItems = intent.getParcelableArrayListExtra<CurrencyItem>("list")

        recyclerView = findViewById(R.id.list_item)
        recyclerView.adapter = settingsListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setItemViewCacheSize(30)
        settingsListAdapter.setList(listItems)

        val itemTouchHelper = ItemTouchHelper(object :  ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {

            override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onMove(recyclerView: RecyclerView, dragged: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder ): Boolean {
                val draggedPosition = dragged.adapterPosition
                val targetPosition = target.adapterPosition
                Collections.swap(listItems, draggedPosition, targetPosition)
                settingsListAdapter.notifyItemMoved(draggedPosition, targetPosition)
                return true
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
//        bindPositions()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_settings){
            val intent = Intent(this, MainActivity::class.java)
            intent.putParcelableArrayListExtra("list", listItems)
            startActivity(intent)
        }
        return true
    }

//    fun bindPositions(){
//        for(i in listItems.indices){
//            sharedPreferences.edit().putInt("${listItems[i].charCode.toString()}_position", i).apply()
//        }
//    }
}