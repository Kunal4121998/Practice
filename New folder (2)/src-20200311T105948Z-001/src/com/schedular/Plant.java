package com.schedular;

import java.util.Vector;

public class Plant {
    String PlantId;
    String DocumentDate;
    String MaterialId;
    String MaterialName;
    Vector<Vendor> SupplierName = new Vector<Vendor>();
    String OrderUnit;
    float OpeningStocks;
    float DailyConsumption;
    float MaxValue;
    float MinValue;

    public void setValues(String[] row){
        PlantId = row[0];
        currentDate curD = new currentDate();
        curD.getDate();
        this.DocumentDate = Integer.toString(curD.getDd()) + "/" + Integer.toString(curD.getMm()) + "/" + Integer.toString(curD.getYy());
        this.MaterialId = row[8];
        this.MaterialName = row[4];
        this.OrderUnit = row[6];
        this.OpeningStocks = 0;
    }

    public void setOpeningStock(float stock){
        this.OpeningStocks = stock;
    }

    public void setConsumption(float Consumption){
        this.DailyConsumption = Consumption;
    }

    public void setStorage(float Maximum_cap, float Minimum_cap){
        this.MaxValue = Maximum_cap;
        this.MinValue = Minimum_cap;
    }
}