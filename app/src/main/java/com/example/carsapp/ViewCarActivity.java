package com.example.carsapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class ViewCarActivity extends AppCompatActivity {

    public static final int PICK_IMG_REQ_CODE = 1;
    public static final int ADD_CAR_RESULT_CODE = 2;
    public static final int EDIT_CAR_RESULT_CODE = 3;
    private Toolbar toolbar;
    private TextInputEditText et_model, et_color, et_dpl, et_description;
    private ImageView imageView;
    private int carId = -1;
    private DatabaseAccess db;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_car);

        toolbar = findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);

        et_model = findViewById(R.id.et_details_model);
        et_color = findViewById(R.id.et_details_color);
        et_dpl = findViewById(R.id.et_details_dpl);
        et_description = findViewById(R.id.et_details_description);
        imageView = findViewById(R.id.details_iv);

        db = DatabaseAccess.getInstance(this);

        Intent intent = getIntent();
        carId = intent.getIntExtra(MainActivity.CAR_KEY, -1);

        if (carId == -1) {
            // add new car
            ensableFields();
            clearFields();
        } else {
            // edite or delete car
            disableFields();
            db.open();
            Car car = db.getCar(carId);
            db.close();

            if (car != null) {
                fillCarToFildes(car);
            }
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMG_REQ_CODE);
            }
        });
    }

    private void fillCarToFildes(Car car) {
        if (car.getImage() != null && !car.getImage().isEmpty())
            imageView.setImageURI(Uri.parse(car.getImage()));
        et_model.setText(car.getModel());
        et_color.setText(car.getColor());
        et_dpl.setText(car.getDpl() + "");
        et_description.setText(car.getDescription());

    }

    private void disableFields() {
        imageView.setEnabled(false);
        et_description.setEnabled(false);
        et_color.setEnabled(false);
        et_model.setEnabled(false);
        et_dpl.setEnabled(false);
    }

    private void ensableFields() {
        imageView.setEnabled(true);
        et_description.setEnabled(true);
        et_color.setEnabled(true);
        et_model.setEnabled(true);
        et_dpl.setEnabled(true);
    }

    private void clearFields() {
        imageView.setImageURI(null);
        et_description.setText("");
        et_color.setText("");
        et_model.setText("");
        et_dpl.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);

        MenuItem save = menu.findItem(R.id.details_save);
        MenuItem delete = menu.findItem(R.id.details_delete);
        MenuItem edit = menu.findItem(R.id.details_edit);
        if (carId == -1) {
            save.setVisible(true);
            delete.setVisible(false);
            edit.setVisible(false);
        } else {
            save.setVisible(false);
            delete.setVisible(true);
            edit.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String model, color, description, image="";
        Double dpl;
        switch (item.getItemId()) {
            case R.id.details_save:
                model = et_model.getText().toString();
                color = et_color.getText().toString();
                dpl = Double.parseDouble(et_dpl.getText().toString());
                description = et_description.getText().toString();
                if (imageUri != null) {
                    image = imageUri.toString();
                }

                Car car = new Car(carId, model, color, dpl, image, description);
                db.open();

                boolean res;
                if (carId == -1) {
                    res = db.insertCar(car);
                    if (res)
                        Toast.makeText(this, "Car Added Successfully", Toast.LENGTH_SHORT).show();
                    setResult(ADD_CAR_RESULT_CODE, null);
                    finish();
                } else {
                    res = db.updateCar(car);
                    if (res)
                        Toast.makeText(this, "Car Modified Successfully", Toast.LENGTH_SHORT).show();
                    setResult(EDIT_CAR_RESULT_CODE, null);
                    finish();
                }
                db.close();
                return true;

            case R.id.details_edit:
                ensableFields();
                MenuItem save = toolbar.getMenu().findItem(R.id.details_save);
                MenuItem delete = toolbar.getMenu().findItem(R.id.details_delete);
                MenuItem edit = toolbar.getMenu().findItem(R.id.details_edit);
                save.setVisible(true);
                delete.setVisible(false);
                edit.setVisible(false);
                return true;
            case R.id.details_delete:
                return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMG_REQ_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                imageUri = data.getData();
                imageView.setImageURI(imageUri);
            }
        }
    }
}
