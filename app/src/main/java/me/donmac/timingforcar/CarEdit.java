package me.donmac.timingforcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Objects;

import me.donmac.timingforcar.db.DbHelper;
import me.donmac.timingforcar.model.Car;
import me.donmac.timingforcar.wigets.YearPicker;

public class CarEdit extends AppCompatActivity {
    private EditText manuEt;
    private EditText modelEt;
    private YearPicker yearPicek;
    private Button confBut;
    private static Car localCar;

    public static void setLocalCar(Car intrensCar){
        localCar = intrensCar;
    }

    @Override
    protected void onStop() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        manuEt = findViewById(R.id.manuEt);
        modelEt = findViewById(R.id.modelEt);
        yearPicek = findViewById(R.id.yearDatePicker);
        confBut = findViewById(R.id.confirmBut);
        if (localCar != null){
            manuEt.setText(localCar.getManufacturer());
            modelEt.setText(localCar.getModel());
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, localCar.getYear());
            yearPicek.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);
            confBut.setOnClickListener(v -> {
                assert !manuEt.getText().equals("");
                assert !modelEt.getText().equals("");
                if (localCar == null) {
                    localCar = new Car();
                }
                localCar.setManufacturer(manuEt.getText().toString());
                localCar.setModel(modelEt.getText().toString());
                localCar.setYear(yearPicek.getYear());
                if ((!Objects.equals(localCar.getManufacturer(), "")) || (!Objects.equals(localCar.getModel(), ""))) {
                    try (DbHelper dbHelper = new DbHelper(this)) {
                        Dao<Car, Integer> carDao = DaoManager.createDao(new AndroidConnectionSource(dbHelper), Car.class);
                        carDao.update(localCar);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                finish();
            });
        }else {
            confBut.setOnClickListener(v -> {
                assert !manuEt.getText().equals("");
                assert !modelEt.getText().equals("");
                if (localCar == null) {
                    localCar = new Car();
                }
                localCar.setManufacturer(manuEt.getText().toString());
                localCar.setModel(modelEt.getText().toString());
                localCar.setYear(yearPicek.getYear());
                if ((!Objects.equals(localCar.getManufacturer(), "")) || (!Objects.equals(localCar.getModel(), ""))) {
                    try (DbHelper dbHelper = new DbHelper(this)) {
                        Dao<Car, Integer> carDao = DaoManager.createDao(new AndroidConnectionSource(dbHelper), Car.class);

                        carDao.create(localCar);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                finish();
            });
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                // Возвращаемся к родительскому действию (CatalogActivity)
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}