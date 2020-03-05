/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dynamic;

import com.codoid.products.exception.FilloException;
import dynamic.consumption.Consumption;
import dynamic.consumption.Model2;
import dynamic.schedule.Scheduling;
import java.util.ArrayList;

/**
 *
 * @author p.charde
 */
public class Dynamic {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NumberFormatException, FilloException {
        // TODO code application logic here
     
    
        Scheduling scheduling=new Scheduling();
        scheduling.sched();
//        ArrayList<Model2> list= consumption.display();
//         for (Model2 model2 : list) {
//             System.out.println(model2.getId());
//        }
    
    }
    
    
}
