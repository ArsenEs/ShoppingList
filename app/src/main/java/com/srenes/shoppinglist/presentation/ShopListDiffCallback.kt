package com.srenes.shoppinglist.presentation

import androidx.recyclerview.widget.DiffUtil
import com.srenes.shoppinglist.domain.ShopItem

class ShopListDiffCallback(
    private val oldShopList : List<ShopItem>,
    private val newShopList : List<ShopItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldShopList.size
    }
    private var counter = 0

    override fun getNewListSize(): Int {
        counter == newShopList.size
        fun isEmpty() : Boolean{
            return counter == 0
        }
        return newShopList.size
    }


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldShopItem = oldShopList[oldItemPosition]
        val newShopItem = newShopList[newItemPosition]
        return oldShopItem.id == newShopItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldShopItem = oldShopList[oldItemPosition]
        val newShopItem = newShopList[newItemPosition]
        return oldShopItem == newShopItem
    }
}