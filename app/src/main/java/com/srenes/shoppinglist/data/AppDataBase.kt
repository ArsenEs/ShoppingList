package com.srenes.shoppinglist.data

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShopItemDbModel::class], version = 1, exportSchema = false)
abstract class AppDataBase  : RoomDatabase(){

    abstract fun getShopListDAO() : ShopListDAO

    companion object{


        private var INSTANCE: AppDataBase? = null
        private var LOCK = Any()
        private const val DB_NAME = "shop_item.db"


        fun getInstance(application: Application) : AppDataBase{
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK){
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application, AppDataBase::class.java, DB_NAME
                )
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = db
                return db
            }


        }
    }

}