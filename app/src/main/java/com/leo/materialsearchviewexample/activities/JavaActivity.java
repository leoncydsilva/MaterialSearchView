package com.leo.materialsearchviewexample.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.google.android.material.appbar.MaterialToolbar;
import com.leo.materialsearchview.MaterialSearchView;
import com.leo.materialsearchviewexample.R;

public class JavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_layout);
        MaterialToolbar materialToolbar = findViewById(R.id.materialToolbar);
        ImageView imageView = findViewById(R.id.imageViewSearch);

        final MaterialSearchView materialSearchView = new MaterialSearchView(this);
//        materialSearchView.setBackButtonTint(R.color.colorAccent);
//        materialSearchView.setAnimationDuration(1000);
//        materialSearchView.setSearchHint("Search");
//        materialSearchView.setBackButtonDrawable(getDrawable(R.drawable.ic_arrow_back));
//        materialSearchView.setClearSearchOnDismiss(false);
//        materialSearchView.setShowKeyBoardDefault(false);
        materialSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(JavaActivity.this, query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(JavaActivity.this, newText, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        materialToolbar.setTitle("Java");
        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Menu menu = materialToolbar.getMenu();
        menu.findItem(R.id.searchMenuIcon).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(JavaActivity.this, "Showing Search from ToolbarMenu", Toast.LENGTH_LONG).show();
                materialSearchView.show(findViewById(R.id.searchMenuIcon));
                return false;
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JavaActivity.this, "Showing Search from ImageView", Toast.LENGTH_LONG).show();
                materialSearchView.show(v);
            }
        });
    }
}
