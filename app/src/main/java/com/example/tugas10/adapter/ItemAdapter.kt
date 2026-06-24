package com.example.tugas10.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tugas10.model.DisplayItem
import com.example.tugas10.model.ItemType

/**
 * Advanced RecyclerView adapter using ListAdapter + DiffUtil for
 * background-thread diff calculations and minimal UI updates.
 *
 * Supports Multiple Item View Types: HEADER, NUMBER_ITEM, COLOR_ITEM.
 *
 * Unlike RecyclerView.Adapter which requires costly notifyDataSetChanged()
 * on every data change, ListAdapter automatically computes minimal
 * changeset via DiffUtil on a background thread.
 */
class ItemAdapter : ListAdapter<DisplayItem, RecyclerView.ViewHolder>(ItemDiffCallback) {

    /**
     * Returns the view type for the item at the given position.
     * Used by RecyclerView to select the correct layout.
     */
    override fun getItemViewType(position: Int): Int {
        return getItem(position).type.value
    }

    /**
     * Creates the appropriate ViewHolder based on view type.
     * Delegates to each ViewHolder's companion object factory method.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ItemType.HEADER.value -> HeaderViewHolder.from(parent)
            ItemType.NUMBER_ITEM.value -> NumberViewHolder.from(parent)
            ItemType.COLOR_ITEM.value -> ColorViewHolder.from(parent)
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    /**
     * Binds data to the appropriate ViewHolder type.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is HeaderViewHolder -> holder.bind(item)
            is NumberViewHolder -> holder.bind(item)
            is ColorViewHolder -> holder.bind(item)
        }
    }
}
