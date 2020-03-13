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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class currentDate {
    int dd;
    int mm;
    int yy;
    public void getDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);
        String dd= "";
        String mm= "";
        String yy= "";
        int i=0;
        while(date.charAt(i) >= '0' && date.charAt(i) <= '9'){
            dd = dd + date.charAt(i);
            i++;
        }
        i++;
        while(date.charAt(i) >= '0' && date.charAt(i) <= '9'){
            mm = mm + (date.charAt(i));
            i++;
        }
        i++;
        while(i < date.length()){
            yy = yy + (date.charAt(i));
            i++;
        }

        this.dd = Integer.parseInt(dd.toString());
        this.mm = Integer.parseInt(mm.toString());
        this.yy = Integer.parseInt(yy.toString());
    }

    public int getDd(){
        return dd;
    }

    public int getMm(){
        return mm;
    }

    public int getYy() {
        return yy;
    }
}
