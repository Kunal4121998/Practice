/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vendor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Vector;

/**
 *
 * @author KUNAL
 */
public class Vendor_call {
    
    public static void main(String[] args) {
        ArrayList<Vendors> vendor=new ArrayList<>();
        vendor.add(new Vendors(4,20,80,"A"));
         vendor.add(new Vendors(3,20,70,"B"));
          vendor.add(new Vendors(4,20,60,"C"));
           vendor.add(new Vendors(5,20,80,"D"));
          
           Collections.sort(vendor);
           
              System.out.println("the items are "+vendor.get(0)); 
       
    }
}
