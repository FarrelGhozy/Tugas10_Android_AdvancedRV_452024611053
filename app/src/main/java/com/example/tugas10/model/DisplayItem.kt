package com.example.tugas10.model

/**
 * Types of items in the RecyclerView.
 * Used in getItemViewType() to determine which layout to inflate.
 */
enum class ItemType(val value: Int) {
    HEADER(0),
    NUMBER_ITEM(1),
    COLOR_ITEM(2)
}

/**
 * Data model representing a single item in the RecyclerView.
 *
 * @param id Unique identifier for DiffUtil areItemsTheSame comparison
 * @param type Determines which view type layout to use
 * @param title Main display text
 * @param description Secondary text (used by NUMBER_ITEM for value display)
 * @param value Numeric value (Long to support large numbers like population)
 * @param colorHex Hex color string used by COLOR_ITEM via custom @BindingAdapter
 */
data class DisplayItem(
    val id: Long,
    val type: ItemType,
    val title: String,
    val description: String = "",
    val value: Long = 0L,
    val colorHex: String = "#FFFFFF"
)
