package com.app.william.testpaging

import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.william.testpaging.databinding.ActivitySearchBinding


class SearchActivity : AppCompatActivity() {
    private lateinit var viewModel: SearchViewModel
    private lateinit var binding: ActivitySearchBinding
    private lateinit var adapter: SearchAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(LayoutInflater.from(this))
        viewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                SearchViewModel::class.java
            )

        setContentView(binding.root)

        adapter = SearchAdapter(viewModel)

        binding.recyclerView.adapter = adapter

        binding.layoutSearch.setOnClickListener {
            finish()
        }

        binding.editText.doOnTextChanged { text, _, _, _ ->
            viewModel.setQuery(text.toString())
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        viewModel.messages.observe(this, Observer {
            adapter.submitList(it)
        })

        viewModel.selectId.observe(this, Observer {
            intent.putExtra("ID", it)
            setResult(Activity.RESULT_OK, intent)
            finish()
        })

        handleSearch()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleSearch()
    }

    private fun handleSearch() {
        val intent = intent
        if (Intent.ACTION_SEARCH == intent.action) {
            val searchQuery = intent.getStringExtra(SearchManager.QUERY)
            searchQuery?.let {
                viewModel.setQuery(it)
            }
        } else if (Intent.ACTION_VIEW == intent.action) {
            val selectedSuggestionRowId = intent.dataString
            //execution comes here when an item is selected from search suggestions
            //you can continue from here with user selected search item
            Toast.makeText(
                this, "selected search suggestion $selectedSuggestionRowId",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}