package com.example.tugas10

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tugas10.model.DisplayItem
import com.example.tugas10.model.ItemType
import kotlin.random.Random

/**
 * ViewModel that holds the list of DisplayItems.
 * Generates sample data and supports update/shuffle operations
 * to demonstrate ListAdapter's efficient diffing.
 */
class MainViewModel : ViewModel() {

    private val _items = MutableLiveData<List<DisplayItem>>()
    val items: LiveData<List<DisplayItem>> = _items

    private var idCounter = 0L

    init {
        generateData()
    }

    fun generateData() {
        idCounter = 0
        val list = mutableListOf<DisplayItem>()

        // --- Section 1: Numbers ---
        list.add(
            DisplayItem(
                id = idCounter++,
                type = ItemType.HEADER,
                title = "📊 Population Statistics"
            )
        )
        list.addAll(
            listOf(
                DisplayItem(id = idCounter++, type = ItemType.NUMBER_ITEM, title = "World", description = "Total Population", value = 8_100_000_000L),
                DisplayItem(id = idCounter++, type = ItemType.NUMBER_ITEM, title = "Indonesia", description = "Total Population", value = 281_000_000L),
                DisplayItem(id = idCounter++, type = ItemType.NUMBER_ITEM, title = "Jakarta", description = "Metro Population", value = 35_000_000L),
                DisplayItem(id = idCounter++, type = ItemType.NUMBER_ITEM, title = "Bandung", description = "City Population", value = 2_500_000L),
                DisplayItem(id = idCounter++, type = ItemType.NUMBER_ITEM, title = "Surabaya", description = "City Population", value = 3_000_000L),
                DisplayItem(id = idCounter++, type = ItemType.NUMBER_ITEM, title = "Yogyakarta", description = "City Population", value = 400_000L),
                DisplayItem(id = idCounter++, type = ItemType.NUMBER_ITEM, title = "Semarang", description = "City Population", value = 1_700_000L),
                DisplayItem(id = idCounter++, type = ItemType.NUMBER_ITEM, title = "Medan", description = "City Population", value = 2_500_000L)
            )
        )

        // --- Section 2: Colors ---
        list.add(
            DisplayItem(
                id = idCounter++,
                type = ItemType.HEADER,
                title = "🎨 Color Palette"
            )
        )
        list.addAll(
            listOf(
                DisplayItem(id = idCounter++, type = ItemType.COLOR_ITEM, title = "Warm Coral", description = "#FF6B6B", colorHex = "#FF6B6B"),
                DisplayItem(id = idCounter++, type = ItemType.COLOR_ITEM, title = "Ocean Blue", description = "#4ECDC4", colorHex = "#4ECDC4"),
                DisplayItem(id = idCounter++, type = ItemType.COLOR_ITEM, title = "Sunset Orange", description = "#FF8C42", colorHex = "#FF8C42"),
                DisplayItem(id = idCounter++, type = ItemType.COLOR_ITEM, title = "Forest Green", description = "#2ECC71", colorHex = "#2ECC71"),
                DisplayItem(id = idCounter++, type = ItemType.COLOR_ITEM, title = "Royal Purple", description = "#9B59B6", colorHex = "#9B59B6"),
                DisplayItem(id = idCounter++, type = ItemType.COLOR_ITEM, title = "Golden Yellow", description = "#F1C40F", colorHex = "#F1C40F")
            )
        )

        _items.value = list
    }

    /**
     * Simulates data update: modifies some values to demonstrate
     * ListAdapter's efficient partial re-rendering via DiffUtil.
     */
    fun refreshData() {
        val current = _items.value ?: return
        val updated = current.map { item ->
            when (item.type) {
                ItemType.NUMBER_ITEM -> item.copy(
                    value = item.value + Random.nextLong(100_000L, 500_000L)
                )
                ItemType.COLOR_ITEM -> item.copy(
                    title = "${item.title} ★"
                )
                ItemType.HEADER -> item.copy(
                    title = item.title.replace("📊", "📈").replace("🎨", "🎭")
                )
            }
        }
        _items.value = updated
    }

    /**
     * Shuffles non-header items while preserving headers,
     * demonstrating ListAdapter's ability to handle reordering.
     */
    fun shuffleData() {
        val current = _items.value ?: return
        val contentItems = current.filter { it.type != ItemType.HEADER }.toMutableList()
        contentItems.shuffle()

        val result = mutableListOf<DisplayItem>()
        var contentIndex = 0
        for (item in current) {
            if (item.type == ItemType.HEADER) {
                result.add(item)
            } else {
                result.add(contentItems[contentIndex++])
            }
        }
        _items.value = result
    }
}
