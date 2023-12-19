package com.srenes.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.srenes.shoppinglist.data.ShopListRepoImpl
import com.srenes.shoppinglist.domain.DeleteShopItemUseCase
import com.srenes.shoppinglist.domain.EditShopItemUseCase
import com.srenes.shoppinglist.domain.GetShopListUseCase
import com.srenes.shoppinglist.domain.ShopItem

class MainViewModel : ViewModel() {

    val repository = ShopListRepoImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem){
        deleteShopItemUseCase.deleteShopItem(shopItem)

    }

    fun changeEnabled(shopItem: ShopItem){
        val newItem  = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newItem)
    }
}