package com.srenes.shoppinglist.presentation.activity

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.TooltipCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.srenes.shoppinglist.R
import com.srenes.shoppinglist.RVAdapter
import com.srenes.shoppinglist.presentation.viewModels.MainViewModel

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel

    lateinit var recyclerView: RecyclerView
    lateinit var shopListadapter: RVAdapter
    lateinit var buttonShopAddItem: FloatingActionButton
    lateinit var imageView : ImageView

    lateinit var infoButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.emptyCartImage)
        infoButton = findViewById(R.id.infoButton)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setRecyclerView()
        viewModel.shopList.observe(this) {
            shopListadapter.submitList(it)

        }
        buttonShopAddItem = findViewById(R.id.button_add_shop_item)
        buttonShopAddItem.setOnClickListener {
            val intent = ShopItemActivity.newIntentAddItem(this)
            startActivity(intent)
        }

        val balloon = Balloon.Builder(this)
            .setHeight(BalloonSizeSpec.WRAP)
            .setWidth(220)
            .setText("Свайпни, чтобы удалить! \n" +
                    "Зажми, чтобы отметить!")
            .setTextSize(15f)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
            .setArrowSize(10)
            .setArrowPosition(0.5f)
            .setPadding(12)
            .setCornerRadius(8f)
            .setBackgroundColorResource(R.color.Backgroun)
            .setBalloonAnimation(BalloonAnimation.ELASTIC)
            .setLifecycleOwner(this)
            .build()

        infoButton.setOnClickListener{
            balloon.showAlignBottom(infoButton)
        }





    }

    private fun showToolTip(view:View, gravity: Int) {


    }

    private fun updateImageVisibility() {
        val itemCount = shopListadapter.itemCount
        Log.d("counItem","$itemCount")
        if (itemCount <= 1){
            imageView.visibility = View.GONE
        }else{
            imageView.visibility = View.VISIBLE
        }
    }

    fun setRecyclerView() {
        recyclerView = findViewById(R.id.rv_shop_list)
        with(recyclerView) {
            shopListadapter = RVAdapter()
            adapter = shopListadapter.apply {
                setDataChangeListener(object : RVAdapter.DataChangeListener {
                    override fun onDataChange() {

                    }
                })
                registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                    override fun onChanged() {
                        super.onChanged()

                    }
                })
            }
            recycledViewPool.setMaxRecycledViews(
                RVAdapter.VIEW_TYPE_ENABLED, RVAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                RVAdapter.VIEW_TYPE_DISABLED, RVAdapter.MAX_POOL_SIZE
            )
        }

        

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
                val item = shopListadapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)


            }
        }


        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun setUpClickListener() {
        shopListadapter.onClickShopItemListener = {
            val intent = ShopItemActivity.newIntentEditItem(this, it.id)
            startActivity(intent)
        }
    }

    private fun setUpLongListener() {
        shopListadapter.onLongShopItemListener = {
            viewModel.changeEnabled(it)
        }
    }


    //Tooltip


}
