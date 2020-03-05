/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dynamic.schedule;

import com.codoid.products.exception.FilloException;
import dynamic.consumption.Consumption;
import dynamic.consumption.Model2;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author p.charde
 */
public class Scheduling {
    
    ArrayList<Model2> list;
    ArrayList<Model2> list1;
    Consumption consumption;
    double opening_stock[];
    int lead_time=2;
    double min=80;
    boolean order_placed=false;
    double closing_stock[];
    double lotsize=20;
    double daily[];
    public Scheduling(){
        consumption=new Consumption();
        opening_stock=new double[100];
        closing_stock=new double[100];
        daily=new double[100];
    }
       public void sched() throws NumberFormatException{
        try {
            
            consumption.getData();
            list=consumption.display();
            for(int i=0; i<list.size();i++){
                opening_stock[i]=0;
                closing_stock[i]=0;
            }
            for(int i=0; i<list.size();i++){
                daily[i]=list.get(i).getDaily_consumption();
            }
//            for (Model2 model2 : list) {
//                System.out.println(model2.getPlant()+" "+model2.getDate()+" "+model2.getDaily_consumption());
//            }
   
            opening_stock[0]=120;
            for(int i=0; i<list.size()-1; i++){
                double daily_con=daily[i];
                      if(closing_stock[i]!=0)
                          order_placed=false;
                     if(closing_stock[i]==0)
                     closing_stock[i]=opening_stock[i]-daily_con;
                 
                if(order_placed==false){
                    double sum =0;
                      if(i<list.size()-lead_time+1){
                    for(int j=i+1; j<=i+lead_time;j++){
                        sum+=daily[j];
                        if(j==i+lead_time){
                            if((closing_stock[i]-sum)<min){
                                closing_stock[j]=closing_stock[i]-sum+lotsize;
                                order_placed=true;
                            }
                        }
                    }}
                }
                    opening_stock[i+1]=closing_stock[i];
        
                
                
            }
        } catch (FilloException ex) {
            Logger.getLogger(Scheduling.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(int i=0; i<list.size()-1; i++){
            System.out.println(opening_stock[i]+" "+daily[i]+" "+closing_stock[i]);
        }
        
       }    
    
}
