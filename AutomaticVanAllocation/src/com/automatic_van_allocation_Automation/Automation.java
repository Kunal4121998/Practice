package com.automatic_van_allocation_Automation;

import java.util.ArrayList;
import java.util.Collections;

import com.automatic_van_allocation_crm.CRMData;
import com.automatic_van_allocation_crm.CRMDataModel;
import com.automatic_van_allocation_interface.Interface;
import com.automatic_van_allocation_vanMaster.VanMasterData;
import com.automatic_van_allocation_vanMaster.VanMasterModel;
import com.automatic_van_allocation_vanStatus.VanStatusData;
import com.automatic_van_allocation_vanStatus.VanStatusModel;

public class Automation implements Interface<AutomationDataModel> {

	CRMData crmData;
	VanMasterData vanMasterData;
	VanStatusData vanStatusData;
	ArrayList<AutomationDataModel> automationList;
	ArrayList<CRMDataModel> crmList;
	ArrayList<VanMasterModel> masterList;
	ArrayList<VanStatusModel> statusList;
	
	 public Automation() {
		 crmData=new CRMData();
         vanMasterData=new VanMasterData();
         vanStatusData =new VanStatusData();
         automationList =new ArrayList<>();
         crmList=crmData.getData("C:\\Users\\KUNAL\\Desktop\\Van\\CRMData.csv");
         masterList=vanMasterData.getData("C:\\Users\\KUNAL\\Desktop\\Van\\VanMaster.csv");
         statusList=vanStatusData.getData("C:\\Users\\KUNAL\\Desktop\\Van\\Vanstatus.csv");
	}
	
	@Override
	public ArrayList<AutomationDataModel> getData(String ExcelPath) {
		// TODO Auto-generated method stub
		
		automationList =getResult();
		
		return automationList;
	}
	
	
	public ArrayList<AutomationDataModel> getResult() {
		   for (CRMDataModel listcrm : crmList.subList(1, crmList.size())) {
			  // System.out.println(listcrm.getIndent_id());
	  	   for (VanMasterModel listmaster : masterList.subList(1, masterList.size())) {
				if(listcrm.getPlant_id().equals(listmaster.getPlancode())) {
			       for (VanStatusModel liststatus : statusList.subList(1, statusList.size())) {
					  if(listmaster.getVehno().equals(liststatus.getVehicle_no()) && liststatus.getStatus().equalsIgnoreCase("ok for dispatch")) {
						if(listmaster.getMaterial().equalsIgnoreCase("Total Capacity (MT)")) {
	                                            //System.out.println(listcrm.getIndent_id()+"  "+liststatus.getVehicle_no());
	                                         int crm =listcrm.getQuantity();
	                                         Double sts=Double.parseDouble(liststatus.getCapacity());
	                                         if(!listcrm.isOrderDone())
	                                         if(sts>crm){
	                                             //System.out.println(liststatus.getVehicle_no());
	                                             liststatus.setStatus("Dispatched");
	                                             listcrm.setOrderDone(true);
	                                             AutomationDataModel vanAutomaticAllocation=new AutomationDataModel();
	                                             vanAutomaticAllocation.setIndent_id(listcrm.getIndent_id());
	                                             vanAutomaticAllocation.setIndent_date(listcrm.getIndent_date());
	                                             vanAutomaticAllocation.setCustomer_id(listcrm.getCustomer_id());
	                                             vanAutomaticAllocation.setPlant(Integer.parseInt(listcrm.getPlant_id()));
	                                             vanAutomaticAllocation.setQuantity(listcrm.getQuantity());
	                                             vanAutomaticAllocation.setVan_no(liststatus.getVehicle_no());
	                                             automationList.add(vanAutomaticAllocation);
	                                         }
	                                        }
					  }
			     	}
				}
			}
			}
		   return automationList;
		}

	  public void displayData(){
		  
		  Collections.sort(automationList);
		  for (AutomationDataModel automationDataModel : automationList) {
			
              String a=automationDataModel.getIndent_id();
              String b=automationDataModel.getIndent_date();
              String c=(automationDataModel.getCustomer_id());
              int d=automationDataModel.getPlant();
              int e=automationDataModel.getQuantity();
              String f=automationDataModel.getVan_no();
              
              System.out.println(a+"         "+b+"         "+d+"         "+e+"         "+f+"         "+c+"         ");
      } }
	
}


