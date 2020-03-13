/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package date;

/**
 *
 * @author p.charde
 */
public class Date {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int days=30;
        String date = "07-03-2020";
        String[] dateString = new String[days];
        dateR D = new dateR();
        currentDate currD = new currentDate();
        currD.getDate();
        D.setDate(currD.dd,currD.mm,currD.yy);
        for(int i=0;i<days;i++){
            dateString[i] = date;
            D.nextDate();
            date = Integer.toString(D.dd) + "/" + Integer.toString(D.mm) + "/" + Integer.toString(D.yyyy);
            System.out.println(date);
        }
        
        
    }
    
}
