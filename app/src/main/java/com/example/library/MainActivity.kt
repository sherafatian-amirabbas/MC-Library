package com.example.library

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnBookClickListener {
    private lateinit var viewModel: MainViewModel
    private val handler = Handler()
    private var runnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbarMain)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        rvMain.setHasFixedSize(true)
        val bookAdapter = BookAdapter(ArrayList(), this)
        rvMain.adapter = bookAdapter

        viewModel.getAllBooks()

        viewModel.books.observe(this) {
            bookAdapter.updateBooks(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu?.findItem(R.id.menu_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (runnable != null) {
                    handler.removeCallbacks(runnable!!)
                }

                runnable = Runnable {
                    if (newText?.isNotEmpty() == true)
                        viewModel.searchBooks(newText)
                    else
                        viewModel.getAllBooks()
                }
                handler.postDelayed(runnable!!, 500)
                return false
            }
        })


        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                viewModel.getAllBooks()
                return true
            }
        })

        return true
    }

    override fun onBookClick(bookId: String) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(BOOK_ID_KEY, bookId)
        startActivity(intent)
    }
}