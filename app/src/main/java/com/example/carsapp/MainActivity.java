package com.example.carsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.DatabaseUtils;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText textmodel, textcolor, textdpr;
    Button buttonsave, buttonrestor;
    MyDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textmodel = findViewById(R.id.model);
        textcolor = findViewById(R.id.color);
        textdpr = findViewById(R.id.dpr);
        buttonsave = findViewById(R.id.btn_save);
        buttonrestor = findViewById(R.id.btn_restor);

        buttonsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String model = textmodel.getText().toString();
                String color = textcolor.getText().toString();
                double drp = Double.parseDouble(textdpr.getText().toString());

                Car car = new Car(model, color, drp);
                boolean save = database.insertCar(car);
                if (save)
                    Toast.makeText(MainActivity.this, "SUCCES", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "FAILD", Toast.LENGTH_SHORT).show();

            }
        });

        buttonrestor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long restor = database.getCarsCount();
                Toast.makeText(MainActivity.this,"sl"+ restor, Toast.LENGTH_SHORT).show();

            }
        });
    }


}
