package me.donmac.timingforcar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;

import me.donmac.timingforcar.db.DbHelper;
import me.donmac.timingforcar.model.ServiceKit;

public class PartEditor extends AppCompatActivity {
    private EditText tvName;
    private EditText tvProbegS;
    private EditText tvProbegE;
    private EditText tvCost;

    private static ServiceKit localKit;
    private static Long localCarId;

    public static void setLocalCar(Long Car) {
        localCarId = Car;
    }

    public static void setLocalKit(ServiceKit Kit) {
        localKit = Kit;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_editor);
        tvName = findViewById(R.id.tvPartName);
        tvProbegS = findViewById(R.id.tbPartProbegS);
        tvProbegE = findViewById(R.id.tbProbegE);
        tvCost = findViewById(R.id.tvPartCost);
        Button confirmPartBut = findViewById(R.id.confirmPartBut);
        if (localKit != null){
            tvName.setText(localKit.getName());
            tvProbegE.setText(String.valueOf(localKit.getZamena()));
            tvProbegS.setText(String.valueOf(localKit.getProbeg()));
            tvCost.setText(String.valueOf(localKit.getCost()));
            confirmPartBut.setOnClickListener(v -> {
                localKit.setName(tvName.getText().toString());
                localKit.setProbeg(Integer.parseInt(String.valueOf(tvProbegS.getText())));
                localKit.setZamena(Integer.parseInt(String.valueOf(tvProbegE.getText())));
                localKit.setCost(Integer.parseInt(String.valueOf(tvCost.getText())));
                try (DbHelper dbHelper = new DbHelper(this)){
                    Dao<ServiceKit, Integer> serviceKits = DaoManager.createDao(new AndroidConnectionSource(dbHelper), ServiceKit.class);
                    serviceKits.update(localKit);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                finish();
            });
        }else {
            localKit = new ServiceKit();
            localKit.setCarId(localCarId);
            confirmPartBut.setOnClickListener(v -> {
                localKit.setName(tvName.getText().toString());
                localKit.setProbeg(Integer.parseInt(String.valueOf(tvProbegS.getText())));
                localKit.setZamena(Integer.parseInt(String.valueOf(tvProbegE.getText())));
                localKit.setCost(Integer.parseInt(String.valueOf(tvCost.getText())));
                try (DbHelper dbHelper = new DbHelper(this)){
                    Dao<ServiceKit, Integer> serviceKits = DaoManager.createDao(new AndroidConnectionSource(dbHelper), ServiceKit.class);
                    serviceKits.create(localKit);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                finish();
            });
        }



    }
   }