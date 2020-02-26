
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automatic_van_allocation_csv;

import com.automatic_van_allocation_Automation.Automation;
import com.automatic_van_allocation_Automation.AutomationDataModel;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author p.charde
 */
public class CsvWrite {
    
    ArrayList<AutomationDataModel> automationDataModels;
    Automation automation;
    public CsvWrite() throws IOException, ParseException{
    
        automation=new Automation();
        automationDataModels=automation.getResult();
        
        String csvFile="C:\\Users\\p.charde\\Desktop\\Van\\abc1.csv";
        FileWriter writer=new FileWriter(csvFile);
        
        CSVUtils.writeLine(writer, Arrays.asList("Indent_Id","Indent_Date","Plant_id","Quantity","Vehicle_no.","Customer_id"));
        
        for (AutomationDataModel automationDataModel : automationDataModels) {
              List<String> list=new ArrayList<>();
              list.add(automationDataModel.getIndent_id());
              list.add(automationDataModel.getIndent_date());
              list.add(String.valueOf(automationDataModel.getPlant()));
              list.add(String.valueOf(automationDataModel.getQuantity()));
              list.add(automationDataModel.getVan_no());
              list.add(automationDataModel.getCustomer_id());
              
              CSVUtils.writeLine(writer, list);
              
            
        }
        writer.flush();
        writer.close();
    }
}
