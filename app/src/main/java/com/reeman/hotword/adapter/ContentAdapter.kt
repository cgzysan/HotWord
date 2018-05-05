package com.reeman.hotword.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.reeman.hotword.R
import com.reeman.hotword.entity.ItemData
import com.reeman.hotword.extension.ctx
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.content_item.*


/**
 * Created by YSAN on 2018/04/25
 */
class ContentAdapter(private val items: MutableList<ItemData>,
                     private val itemClick: OnItemClickListener,
                     private val itemLongClick: onItemLongClickListener,
                     private val itemDeleteClick: onDeleteClickListener)
    : RecyclerView.Adapter<ContentAdapter.ViewHolder>() {

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.ctx).inflate(R.layout.content_item, parent, false)
        return ViewHolder(view, itemClick, itemLongClick, itemDeleteClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItemWord(items[position])
    }

    class ViewHolder(override val containerView: View,
                     private val itemClick: OnItemClickListener,
                     private val itemLongClick: onItemLongClickListener,
                     private val itemDelete: onDeleteClickListener)
        : RecyclerView.ViewHolder(containerView), LayoutContainer{

        fun bindItemWord(data: ItemData) {
            with(data) {
                tv_content_item.text = data.word
                when (data.delete) {
                    false -> iv_delete.visibility = View.GONE
                    true -> iv_delete.visibility = View.VISIBLE
                }
                iv_delete.setOnClickListener { itemDelete(data) }
                itemView.setOnClickListener { itemClick(data) }
                itemView.setOnLongClickListener { itemLongClick(data) }
            }
        }
    }

    interface OnItemClickListener {
        operator fun invoke(item: ItemData)
    }

    interface onItemLongClickListener {
        operator fun invoke(item: ItemData): Boolean
    }

    interface onDeleteClickListener {
        operator fun invoke(item: ItemData)
    }
}