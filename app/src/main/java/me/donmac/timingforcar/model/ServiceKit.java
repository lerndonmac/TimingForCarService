package me.donmac.timingforcar.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "rashodniki")
public class ServiceKit {
    @DatabaseField(columnName = "id",generatedId = true)
    private int id;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "probeg")
    private int probeg;
    @DatabaseField(columnName = "cost")
    private int cost;
    @DatabaseField(columnName = "zamena")
    private int zamena;
    @DatabaseField(columnName = "car_id")
    private long carId;

    public ServiceKit() {
    }
    public ServiceKit(String name, int probeg, int zamena, int carId,int cost) {
        this.name = name;
        this.probeg = probeg;
        this.zamena = zamena;
        this.carId = carId;
        this.cost = cost;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getProbeg() {
        return probeg;
    }
    public void setProbeg(int probeg) {
        this.probeg = probeg;
    }
    public int getZamena() {
        return zamena;
    }
    public void setZamena(int zamena) {
        this.zamena = zamena;
    }
    public long getCarId() {
        return carId;
    }
    public void setCarId(long carId) {
        this.carId = carId;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
