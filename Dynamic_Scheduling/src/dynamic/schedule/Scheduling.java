/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dynamic.schedule;

import Vendor.Vendors;
import com.codoid.products.exception.FilloException;
import dynamic.consumption.Consumption;
import dynamic.consumption.Model2;
import dynamic_output.Output;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author p.charde
 */
public class Scheduling {
    int l;
    ArrayList<Model2> list;
    ArrayList<Model2> list1;
    Consumption consumption;
    double opening_stock[];
     double vendor[];
    int lead_time=2;
    double min=100;
    boolean order_placed=false;
    double closing_stock[];
    double lotsize=20;
    double daily[];
    double max=120;
    ArrayList<Integer> leadArrayList;
    ArrayList<Output> out;
    ArrayList<Double> po_qtyArrayList;
    ArrayList<String> vendor_name;
    int x=0;
    String ven[];
    public Scheduling(){
        consumption=new Consumption();
        opening_stock=new double[100];
        vendor=new double[]{80,70,80};
        closing_stock=new double[100];
        daily=new double[100];
        leadArrayList=new ArrayList<>();
        po_qtyArrayList=new ArrayList<>();
        ven =new String[100];
        out=new ArrayList<>();
    }
       public void sched() throws NumberFormatException{
        try {
            
            
             ArrayList<Vendors> vendors=new ArrayList<>();
                vendors.add(new Vendors(4,20,80,"A"));
                 vendors.add(new Vendors(3,20,70,"B"));
                  vendors.add(new Vendors(4,20,60,"C"));
                   
            
                   Collections.sort(vendors);
            
            
            
//            leadArrayList.add(4);
//            leadArrayList.add(3);
//            leadArrayList.add(5);
//            po_qtyArrayList.add(80.0);
//             po_qtyArrayList.add(70.0);
//              po_qtyArrayList.add(80.0);
            
            
            consumption.getData();
            list=consumption.display();
            
            for(int i=0; i<list.size(); i++){
                ven[i]="NO";
            }
            
            for(int i=0; i<list.size();i++){
                opening_stock[i]=0;
                closing_stock[i]=0;
            }
            for(int i=0; i<list.size();i++){
                daily[i]=list.get(i).getDaily_consumption();
            }
            
            l=max();
            
           // lead_time=leadArrayList.get(l);
            
            
//            for (Model2 model2 : list) {
//                System.out.println(model2.getPlant()+" "+model2.getDate()+" "+model2.getDaily_consumption());
//            }
            
            Output output=new Output();
            opening_stock[0]=120;
            
            for(int i=0; i<list.size(); i++){
                
                double daily_con=daily[i];
                      if(closing_stock[i]!=0){
                          order_placed=false;
                          x++;
                          if(x==vendors.size())
                              x=0;
                          lead_time=vendors.get(x).getLead_time();
                      }
                          
                     if(closing_stock[i]==0)
                     closing_stock[i]=opening_stock[i]-daily_con;
                 
                if(order_placed==false){
                    double sum =0;
                      if(i<list.size()-lead_time+1){
                    for(int j=i+1; j<=i+lead_time;j++){
                        sum+=daily[j];
                        if(j==i+lead_time){
                            if((closing_stock[i]-sum)<min){
                                double order_quantity=Math.ceil((max-opening_stock[i]-daily_con)/10)*10;
                                closing_stock[j]=(closing_stock[i]-sum)+order_quantity;
                                order_placed=true;
                                ven[i]=vendors.get(x).getVendor_id();
                            }
                        }
                    }}
                }
                    opening_stock[i+1]=closing_stock[i];
        
                
                
            }
        } catch (FilloException ex) {
            Logger.getLogger(Scheduling.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(int i=0; i<list.size(); i++){
            Output output=new Output();
            output.setOpening_stock(opening_stock[i]);
            output.setDaily_consumption(daily[i]);
            output.setClosing_stock(closing_stock[i]);
            output.setVendor(ven[i]);
            //System.out.println(opening_stock[i]+" "+daily[i]+" "+closing_stock[i]);
            out.add(output);
        }
        
        for(Output output:out){
            System.out.println(output.getVendor()+" "+output.getOpening_stock()+" "+output.getDaily_consumption()+" "+output.getClosing_stock());
        }
        
       }    
       
   
       public int max(){
           
           double max1=vendor[0];
           	int index = 0;

		for (int i = 0; i < vendor.length; i++) 
		{
			if (max1 < vendor[i]) 
			{
				max1 = vendor[i];
				index = i;
			}
		}
           return index;
       }
       
       
    
}
