package me.donmac.timingforcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;
import java.util.List;

import me.donmac.timingforcar.adapters.CarsAdapter;
import me.donmac.timingforcar.adapters.DetalesAdapter;
import me.donmac.timingforcar.db.DbHelper;
import me.donmac.timingforcar.model.Car;
import me.donmac.timingforcar.model.ServiceKit;

public class MainActivity extends AppCompatActivity {
    private TextView tvFirstRowName;
    private TextView tvSecondRowName;
    private TextView tvDopRow;
    private TextView tvTherdRowName;
    private ListView carsListView;
    private Class editorCalss;
    private Long carId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        carsListView = findViewById(R.id.carsListView);
        tvFirstRowName = findViewById(R.id.tvFirstRowName);
        tvSecondRowName = findViewById(R.id.tvSecondRowName);
        tvTherdRowName = findViewById(R.id.tvTherdRowName);
        tvDopRow = findViewById(R.id.tvDopRowcost);
        initCarsList();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Расширение пунктов меню из файла res / menu / menu_catalog.xml.
        // Это добавляет пункты меню на панель приложения.
        getMenuInflater().inflate(R.menu.main_act_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Пользователь щелкнул пункт меню в меню переполнения панели приложения
        switch (item.getItemId()) {
            // Отвечаем на щелчок по пункту меню "Удалить все записи"
            case R.id.createItem:
                // Пока ничего не делаем
                Intent intent = new Intent(this, editorCalss);
                startActivity(intent);
                finish();
                return true;
            case android.R.id.home:
                // Возвращаемся к родительскому действию (CatalogActivity)
                initCarsList();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void initCarsList(){
        tvDopRow.setVisibility(View.GONE);
        editorCalss = CarEdit.class;
        tvFirstRowName.setText(getResources().getText(R.string.manufact));
        tvSecondRowName.setText(getResources().getText(R.string.model));
        tvTherdRowName.setText(getResources().getText(R.string.year));
        try (DbHelper dbHelper = new DbHelper(this)){
            Dao<Car, Integer> carIntegerDao = DaoManager.createDao(new AndroidConnectionSource(dbHelper),Car.class);
            List<Car> carList = carIntegerDao.queryForAll();
            CarsAdapter carsAdapter = new CarsAdapter(this, carList);
            carsListView.setAdapter(carsAdapter);
            carsListView.setOnItemClickListener((parent, view, position, id) -> {
                AlertDialog.Builder alertBoulder = new AlertDialog.Builder(this);
                alertBoulder.setMessage("что делатьс записью?")
                        .setNeutralButton("удалить", (dialog, which) -> {
                    try (DbHelper delHelper = new DbHelper(this)){
                        Dao<Car, Integer> carDelrDao = DaoManager.createDao(new AndroidConnectionSource(delHelper),Car.class);
                        Dao<ServiceKit, Integer> serviceKits=  DaoManager.createDao(new AndroidConnectionSource(delHelper), ServiceKit.class);
                        List<ServiceKit> serviceKits1 = serviceKits.queryForEq("car_id", ((Car) carsListView.getItemAtPosition(position)).getId());
                        for (ServiceKit s:serviceKits1) {
                            serviceKits.delete(s);
                        }
                        carDelrDao.delete((Car) carsListView.getItemAtPosition(position));
                        initCarsList();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }).setPositiveButton("изменить",(dialog, which) -> {
                    Intent intent = new Intent(this, CarEdit.class);
                    CarEdit.setLocalCar((Car) (carsListView.getItemAtPosition(position)));
                    startActivity(intent);
                    finish();

                }).setNegativeButton("открыть",(dialog, which) -> {
                    carId = id;
                    initPartsList();
                });

                alertBoulder.create();
                alertBoulder.show();
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void initPartsList(){
        tvDopRow.setVisibility(View.VISIBLE);
        tvDopRow.setText(getResources().getText(R.string.part_cost));
        tvFirstRowName.setText(getResources().getText(R.string.part_name));
        tvSecondRowName.setText(getResources().getText(R.string.part_probegS));
        tvTherdRowName.setText(getResources().getText(R.string.part_probegE));
        editorCalss = PartEditor.class;
        PartEditor.setLocalCar(carId);
        try (DbHelper dbHelper = new DbHelper(this)){
            Dao<ServiceKit, Integer> detalDao = DaoManager.createDao(new AndroidConnectionSource(dbHelper), ServiceKit.class);
            List<ServiceKit> serviceKits = detalDao.queryForEq("car_id", carId);
            DetalesAdapter detalesAdapter = new DetalesAdapter(this, serviceKits);
            if (serviceKits.isEmpty()){
            }
            carsListView.setAdapter(detalesAdapter);
            carsListView.setOnItemClickListener((parent, view, position, id) -> {
                AlertDialog.Builder alertBoulder = new AlertDialog.Builder(this);
                alertBoulder.setMessage("что делатьс записью?")
                        .setNegativeButton("удалить", (dialog, which) -> {
                    try (DbHelper dHelper = new DbHelper(this)) {
                        Dao<ServiceKit, Integer> detaldelDao = DaoManager.createDao(new AndroidConnectionSource(dHelper), ServiceKit.class);
                        detaldelDao.delete((ServiceKit) carsListView.getItemAtPosition(position));
                        initPartsList();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                })
                        .setPositiveButton("изменить",(dialog, which) -> {
                    Intent intent = new Intent(this, PartEditor.class);
                    PartEditor.setLocalKit((ServiceKit) carsListView.getItemAtPosition(position));
                    startActivity(intent);
                });
                alertBoulder.create();
                alertBoulder.show();
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}