package com.srenes.shoppinglist.data

import com.srenes.shoppinglist.domain.ShopItem

class ShopListMapper  {

    fun mapEntityToDBModel(shopItem: ShopItem) : ShopItemDbModel{
        return ShopItemDbModel(
            id = shopItem.id,
            name = shopItem.name,
            count = shopItem.count,
            enabled = shopItem.enabled
        )
    }

    fun mapDBmodelToEntity(shopItemDbModel: ShopItemDbModel) : ShopItem{
        return ShopItem(
            id = shopItemDbModel.id,
            name = shopItemDbModel.name,
            count = shopItemDbModel.count,
            enabled = shopItemDbModel.enabled
        )
    }

    fun mapListDBtoEntity(list: List<ShopItemDbModel>) = list.map {
        mapDBmodelToEntity(it)
    }
}