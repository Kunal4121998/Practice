package com.schedular;
import java.util.Vector;

public class Vendor {
    String VendorName;
    float lotSize;
    Vector<String> POID = new Vector<String>();
    Vector<Float> Quantity = new Vector<Float>();
    Vector<Float> TotalQuan = new Vector<Float>();
    int Duration;
    float MaxCapacity;
    Vector<Integer> ASNTime = new Vector<Integer>();
    Vector<String> ASNDate = new Vector<String>();
    Vector<Float> ASNQuan = new Vector<Float>();
    Vector<String> ASNPoid = new Vector<String>();

    public void setVendorName(String name){
        this.VendorName = name;
    }

    public void setVendors(String poid, float tquan, float quan){
        this.POID.add(poid);
        this.Quantity.add(quan);
        this.TotalQuan.add(tquan);
    }

    public void setLotSize(int duration, float lotsize, float cap){
        this.Duration = duration;
        this.lotSize = lotsize;
        this.MaxCapacity = cap;
    }

    public void setASNvalue(String poid, String date, float quan){
        int d,m,y;
        StringBuilder dd= new StringBuilder();
        StringBuilder mm= new StringBuilder();
        StringBuilder yy= new StringBuilder();
        int i=0;
        while(date.charAt(i) >= '0' && date.charAt(i) <= '9'){
            dd.append(date.charAt(i));
            i++;
        }
        i++;
        while(date.charAt(i) >= '0' && date.charAt(i) <= '9'){
            mm.append(date.charAt(i));
            i++;
        }
        i++;
        while(i<date.length()){
            yy.append(date.charAt(i));
            i++;
        }
        d = Integer.parseInt(dd.toString());
        m = Integer.parseInt(mm.toString());
        y = Integer.parseInt(yy.toString());

        int cd,cm,cy;
        currentDate curD = new currentDate();
        curD.getDate();
        cd = curD.getDd();
        cm = curD.getMm();
        cy = curD.getYy();

        dateR D1 = new dateR();
        dateR D2 = new dateR();
        D1.setDate(d,m,y);
        D2.setDate(cd,cm,cy);
        if(D1.y() > D2.y()){
            i = D1.findDiff(D2);
        }
        else if(D1.y() < D2.y()){
            i = -1*D2.findDiff(D1);
        }
        else{
            if(D1.m() > D2.m()){
                i = D1.findDiff(D2);
            }
            else if(D1.m() < D2.m()){
                i = -1*D2.findDiff(D1);
            }
            else{
                i = D1.d() - D2.d();
            }
        }
        if((i+this.Duration) < 0 ){
            return;
        }
        ASNDate.add(String.valueOf(d) + "/" + String.valueOf(m) + "/" + String.valueOf(y));
        ASNTime.add(i);
        ASNPoid.add(poid);
        ASNQuan.add(quan);

        for(int j=0;j<POID.size();j++){
            if(poid.equals(this.POID.get(j))){
                this.Quantity.set(j,this.Quantity.get(j) - quan) ;
            }
        }
    }
}
