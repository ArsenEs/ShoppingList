package com.srenes.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShopListDAO {


    //Получить весь список item`ов
    @Query("SELECT * FROM shop_items")
    fun getShopList(): LiveData<List<ShopItemDbModel>>

    //Добавить item
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addShopitem(shopItemDbModel: ShopItemDbModel)

    //Удаление item
    @Query("DELETE FROM shop_items WHERE id=:shopItemId")
    fun deleteItem(shopItemId: Int)

    //Получить item
    @Query("SELECT * FROM shop_items WHERE id=:shopItemId LIMIT 1")
    fun getShopItem(shopItemId: Int): ShopItemDbModel


}