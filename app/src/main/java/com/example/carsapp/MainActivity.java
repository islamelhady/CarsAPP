package com.example.carsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText textmodel, textcolor, textdistance;
    Button buttonsave, buttonrestor;
    MyDatabase database;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_main);


//        database = new MyDatabase(this);

        DatabaseAccess databaseAccess =  DatabaseAccess.getInstance(this);
        ArrayList<Car> cars = new ArrayList<>();
        databaseAccess.open();
        cars = databaseAccess.getAllCars();
        databaseAccess.close();


        RecyclerViewAdapter adapter = new RecyclerViewAdapter(cars);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);



    }


}
