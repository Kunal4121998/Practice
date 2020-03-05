/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dynamic.consumption;

/**
 *
 * @author p.charde
 */
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author p.charde
 */
public class Consumption {
    
    	ArrayList<Model2> list;
	 ArrayList<Model2> mergedList;

         public Consumption(){
             list = new ArrayList<Model2>();
             mergedList = new ArrayList<Model2>();
         }
	public void getData() throws NumberFormatException, FilloException{
		int z=0; 
		
	       Fillo fillo=new Fillo();
	       Connection connection=fillo.getConnection("C:\\Users\\p.charde\\Desktop\\ds\\Consumption File.xlsx");
		int i1;
		int y;
		Scanner scanner =new Scanner(System.in);
		System.out.println("Enter Plant:");
		i1=scanner.nextInt();
		System.out.println("Enter Material:");
		y=scanner.nextInt();
		String strQuery="Select * from sheet1 where Plant='"+i1+"' and Material='"+y+"' ";
		
		Recordset recordset=connection.executeQuery(strQuery);
		System.out.println(recordset.getCount()); 
		
		while(recordset.next()){
			String inp=recordset.getField("Quantity");
			inp=inp.replaceAll("[-]","" );
			inp=inp.replaceAll("[,]", "");
			double b=Double.parseDouble(inp)/1000;
			Model2 model2=new Model2(recordset.getField("Plant"),recordset.getField("Posting"),recordset.getField("Material"),b,z++);
                    list.add(model2);
		//System.out.println(recordset.getField("Plant")+"  "+recordset.getField("Posting") +" "+recordset.getField("Material")+" "+inp);
		}
	        Collections.sort(list);
		recordset.close();
		connection.close();
		
		
         for(Model2 p : list) {
              int index = mergedList.indexOf(p);
              if(index != -1) {
              mergedList.set(index, mergedList.get(index).merge(p));
           } else {
            mergedList.add(p);
       }
             
     }}
          public ArrayList<Model2> display(){
              for (Model2 model2 : mergedList) {
                  double x=Math.ceil(model2.getDaily());
                  model2.setDaily_consumption(x);
                  System.out.println(model2.getDaily_consumption());
                 // System.out.println(model2.getId());
             }
              
             return mergedList;
          }
}