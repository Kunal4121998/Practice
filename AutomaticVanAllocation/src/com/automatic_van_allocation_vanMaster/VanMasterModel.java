package com.automatic_van_allocation_vanMaster;

public class VanMasterModel {


	private String plancode;
	private String vehno;
        private String material;

  
        public String getMaterial() {
        return material;
    }

        public void setMaterial(String material) {
              this.material = material;
        }
	
	public String getPlancode() {
		return plancode;
	}
	public void setPlancode(String plancode) {
		this.plancode = plancode;
	}
	public String getVehno() {
		return vehno;
	}
	public void setVehno(String vehno) {
		this.vehno = vehno;
	}
	
}
