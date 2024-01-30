package com.srenes.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import com.srenes.shoppinglist.domain.ShopItem
import com.srenes.shoppinglist.domain.ShopListRepository


class ShopListRepoImpl(
    application: Application
) : ShopListRepository {

    private val shopListDAO = AppDataBase.getInstance(application).getShopListDAO()
    private val mapper = ShopListMapper()


    override fun addShopItem(shopItem: ShopItem) {
        shopListDAO.addShopitem(mapper.mapEntityToDBModel(shopItem))
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopListDAO.deleteItem(shopItem.id)
    }

    override fun editShopItem(shopItem: ShopItem) {
        shopListDAO.addShopitem(mapper.mapEntityToDBModel(shopItem))
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        val dbModel = shopListDAO.getShopItem(shopItemId)
        return mapper.mapDBmodelToEntity(dbModel)

    }

    override fun getShopList(): LiveData<List<ShopItem>> = shopListDAO.getShopList().map {
        mapper.mapListDBtoEntity(it)
    }

}