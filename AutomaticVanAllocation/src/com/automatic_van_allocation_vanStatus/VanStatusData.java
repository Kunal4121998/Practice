package com.automatic_van_allocation_vanStatus;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.automatic_van_allocation_interface.Interface;

public class VanStatusData implements Interface<VanStatusModel>{

	@Override
	public ArrayList<VanStatusModel> getData(String excelPath3) {
		ArrayList<VanStatusModel> statusList=new ArrayList<>();
		
		File statusfile = new File(excelPath3); 
		List<String> lines2;
		try {
			lines2 = Files.readAllLines(statusfile.toPath(), StandardCharsets.UTF_8);
			for (String line : lines2) { 
				   String[] array = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				   VanStatusModel vanStatus=new VanStatusModel();
				   vanStatus.setStatus(array[1]);
				   vanStatus.setVehicle_no(array[2]);
				   vanStatus.setCapacity(array[4]);
				   statusList.add(vanStatus);
		}
		}catch (IOException e) {
			System.out.println("VanStatus Error");
		} 
		
		
		return statusList;
	}
	

}
