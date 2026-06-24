package com.example.tugas10.adapter

import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter

/**
 * Custom @BindingAdapter functions for mapping presentation logic
 * directly from XML layout attributes.
 *
 * These adapters allow clean separation of UI logic from ViewHolder code,
 * enabling declarative data binding in XML layouts.
 */

/**
 * Sets the background color of a view from a hex color string.
 * Usage in XML: app:backgroundColorHex="@{item.colorHex}"
 */
@BindingAdapter("backgroundColorHex")
fun setBackgroundColorHex(view: android.view.View, colorHex: String?) {
    if (colorHex.isNullOrBlank()) return
    try {
        val color = Color.parseColor(colorHex)
        view.setBackgroundColor(color)
    } catch (e: Exception) {
        view.setBackgroundColor(Color.WHITE)
    }
}

/**
 * Formats a numeric value with a prefix/suffix label.
 * Usage in XML: app:formattedNumber="@{item.value}"
 */
@BindingAdapter("formattedNumber")
fun setFormattedNumber(textView: TextView, value: Long) {
    textView.text = when {
        value >= 1_000_000_000 -> String.format("%.2fB", value / 1_000_000_000.0)
        value >= 1_000_000 -> String.format("%.1fM", value / 1_000_000.0)
        value >= 1_000 -> String.format("%.1fK", value / 1_000.0)
        else -> value.toString()
    }
}
