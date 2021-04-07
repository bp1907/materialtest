package com.example.materialtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    val fruits = mutableListOf(Fruit("Apple", R.mipmap.ic_launcher),
        Fruit("Banana", R.mipmap.ic_launcher),
        Fruit("Orange", R.mipmap.ic_launcher),
        Fruit("Watermelon", R.mipmap.ic_launcher),
        Fruit("Pear", R.mipmap.ic_launcher),
        Fruit("Grape", R.mipmap.ic_launcher),
        Fruit("Pineapple", R.mipmap.ic_launcher),
        Fruit("Cherry", R.mipmap.ic_launcher),
        Fruit("Mango", R.mipmap.ic_launcher))

    val fruitList = ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_launcher_foreground)
        }
        navView.setCheckedItem(R.id.navCall)
        navView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawers()
            true
        }

        floatingBtn.setOnClickListener {
            Snackbar.make(it, "data delete", Snackbar.LENGTH_SHORT)
                .setAction("undo") {
                    Toast.makeText(this, "data restored", Toast.LENGTH_SHORT).show()
                }.show()
        }


        initFruits()
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager
        val adapter = FruitAdapter(this, fruitList)
        recyclerView.adapter = adapter

        swipeRefresh.setColorSchemeResources(R.color.design_default_color_primary)
        swipeRefresh.setOnRefreshListener {
            refreshFruits(adapter)
        }
    }

    private fun refreshFruits(adapter: FruitAdapter) {
        thread {
            Thread.sleep(2000)
            runOnUiThread {
                initFruits()
                adapter.notifyDataSetChanged()
                swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun initFruits() {
        fruitList.clear()
        repeat(20) {
            val index = (0 until fruits.size).random()
            fruitList.add(fruits[index])
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> drawerLayout.openDrawer(GravityCompat.START)
            R.id.backup -> Toast.makeText(this, "you clicked Backup", Toast.LENGTH_SHORT).show()
            R.id.delete -> Toast.makeText(this, "you clicked Delete", Toast.LENGTH_SHORT).show()
            R.id.settings -> Toast.makeText(this, "you clicked Settings", Toast.LENGTH_SHORT).show()
        }
        return true
    }
}