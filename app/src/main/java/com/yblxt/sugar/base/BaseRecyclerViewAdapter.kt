package com.yblxt.sugar.base

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
    private val context: Context,
    @LayoutRes private val layoutResId: Int,
    var data: List<E>? = null
) : RecyclerView.Adapter<BaseRecyclerViewAdapter.InnerHolder>() {

    private var onItemClickListener: OnItemClickListener? = null
    private var onItemLongClickListener: OnItemLongClickListener? = null

    private val layoutInflater: LayoutInflater by lazy {
        LayoutInflater.from(context)
    }

    override fun getItemCount() = data?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val view = layoutInflater.inflate(layoutResId, parent, false)
        val holder = InnerHolder(view)
        view.setOnClickListener {
            onItemClickListener?.onItemClick(view, holder.adapterPosition)
        }
        view.setOnLongClickListener {
            onItemLongClickListener?.onItemLongClick(view, holder.adapterPosition) ?: false
        }
        return holder
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        convert(holder, data?.get(position), position)
    }

    abstract fun convert(holder: InnerHolder, itemData: E?, position: Int)

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        this.onItemLongClickListener = listener
    }

    fun setOnItemClickListener(closure: (View, Int) -> Unit) {
        setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                closure(view, position)
            }
        })
    }

    fun setOnItemLongClickListener(closure: (View, Int) -> Boolean) {
        setOnItemLongClickListener(object : OnItemLongClickListener {
            override fun onItemLongClick(view: View, position: Int): Boolean {
                return closure(view, position)
            }
        })
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, position: Int): Boolean
    }

    class InnerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val viewCache: SparseArray<View> = SparseArray()

        @Suppress("UNCHECKED_CAST")
        fun <T> getViewById(resId: Int): T {
            var view: View? = viewCache[resId]
            if (view == null) {
                view = itemView.findViewById(resId)
                if (view != null) {
                    viewCache.put(resId, view)
                }
            }
            return view as T
        }

    }
}

