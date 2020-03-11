/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vendor;

/**
 *
 * @author KUNAL
 */
public class Vendors implements Comparable<Vendors>{
    private int lead_time;
    private int lot_size;
    private int capacity;
    private String vendor_id;

    public int getLead_time() {
        return lead_time;
    }

   

    public int getLot_size() {
        return lot_size;
    }

    

    public int getCapacity() {
        return capacity;
    }

   
    public String getVendor_id() {
        return vendor_id;
    }

    public Vendors(int lead_time, int lot_size, int capacity, String vendor_id) {
        this.lead_time = lead_time;
        this.lot_size = lot_size;
        this.capacity = capacity;
        this.vendor_id = vendor_id;
    }

  
    @Override
    public int compareTo(Vendors obj) {
        return lead_time-obj.lead_time;
    }

    @Override
    public String toString() {
        return "Vendors{" + "lead_time=" + lead_time + ", lot_size=" + lot_size + ", capacity=" + capacity + ", vendor_id=" + vendor_id + '}';
    }
    
    
    
}
