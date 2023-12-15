package com.srenes.shoppinglist.data

import com.srenes.shoppinglist.domain.ShopItem
import com.srenes.shoppinglist.domain.ShopListRepository
import java.lang.RuntimeException

object ShopListRepoImpl : ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()

    private var autoIncreament = 0
    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID){
            shopItem.id = autoIncreament++
        }

        shopList.add(shopItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldItem = getShopItem(shopItem.id)
        shopList.remove(oldItem)
        addShopItem(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId
        } ?: throw RuntimeException("element with id $shopItemId not found")
    }

    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }
}