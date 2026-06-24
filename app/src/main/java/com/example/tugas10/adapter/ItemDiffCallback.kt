package com.example.tugas10.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.tugas10.model.DisplayItem

/**
 * DiffUtil.ItemCallback implementation for ListAdapter.
 *
 * Performs efficient diff calculations on a background thread to minimize
 * UI updates. Only changed items are re-rendered, unlike RecyclerView.Adapter
 * which requires manual notifyDataSetChanged() — a costly full list rebind.
 */
object ItemDiffCallback : DiffUtil.ItemCallback<DisplayItem>() {

    /**
     * Determines if two items represent the same entity.
     * Uses stable unique ID to avoid false mismatches.
     */
    override fun areItemsTheSame(oldItem: DisplayItem, newItem: DisplayItem): Boolean {
        return oldItem.id == newItem.id
    }

    /**
     * Determines if the contents of two items are the same.
     * Uses full data class equality (compares all fields).
     */
    override fun areContentsTheSame(oldItem: DisplayItem, newItem: DisplayItem): Boolean {
        return oldItem == newItem
    }
}
