package com.yblxt.sugar.common.base

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * @author evanyu
 * @date 2019-07-18
 */
abstract class BaseRecyclerViewAdapter<E>(
    @LayoutRes protected val layoutResId: Int,
    var data: List<E>? = null
) : RecyclerView.Adapter<BaseRecyclerViewAdapter.InnerHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    private var layoutInflater: LayoutInflater? = null

    override fun getItemCount() = data?.size ?: 0

    override fun getItemId(position: Int) = position.toLong()

    open fun getItem(position: Int): E? = data?.get(position)

    protected fun getLayoutInflater(context: Context): LayoutInflater {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(context)
        }
        return layoutInflater!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val view = getLayoutInflater(parent.context).inflate(layoutResId, parent, false)
        val holder = InnerHolder(view)
        initListener(holder)
        return holder
    }

    open fun initListener(holder: InnerHolder) {
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(it, holder.adapterPosition)
        }
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        convert(holder, data?.get(position), position)
    }

    abstract fun convert(holder: InnerHolder, itemData: E?, position: Int)

    fun setDataAndNotifyChanged(data: List<E>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    fun setOnItemClickListener(closure: (View, Int) -> Unit) {
        setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                closure(view, position)
            }
        })
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    open class InnerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val viewCache: SparseArray<View> = SparseArray()

        @Suppress("UNCHECKED_CAST")
        fun <V : View> getViewById(resId: Int): V {
            var view: View? = viewCache[resId]
            if (view == null) {
                view = itemView.findViewById(resId)
                if (view != null) {
                    viewCache.put(resId, view)
                }
            }
            return view as V
        }

    }
}

