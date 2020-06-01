package com.piyush.internal.sbnri.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.piyush.internal.sbnri.R
import com.piyush.internal.sbnri.di.DaggerAppComponent
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var adapter: ReposAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerAppComponent.factory().create(application, this).inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        setupRV()
    }

    private fun setupRV() {
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        rv_list.addItemDecoration(decoration)
        rv_list.adapter = adapter

        mainViewModel.repos.observe(this, Observer {
            Log.d("test", "list: ${it?.size}")
            showEmptyList(it.size == 0)
            adapter.submitList(it)
        })

        mainViewModel.networkErrors.observe(this, Observer {
            Log.d("test", "error $it")
            Toast.makeText(this, "error $it", Toast.LENGTH_LONG).show()
        })

        mainViewModel.isLoading.observe(this, Observer {
            Log.d("test", "loading $it")
            showEmptyList(false)
            pb_load.visibility = if (it == true) View.VISIBLE else View.GONE
        })

    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            tv_emptyList.visibility = View.VISIBLE
            rv_list.visibility = View.GONE
        } else {
            tv_emptyList.visibility = View.GONE
            rv_list.visibility = View.VISIBLE
        }
    }
}
