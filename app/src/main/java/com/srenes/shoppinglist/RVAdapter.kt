package com.srenes.shoppinglist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.srenes.shoppinglist.domain.ShopItem
import com.srenes.shoppinglist.presentation.ShopitemDiffCallback

class RVAdapter : ListAdapter<ShopItem,RVAdapter.ViewHolder>(ShopitemDiffCallback()) {

    var onLongShopItemListener : ((ShopItem) -> Unit)? = null
    var onClickShopItemListener :((ShopItem) -> Unit)?= null



    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layout = when(viewType){
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            else ->throw RuntimeException("Unknown layout $viewType")
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ViewHolder(view)
    }


    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.enabled){
            VIEW_TYPE_ENABLED
        }else{
            VIEW_TYPE_DISABLED
        }
    }
    interface DataChangeListener {
        fun onDataChange()
    }

    private var listener: DataChangeListener? = null

    fun setDataChangeListener(listener: DataChangeListener) {
        this.listener = listener
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shopItem = getItem(position)
        holder.tvName.text = shopItem.name
        holder.tvCount.text = shopItem.count.toString()
        holder.itemView.setOnLongClickListener{
            onLongShopItemListener?.invoke(shopItem)
            listener?.onDataChange() // Вызов метода при изменении данных
            true
        }
        holder.itemView.setOnClickListener{
            onClickShopItemListener?.invoke(shopItem)
        }
    }



    companion object{
        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = 2

        const val MAX_POOL_SIZE= 15
    }
}