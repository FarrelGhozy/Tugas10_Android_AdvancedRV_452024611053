package com.example.tugas10.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tugas10.databinding.ItemColorBinding
import com.example.tugas10.databinding.ItemHeaderBinding
import com.example.tugas10.databinding.ItemNumberBinding
import com.example.tugas10.model.DisplayItem
import com.example.tugas10.model.ItemType

/**
 * ViewHolder for Header items (full width, 3 spans).
 * Uses private constructor + companion object factory pattern for clean binding.
 */
class HeaderViewHolder private constructor(
    private val binding: ItemHeaderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: DisplayItem) {
        binding.item = item
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): HeaderViewHolder {
            val binding = ItemHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return HeaderViewHolder(binding)
        }
    }
}

/**
 * ViewHolder for Number items (1 span, formatted numeric display).
 * Uses private constructor + companion object factory pattern.
 */
class NumberViewHolder private constructor(
    private val binding: ItemNumberBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: DisplayItem) {
        binding.item = item
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): NumberViewHolder {
            val binding = ItemNumberBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return NumberViewHolder(binding)
        }
    }
}

/**
 * ViewHolder for Color items (1 span, colored background display).
 * Uses private constructor + companion object factory pattern.
 */
class ColorViewHolder private constructor(
    private val binding: ItemColorBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: DisplayItem) {
        binding.item = item
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ColorViewHolder {
            val binding = ItemColorBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ColorViewHolder(binding)
        }
    }
}
