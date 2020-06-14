package com.app.william.testpaging

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.william.testpaging.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private val adapter = MainAdapter()

    private var isEvent = false

    private var selectId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        viewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                MainViewModel::class.java
            )

        setContentView(binding.root)

        binding.recyclerView.adapter = adapter

        viewModel.messages.observe(this, Observer { page->
            adapter.submitList(page)
            if (isEvent) {
                recyclerView.scrollToPosition(0)
                isEvent = false
            }
            selectId?.let { id ->
                val index: Int? = adapter.getIndexById(id)
                index?.let {
                    if(it!=-1){
                        recyclerView.scrollToPosition(it)
                    }
                }
                selectId = null
            }
        })

        viewModel.selectId.observe(this, Observer {
            selectId = it
        })

        binding.editText.setOnEditorActionListener { _, keyCode, _ ->
            send(keyCode)
        }

        binding.btnRemoveAll.setOnClickListener {
            viewModel.removeAll()
        }

        binding.btnToLast.setOnClickListener {
            recyclerView.scrollToPosition(0)
        }

    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        data?.let {
            if (requestCode == 1) {
                if (resultCode == Activity.RESULT_OK) {
                    val id = it.getLongExtra("ID", 0)
                    if (id != 0L) viewModel.scrollTo(id)
                }
            }

        }


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == R.id.app_bar_search) {
            val intent = Intent(this, SearchActivity::class.java)
            startActivityForResult(intent, 1)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun send(keyCode: Int): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_ENDCALL,
            KeyEvent.ACTION_DOWN -> {
                val str = binding.editText.text
                if (str.isNotBlank()) {
                    viewModel.post(str.toString())
                    binding.editText.setText("")
                    isEvent = true
                }
                return true
            }
        }
        return false
    }
}