package com.android.app.android_baraka_demo.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.app.android_baraka_demo.R
import com.android.app.android_baraka_demo.presentation.main.adapters.MainAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel:MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = getLayoutManager()
        recyclerView.isNestedScrollingEnabled = true

        mainViewModel.getSectionalsList().observe(this) {
            recyclerView.adapter = MainAdapter(this@MainActivity, it)
        }

        CoroutineScope(Dispatchers.IO).launch {
            //mainViewModel.getTickersSection()
            mainViewModel.getNewsSection()
        }
    }

    private fun getLayoutManager() =
        LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
}