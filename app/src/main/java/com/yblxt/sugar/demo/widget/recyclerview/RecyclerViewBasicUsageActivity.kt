package com.yblxt.sugar.demo.widget.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yblxt.sugar.R
import com.yblxt.sugar.common.base.BaseViewBindingActivity
import com.yblxt.sugar.databinding.ActivityRecyclerviewBasicUsageBinding
import timber.log.Timber

/**
 * @author evanyu
 * @date 2021-04-27
 */
class RecyclerViewBasicUsageActivity : BaseViewBindingActivity<ActivityRecyclerviewBasicUsageBinding>() {

    private val cboxPayload by lazy { binding.cboxPayload }
    private val recyclerview by lazy { binding.recyclerview }
    private val adapter by lazy { MyAdapter(this, data) }
    private val data by lazy {
        ArrayList<String>().apply {
            for (i in 0..100) {
                this.add("$i")
            }
        }
    }

    override fun init() {
        initRecyclerView()

        binding.btnNotifyDataSetChanged.setOnClickListener {
            recyclerview.adapter!!.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView() {
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerview.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerview.setItemViewCacheSize(10)
        recyclerview.recycledViewPool.setMaxRecycledViews(0, 100)
        recyclerview.adapter = adapter
        adapter.onItemClickListener = object : MyAdapter.OnItemClickListener {
            override fun onItemClick(itemView: View, position: Int) {
                Toast.makeText(this@RecyclerViewBasicUsageActivity, "item:$position", Toast.LENGTH_SHORT).show()
                // 同时执行多个操作
                // add
                data.add(position, "added text")
                adapter.notifyItemInserted(position)
                // remove
                data.removeAt(position + 2)
                adapter.notifyItemRemoved(position + 2)
                // change
                data[position + 3] = "changed"
                adapter.notifyItemChanged(position + 3, if (cboxPayload.isChecked) "changed" else null)
            }

            override fun onAddClick(btnAdd: View, position: Int) {
                data.add(position, "added text")
                adapter.notifyItemInserted(position)
            }

            override fun onDeleteClick(btnDel: View, position: Int) {
                data.removeAt(position)
                adapter.notifyItemRemoved(position)
            }

            override fun onChangeClick(btnChange: View, position: Int) {
                data[position] = "changed"
                adapter.notifyItemChanged(position, if (cboxPayload.isChecked) data[position] else null)
            }
        }
    }
}

class MyAdapter(val context: Context, val data: List<String>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        Timber.d("onCreateViewHolder")
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_test, parent, false)
        val holder = MyViewHolder(itemView)
        itemView.setOnClickListener { onItemClickListener?.onItemClick(it, holder.adapterPosition) }
        holder.btnAdd.setOnClickListener { onItemClickListener?.onAddClick(it, holder.adapterPosition) }
        holder.btnDel.setOnClickListener { onItemClickListener?.onDeleteClick(it, holder.adapterPosition) }
        holder.btnChange.setOnClickListener { onItemClickListener?.onChangeClick(it, holder.adapterPosition) }
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Timber.d("onBindViewHolder -> position = $position")
        holder.tvTitle.text = data[position]
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, payloads: MutableList<Any>) {
        // 在这里实现局部刷新逻辑
        Timber.d("onBindViewHolder -> position = $position, $payloads")
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.tvTitle.text = payloads[0] as String
        }
    }

    class MyViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val btnAdd: Button = itemView.findViewById(R.id.btn_add)
        val btnDel: Button = itemView.findViewById(R.id.btn_delete)
        val btnChange: Button = itemView.findViewById(R.id.btn_change)
    }

    interface OnItemClickListener {
        fun onItemClick(itemView: View, position: Int)
        fun onAddClick(btnAdd: View, position: Int)
        fun onDeleteClick(btnDel: View, position: Int)
        fun onChangeClick(btnChange: View, position: Int)
    }

}
