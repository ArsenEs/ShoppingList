package com.srenes.shoppinglist.presentation.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.srenes.shoppinglist.R
import com.srenes.shoppinglist.databinding.ActivityShopItemBinding
import com.srenes.shoppinglist.domain.ShopItem
import com.srenes.shoppinglist.presentation.viewModels.ShopItemViewModel

class ShopItemActivity : AppCompatActivity() {

    lateinit var binding: ActivityShopItemBinding
    lateinit var viewModel : ShopItemViewModel
    var screenMode = ""
    var shopitemId = ShopItem.UNDEFINED_ID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parseIntent()
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        textWatcher()
        changeRightMode()
        observeViewModel()

    }
    private fun changeRightMode(){
        when(screenMode){
            MODE_EDIT ->launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }
    private fun observeViewModel(){
        viewModel.errorInputCountLD.observe(this){
            val message = if (it){
                getString(R.string.error_input_count)
            }else{
                null
            }
            binding.countTv.error = message
        }
        viewModel.errorInputNameLD.observe(this){
            val message = if (it){
                getString(R.string.error_input_name)
            }else{
                null
            }
            binding.titleTv.error = message
        }
        viewModel.shouldCloseScreen.observe(this){
            finish()
        }
    }
    private fun textWatcher(){
        binding.etName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetInputNameError()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        binding.etCount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetInputCountError()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }
    private fun launchEditMode(){
        binding.apply {
            viewModel.getShopItem(shopitemId)
            viewModel.shopItemLD.observe(this@ShopItemActivity){
                etName.setText(it.name)
                etCount.setText(it.count.toString())
            }
            saveBt.setOnClickListener(){
                viewModel.editShopItem(etName.text?.toString(), etCount.text?.toString())
            }
        }

    }
    private fun launchAddMode(){
        binding.apply {
            saveBt.setOnClickListener(){
                viewModel.addShopItem(etName.text?.toString(), etCount.text?.toString())
            }
        }
    }
    private fun parseIntent(){
        if(!intent.hasExtra(EXTRA_MODE)){
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT){
            throw RuntimeException("unknown screnn mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT){
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)){
                throw RuntimeException("param shop item is absent")
            }
            shopitemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID,ShopItem.UNDEFINED_ID)
        }
    }


    companion object{
        private const val EXTRA_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"

        fun newIntentAddItem(context: Context) : Intent{
            val intent = Intent(context,ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId : Int) : Intent{
            val intent = Intent(context,ShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID,shopItemId)
            return intent
        }

    }
}