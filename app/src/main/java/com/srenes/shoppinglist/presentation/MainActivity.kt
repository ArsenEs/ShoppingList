package com.srenes.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.srenes.shoppinglist.R
import com.srenes.shoppinglist.RVAdapter
import com.srenes.shoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel

    lateinit var recyclerView: RecyclerView
    lateinit var shopListadapter : RVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setRecyclerView()
        viewModel.shopList.observe(this){
            shopListadapter.shopItemList = it

        }

    }

    fun setRecyclerView(){
        recyclerView = findViewById(R.id.rv_shop_list)
        with(recyclerView) {
        shopListadapter = RVAdapter()
        adapter = shopListadapter
        recycledViewPool.setMaxRecycledViews(
            RVAdapter.VIEW_TYPE_ENABLED,RVAdapter.MAX_POOL_SIZE
        )
        recycledViewPool.setMaxRecycledViews(
            RVAdapter.VIEW_TYPE_DISABLED,RVAdapter.MAX_POOL_SIZE
        ) }

        setUpLongListener()
        setUpClickListener()

        setUpSwipeListener()

    }

    private fun setUpSwipeListener() {
        var callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopListadapter.shopItemList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun setUpClickListener() {
        shopListadapter.onClickShopItemListener = {
            Toast.makeText(this@MainActivity, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpLongListener() {
        shopListadapter.onLongShopItemListener = {
            viewModel.changeEnabled(it)
        }
    }
}
