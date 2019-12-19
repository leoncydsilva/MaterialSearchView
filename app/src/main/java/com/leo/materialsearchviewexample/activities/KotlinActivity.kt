package com.leo.materialsearchviewexample.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.leo.materialsearchview.MaterialSearchView
import com.leo.materialsearchviewexample.R
import kotlinx.android.synthetic.main.activity_common_layout.*

class KotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common_layout)

        val materialSearchView = MaterialSearchView(this)
        //materialSearchView.setBackButtonTint(R.color.colorAccent)
        //materialSearchView.animationDuration = 1000
        //materialSearchView.searchHint = "Search"
        //materialSearchView.backButtonDrawable = getDrawable(R.drawable.ic_arrow_back)
        //materialSearchView.clearSearchOnDismiss = false
        //materialSearchView.showKeyBoardDefault = false

        materialSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@KotlinActivity, query, Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Toast.makeText(this@KotlinActivity, newText, Toast.LENGTH_SHORT).show()
                return false
            }

        })

        materialToolbar.title = "Kotlin"
        materialToolbar.setNavigationOnClickListener { onBackPressed() }
        val menu = materialToolbar.menu
        menu.findItem(R.id.searchMenuIcon).setOnMenuItemClickListener {
            Toast.makeText(this, "Showing Search from ToolbarMenu", Toast.LENGTH_LONG).show()
            materialSearchView.show(findViewById(R.id.searchMenuIcon));false
        }

        imageViewSearch.setOnClickListener {
            Toast.makeText(this, "Showing Search from ImageView", Toast.LENGTH_LONG).show()
            materialSearchView.show(it)
        }
    }
}
