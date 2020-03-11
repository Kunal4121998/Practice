package com.schedular;

public class dateR {
    int dd;
    int mm;
    int yyyy;

    public int d(){
        return this.dd;
    }

    public int m(){
        return this.mm;
    }

    public int y(){
        return this.yyyy;
    }
    public void setDate(int d, int m, int y){
        this.dd = d;
        this.mm = m;
        this.yyyy = y;
    }

    public int findDiff(dateR D){
        int t=0;
        while(this.yyyy != D.y() || this.mm != D.m() || this.dd != D.d()){
            D.nextDate();
            t++;
        }
        return t;
    }

    public void nextDate(){
        if(this.mm == 2){
            if (((this.yyyy % 4 == 0) && (this.yyyy % 100 != 0)) || (this.yyyy % 400 == 0) && this.dd == 28){
                this.dd++;
            }
            else if(this.dd < 28){
                this.dd++;
            }
            else{
                this.dd = 1;
                this.mm = 3;
            }
        }
        else if(this.mm == 1 || this.mm ==3 || this.mm == 5 || this.mm == 7 || this.mm == 8 || this.mm == 10 || this.mm == 12){
            if(this.dd < 31){
                this.dd++;
            }
            else if(this.dd == 31){
                if(this.mm == 12){
                    this.mm = 1;
                    this.yyyy++;
                }
                else{
                    this.mm += 1;
                }
                this.dd = 1;
            }
        }
        else{
            if(this.dd<30){
                this.dd++;
            }
            else{
                this.mm++;
                this.dd = 1;
            }
        }
    }
}
