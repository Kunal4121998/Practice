package com.automatic_van_allocation_vanMaster;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.automatic_van_allocation_interface.Interface;

public class VanMasterData implements Interface<VanMasterModel> {

	@Override
	public ArrayList<VanMasterModel> getData(String excelPath2) {
		ArrayList<VanMasterModel> masterList=new ArrayList<>();
		
		File masterfile = new File(excelPath2); 
		List<String> lines1;
		try {
			lines1 = Files.readAllLines(masterfile.toPath(), StandardCharsets.UTF_8);
			for (String line : lines1) { 
				   String[] array = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				   VanMasterModel vanMaster=new VanMasterModel();
				   vanMaster.setPlancode(array[2]);
				   vanMaster.setVehno(array[3]);
                                   vanMaster.setMaterial(array[5]);
				   masterList.add(vanMaster);
		}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error VanMaster");
		} 
		
		return masterList;
	}
	
	

}
