package com.srenes.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.srenes.shoppinglist.R

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel

    private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this){
            Log.d("MainLog", it.toString())
            if (count==0){
                count=1
                val item = it[0]
                viewModel.changeEnabled(item)
            }
        }
    }
}
