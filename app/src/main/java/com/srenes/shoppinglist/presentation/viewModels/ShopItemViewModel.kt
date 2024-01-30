package com.srenes.shoppinglist.presentation.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.srenes.shoppinglist.data.ShopListRepoImpl
import com.srenes.shoppinglist.domain.AddShopItemUseCase
import com.srenes.shoppinglist.domain.EditShopItemUseCase
import com.srenes.shoppinglist.domain.GetShopItemUseCase
import com.srenes.shoppinglist.domain.ShopItem
import java.lang.Exception

class ShopItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository  = ShopListRepoImpl(application)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    //-
    private val _errorInputNameLD = MutableLiveData<Boolean>()
    val errorInputNameLD : LiveData<Boolean>
        get() = _errorInputNameLD

    //-
    private val _errorInputCountLD = MutableLiveData<Boolean>()
    val errorInputCountLD : LiveData<Boolean>
        get() = _errorInputCountLD

    //-
    private val _shopItemLD = MutableLiveData<ShopItem>()
    val shopItemLD : LiveData<ShopItem>
        get() = _shopItemLD

    //-
    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen : LiveData<Unit>
        get() = _shouldCloseScreen

    fun addShopItem(inputName : String?, inputCount : String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val parseResult = validateParse(name, count)
        if (parseResult){
            val shopItem = ShopItem(name,count,true)
            addShopItemUseCase.addShopItem(shopItem)
            finishWork()
        }

    }


    fun getShopItem(shopItemId: Int){
        val item = getShopItemUseCase.getShopItem(shopItemId)
        _shopItemLD.value = item
    }
    fun editShopItem(inputName : String?, inputCount : String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val parseResult = validateParse(name, count)
        if (parseResult){
            _shopItemLD.value?.let {
                val item = it.copy(name, count)
                editShopItemUseCase.editShopItem(item)
                finishWork()
            }

        }
    }

    private fun parseName(inputName : String?) : String{
        return inputName?.trim() ?: ""
    }
    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        }catch(e : Exception){
           0
        }
    }
    private fun validateParse(name : String, count : Int) : Boolean{
        var result = true
        if (name.isBlank()){
            _errorInputNameLD.value = true
            result = false
        }
        if (count <=0){
            _errorInputNameLD.value = true
            result = false
        }
        return result
    }

     fun resetInputNameError(){
        _errorInputNameLD.value = false
    }
     fun resetInputCountError(){
        _errorInputCountLD.value = false
    }
    fun finishWork(){
        _shouldCloseScreen.value = Unit
    }



}