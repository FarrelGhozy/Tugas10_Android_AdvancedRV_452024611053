package com.example.tugas10

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tugas10.adapter.ItemAdapter
import com.example.tugas10.databinding.ActivityMainBinding
import com.example.tugas10.model.ItemType

/**
 * Main entry point demonstrating Advanced RecyclerView features:
 *
 * 1. ListAdapter + DiffUtil — efficient background-thread diffing
 * 2. GridLayoutManager + SpanSizeLookup — dynamic span allocation
 * 3. Multiple View Types — HEADER, NUMBER_ITEM, COLOR_ITEM
 * 4. Custom @BindingAdapter — backgroundColorHex, formattedNumber, itemCount
 * 5. Clean ViewHolder pattern — private constructor + companion object factory
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        adapter = ItemAdapter()

        setupRecyclerView()
        setupObservers()
        setupButtons()
        setupInsets()
    }

    private fun setupRecyclerView() {
        // GridLayoutManager with 3 columns
        val layoutManager = GridLayoutManager(this, 3)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.currentList[position].type) {
                    ItemType.HEADER -> 3  // Full width (3 spans)
                    ItemType.NUMBER_ITEM -> 1  // One column
                    ItemType.COLOR_ITEM -> 1  // One column
                }
            }
        }

        binding.recyclerView.apply {
            this.layoutManager = layoutManager
            setHasFixedSize(true)
            adapter = this@MainActivity.adapter
        }

        // Show span info
        Toast.makeText(
            this,
            "Grid: 3 columns · Headers span=3 · Items span=1",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun setupObservers() {
        viewModel.items.observe(this) { items ->
            adapter.submitList(items)

            // Show update info (for demonstration purposes)
            val headerCount = items.count { it.type == ItemType.HEADER }
            val numberCount = items.count { it.type == ItemType.NUMBER_ITEM }
            val colorCount = items.count { it.type == ItemType.COLOR_ITEM }
            binding.toolbar.subtitle = "${items.size} items | ${headerCount} headers · ${numberCount} numbers · ${colorCount} colors"
        }
    }

    private fun setupButtons() {
        binding.btnRefresh.setOnClickListener {
            viewModel.refreshData()
            Toast.makeText(this, "Data updated — DiffUtil computes minimal changes", Toast.LENGTH_SHORT).show()
        }

        binding.btnShuffle.setOnClickListener {
            viewModel.shuffleData()
            Toast.makeText(this, "Items shuffled — only moved items re-render", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )
            binding.toolbar.updatePadding(top = systemBars.top)
            binding.bottomBar.updatePadding(
                bottom = systemBars.bottom
            )
            insets
        }
    }
}
