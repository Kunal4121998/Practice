/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dynamic_output;

/**
 *
 * @author KUNAL
 */
public class Output {
 
    private double opening_stock;
    private double daily_consumption;
    private double closing_stock;
    private String vendor;

    public double getOpening_stock() {
        return opening_stock;
    }

    public void setOpening_stock(double opening_stock) {
        this.opening_stock = opening_stock;
    }

    public double getDaily_consumption() {
        return daily_consumption;
    }

    public void setDaily_consumption(double daily_consumption) {
        this.daily_consumption = daily_consumption;
    }

    public double getClosing_stock() {
        return closing_stock;
    }

    public void setClosing_stock(double closing_stock) {
        this.closing_stock = closing_stock;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    @Override
    public String toString() {
        return "Output{" + "opening_stock=" + opening_stock + ", daily_consumption=" + daily_consumption + ", closing_stock=" + closing_stock + ", vendor=" + vendor + '}';
    }
    
    
}
