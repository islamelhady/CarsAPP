package com.example.carsapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static final int ADD_CAR_REQ_CODE = 1;
    private static final int EDIT_CAR_REQ_CODE = 1;
    public static final String CAR_KEY = "car_key";
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private CarAdapter adapter;
    private DatabaseAccess db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.main_fab);
        recyclerView = findViewById(R.id.main_recycler_view);

        db = DatabaseAccess.getInstance(this);
        db.open();
        ArrayList<Car> cars = db.getAllCars();
        db.close();

        adapter = new CarAdapter(cars, new OnRvItemClickListener() {
            @Override
            public void onItemClick(int carId) {
                Intent intent = new Intent(getBaseContext(), ViewCarActivity.class);
                intent.putExtra(CAR_KEY, carId);
                startActivityForResult(intent, ADD_CAR_REQ_CODE);
            }
        });
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(lm);
        recyclerView.setHasFixedSize(true);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ViewCarActivity.class);
                startActivityForResult(intent, ADD_CAR_REQ_CODE);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.main_search).getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, "Clicked Submit", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(MainActivity.this, newText, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Toast.makeText(MainActivity.this, "close", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ADD_CAR_REQ_CODE && requestCode== ViewCarActivity.ADD_CAR_RESULT_CODE) {
            db.open();
            ArrayList<Car> cars = db.getAllCars();
            db.close();
            adapter.setCars(cars);
            adapter.notifyDataSetChanged();

        }
    }
}
