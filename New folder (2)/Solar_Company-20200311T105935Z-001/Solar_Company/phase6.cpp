#include<bits/stdc++.h>
using namespace std;

int LEADTIME = 0;


class dateR{
    public:
    int dd;
    int mm;
    int yyyy;

    public:
    void setDate(int d, int m, int y){
        dd = d;
        mm = m;
        yyyy = y;
    }

    public:
    int findDiff(dateR D){
        int t=0;
        while(yyyy != D.yyyy || mm != D.mm || dd != D.dd){
            D.nextDate();
            t++;
        }
        return t;        
    }

    public:
    void nextDate(){
        if(mm == 2){
            if (((yyyy % 4 == 0) && (yyyy % 100 != 0)) || (yyyy % 400 == 0) && dd == 27){
                dd++;
            }
            else if(dd < 27){
                dd++;
            }
            else{
                dd = 1;
                mm = 3;
            }
        }
        else if(mm == 1 || mm ==3 || mm == 5 || mm == 7 || mm == 8 || mm == 10 || mm == 12){
            if(dd < 31){
                dd++;
            }
            else if(dd == 31){
                if(mm == 12){
                    mm = 1;
                    yyyy++;
                }
                else{
                    mm += 1;
                }
                dd = 1;
            }
        }
        else{
            if(dd<30){
                dd++;
            }
            else{
                mm++;
                dd = 1;
            }
        }
        // return to_string(dd) + "/" + to_string(mm) + "/"  + to_string(yyyy);
    }
};


class Vendor{
    public:
    string VendorName;
    float lotSize;
    vector<string> POID;
    vector<float> Quantity;
    vector<float> TotalQuan;
    int Duration;
    float MaxCapacity;
    vector<int> ASNTime;
    vector<string> ASNDate;
    vector<float> ASNQuan;
    vector<string> ASNPoid;

    public:
    void setVendorName(string name){
        VendorName = name;
        // Duration = duration;
        // ASN = asn;
    }

    public:
    void setVendors(string poid, float tquan, float quan){
        POID.push_back(poid);
        Quantity.push_back(quan);
        TotalQuan.push_back(tquan);
    }

    public:
    void setLotSize(int duration, float lotsize, float cap){
        Duration = duration;
        lotSize = lotsize;
        MaxCapacity = cap;
    }

    public:
    void setASNvalue(string poid,string date, float quan){
        int d,m,y;
        string dd="",mm="",yy="";
        int i=0;
        while(date[i] >= '0' && date[i] <= '9'){
            dd = dd + date[i];
            i++;
        }
        i++;
        while(date[i] >= '0' && date[i] <= '9'){
            mm = mm + date[i];
            i++;
        }
        i++;
        while(date[i] >= '0' && date[i] <= '9'){
            yy = yy + date[i];
            i++;
        }
        // cout<<dd<<endl;
        d = stoi(dd);
        m = stoi(mm);
        y = stoi(yy);

        int cd,cm,cy;
        time_t now = time(0);
        tm *ltm = localtime(&now);
        cd = ltm->tm_mday;
        cm = 1 + ltm->tm_mon;
        cy = 1900 + ltm->tm_year;
        dateR D1,D2;
        D1.setDate(d,m,y);
        D2.setDate(cd,cm,cy);
        if(D1.yyyy > D2.yyyy){
            i = D1.findDiff(D2);
        }
        else if(D1.yyyy < D2.yyyy){
            i = -1*D2.findDiff(D1);
        }
        else{
            if(D1.mm > D2.mm){
                i = D1.findDiff(D2);
            }
            else if(D1.mm < D2.mm){
                i = -1*D2.findDiff(D1);
            }
            else{
                i = D1.dd - D2.dd;
            }
        }
        if((i+Duration) < 0 ){
            return;
        }
        ASNDate.push_back(to_string(d) + "/" + to_string(m) + "/" + to_string(y));
        ASNTime.push_back(i);
        ASNPoid.push_back(poid);
        ASNQuan.push_back(quan);

        for(int j=0;j<POID.size();j++){
            if(POID[j] == poid){
                Quantity[j] -= quan;
            }
        }
    }
};

class Plant{
    public:
    string PlantId;
    string DocumentDate;
    string MaterialId;
    string MaterialName;
    // string MaterialType;
    vector<Vendor> SupplierName;
    string OrderUnit;
    float OpeningStocks;
    // string MaterialGroup;
    float DailyConsumption;
    float MaxValue;
    float MinValue;
    // float lotSize;

    public:
    void setValues(vector<string> row){
        PlantId = row[0];
        time_t now = time(0);
        tm *ltm = localtime(&now);
        DocumentDate = to_string(ltm->tm_mday) + "/" + to_string(1 + ltm->tm_mon) + "/" + to_string(1900 + ltm->tm_year);
        MaterialId = row[8];
        MaterialName = row[4];
        // MaterialType = row[5];
        OrderUnit = row[6];
        // MaterialGroup = row[10];
        OpeningStocks = 0;
    }

    public:
    void setOpeningStock(float stock){
        OpeningStocks = stock;
    }

    public:
    void setConsumption(float Consumption){
        DailyConsumption = Consumption;
        // MaxValue = Max;
        // MinValue = Min;
    }

    public:
    void setStorage(float Maximum_cap, float Minimum_cap){
        MaxValue = Maximum_cap;
        MinValue = Minimum_cap;
    }
};

vector<Plant> P;

int CheckPlantMaterial(vector<string> row,string PlantID, string MaterialID, string vendor){
    int Psize = P.size();
    for(int i=0;i<Psize;i++){
        if(P[i].PlantId == PlantID && P[i].MaterialId == MaterialID){
            P[i].DocumentDate == row[2];
            for(int j=0;j<P[i].SupplierName.size();j++){
                if(P[i].SupplierName[j].VendorName == vendor){
                    P[i].SupplierName[j].setVendors(row[1],stof(row[5]),stof(row[7]));
                    return 1;
                }
            }
            Vendor V;
            V.setVendorName(row[3]);
            V.setVendors(row[1],stof(row[5]),stof(row[7]));
            P[i].SupplierName.push_back(V);
            return 1;
        }
    }
    Plant P1;
    P1.setValues(row);
    Vendor V;
    V.setVendorName(row[3]);
    V.setVendors(row[1],stof(row[5]),stof(row[7]));
    P1.SupplierName.push_back(V);
    P.push_back(P1);
    return 1;
}

int setStocks(string MaterialId, string PlantID, float stock){
    int Psize = P.size();
    for(int i=0;i<Psize;i++){
        if(P[i].MaterialId == MaterialId && P[i].PlantId == PlantID){
            P[i].setOpeningStock(stock);
            return 1;
        }
    }
    return -1;
}

int setDailyConsumption(string PlantID, string MaterialId, float consumption){
    int Psize = P.size();
    for(int i=0;i<Psize;i++){
        if(P[i].PlantId == PlantID && P[i].MaterialId == MaterialId){
            P[i].setConsumption(consumption);
        }
    }
    return 1;
}

int setVendorLotSize(string PlantID, string MaterialId, string VendorName, int leadTime, float lot_size, float cap){
    int Psize = P.size();
    for(int i=0;i<Psize;i++){
        if(P[i].PlantId == PlantID && P[i].MaterialId == MaterialId){
            for(int j=0;j<P[i].SupplierName.size();j++){
                // cout<<lot_size<<endl;
                if(P[i].SupplierName[j].VendorName == VendorName){
                    
                    P[i].SupplierName[j].setLotSize(leadTime,lot_size,cap);
                }
            }
        }
    }
}

int MaxBalanceQty(float *TotalPOQuantity, int no_of_vendors, bool *check){
    int max_index = 0;
    int max_value = 0;
    // cout<<"In Function: ";
    for(int i=0;i<no_of_vendors;i++){
        if(max_value < TotalPOQuantity[i] && check[i] == true){
            max_index = i;
            max_value = TotalPOQuantity[i];
        }
        // cout<<TotalPOQuantity[i]<<" ";
    }
    // cout<<endl;
    return max_index;
}

int setStorageCap(string PlantId, string MatID, float MinCap, float MaxCap){
    for(int i=0;i<P.size();i++){
        if(P[i].PlantId == PlantId && P[i].MaterialId == MatID){
            P[i].setStorage(MaxCap,MinCap);
        }
    }
}

int setASN(vector<string> row){
    for(int i=0;i<P.size();i++){
        if(P[i].PlantId == row[0] && P[i].MaterialId == row[5]){
            for(int j=0;j<P[i].SupplierName.size();j++){
                if(P[i].SupplierName[j].VendorName == (row[3])){
                    for(int k=0;k<P[i].SupplierName[j].POID.size();k++){
                        if(P[i].SupplierName[j].POID[k] == row[1]){
                            P[i].SupplierName[j].setASNvalue(row[1],row[26],stof(row[27]));
                            return 1;
                        }
                    }
                    return 1;
                }
            }
            return 1;
        }
    }
    return 1;
}

int updateFile(int index, int schedule_id){
    // cout<<index<<endl;
    fstream fin;
    fin.open("Plantnew.csv", ios::out | ios::app);
    int no_of_vendors = P[index].SupplierName.size();
    float maxCapOfVendor[no_of_vendors];
    for(int i=0;i<no_of_vendors;i++){
        maxCapOfVendor[i] = P[index].SupplierName[i].MaxCapacity;
    }
    float lotSize[no_of_vendors];
    for(int i=0;i<no_of_vendors;i++){
        lotSize[i] = P[index].SupplierName[i].lotSize;
    }

    float DailyConsumption = P[index].DailyConsumption;
    // int storageCap[no_of_vendors] = {350, 350, 350};
    float OpeningStock = P[index].OpeningStocks;
    float MaximumStock = P[index].MinValue;
    float MaximumStockMax = P[index].MaxValue;
    int days = 7+LEADTIME;
    int batch[no_of_vendors];                                             // maxCapOfVendor/lotSize
    bool check[no_of_vendors];
    for(int i=0;i<no_of_vendors;i++){
        batch[i] = maxCapOfVendor[i]/lotSize[i];
        check[i] = true;
    }
    float table[days][no_of_vendors+2];
    for(int i=0;i<days;i++){
        for(int j=0;j<(no_of_vendors+2);j++){
            table[i][j] = 0;
        }
    }
    table[0][0] = OpeningStock;
    int Flag = 0;
    int duration[no_of_vendors];
    for(int i=0;i<no_of_vendors;i++){
        duration[i] = P[index].SupplierName[i].Duration;
    }

    string date = P[index].DocumentDate;
    string dateString[days];
    time_t now = time(0);
    tm *ltm = localtime(&now);
    // DocumentDate = to_string(ltm->tm_mday) + "/" + to_string(1 + ltm->tm_mon) + "/" + to_string(1900 + ltm->tm_year);
    dateR D;
    D.setDate(ltm->tm_mday,1 + ltm->tm_mon,1900 + ltm->tm_year);
    for(int i=0;i<days;i++){
        dateString[i] = date;
        D.nextDate();
        date = to_string(D.dd) + "/" + to_string(D.mm) + "/" + to_string(D.yyyy);
    }

    float TotalPOQuantity[no_of_vendors];
    float _TotalPOQuantity[no_of_vendors];
    string POID[no_of_vendors];
    int skip = 0;
    for(int i=0;i<no_of_vendors;i++){
        TotalPOQuantity[i] = P[index].SupplierName[i].Quantity[0];
        _TotalPOQuantity[i] = P[index].SupplierName[i].TotalQuan[0];
        POID[i] = P[index].SupplierName[i].POID[0];
        if(P[index].SupplierName[i].ASNPoid.size()>0){
            for(int j=0;j<P[index].SupplierName[i].ASNPoid.size();j++){
                // for(int k=0;k<P[index].SupplierName[i].POID.size();k++){
                //     if(P[index].SupplierName[i].POID[k] == P[index].SupplierName[i].ASNPoid[j]){
                //         // fin << P[index].PlantId<<","<<P[index].SupplierName[i].ASNPoid[j] <<","<<P[index].SupplierName[i].TotalQuan[k]<<","<<P[index].SupplierName[j].VendorName <<",1," << P[index].MaterialId <<"," <<P[index].MaterialName<<",EA,"<<dateString[P[index].SupplierName[i].ASNTime[j]] <<",,"<< P[index].SupplierName[i].ASNQuan[j] << endl;
                //     }
                // }
                // File Print
                table[P[index].SupplierName[i].ASNTime[j] + P[index].SupplierName[i].Duration][1 + i] += P[index].SupplierName[i].ASNQuan[j];
            }
            skip = 1;
        }
        while(TotalPOQuantity[i] == 0 && P[index].SupplierName[i].POID.size() > 0){
            P[index].SupplierName[i].Quantity.erase( P[index].SupplierName[i].Quantity.begin());
            P[index].SupplierName[i].TotalQuan.erase( P[index].SupplierName[i].TotalQuan.begin());
            P[index].SupplierName[i].POID.erase(P[index].SupplierName[i].POID.begin());
            
            if(P[index].SupplierName[i].POID.size() != 0){
                TotalPOQuantity[i] = P[index].SupplierName[i].Quantity[0];
                _TotalPOQuantity[i] = P[index].SupplierName[i].TotalQuan[0];
                POID[i] = P[index].SupplierName[i].POID[0];
            }
        }
    }
     
    float consume = 0;
    // float oldconsume = 0;
    for(int i=0;i<7;i++){

        float ClosingStock = table[i][0] - DailyConsumption;
        for(int j=1;j<=no_of_vendors;j++){
            ClosingStock += table[i][j];
            table[i][j] = 0;
        }
        consume = 0;
        // oldconsume = 0;
        if(!skip || i>0){
            jump:
            Flag = MaxBalanceQty(TotalPOQuantity,no_of_vendors,check);
            if((ClosingStock+consume) < MaximumStock && (maxCapOfVendor[Flag]+lotSize[Flag]) >= consume){
                if(lotSize[Flag] > 0){
                    if(TotalPOQuantity[Flag] > lotSize[Flag]){
                        if( (ClosingStock + consume + lotSize[Flag]) <= MaximumStockMax){
                            table[i + P[index].SupplierName[Flag].Duration][1 + Flag] += lotSize[Flag];                      //Lot Size Allocated
                            TotalPOQuantity[Flag] -= lotSize[Flag];
                            consume += lotSize[Flag];
                        }
                        else{
                            check[Flag] = false;
                        }
                        // Flag = MaxBalanceQty(TotalPOQuantity,no_of_vendors,check);
                        if((ClosingStock+consume) < MaximumStock || (ClosingStock+consume) > MaximumStockMax){
                            goto jump;
                        }
                    }

                    else if(TotalPOQuantity[Flag] >0){
                        if((ClosingStock + consume + TotalPOQuantity[Flag]) <= MaximumStockMax){
                            // table[i][1+Flag] = table[i + P[index].SupplierName[Flag].Duration][1 + Flag];
                            table[i + P[index].SupplierName[Flag].Duration][1 + Flag] += TotalPOQuantity[Flag];
                            consume += TotalPOQuantity[Flag];

                            fin << P[index].PlantId<<","<<POID[Flag] <<","<<P[index].SupplierName[Flag].TotalQuan[0]<<","<<P[index].SupplierName[Flag].VendorName <<",1," << P[index].MaterialId <<"," <<P[index].MaterialName<<",EA,SCH_"<<schedule_id<<","<<dateString[i] <<","<< abs(table[i][1+Flag] - table[i + P[index].SupplierName[Flag].Duration][1 + Flag]) <<",No"<<",ASN_"<<schedule_id++<<","<<  endl;
                            
                            
                            if(P[index].SupplierName[Flag].Quantity.size()>1){
                                P[index].SupplierName[Flag].Quantity.erase(P[index].SupplierName[Flag].Quantity.begin());
                                P[index].SupplierName[Flag].TotalQuan.erase(P[index].SupplierName[Flag].TotalQuan.begin());
                                P[index].SupplierName[Flag].POID.erase(P[index].SupplierName[Flag].POID.begin());
                                // oldconsume = consume;
                            }
                            else{
                                P[index].SupplierName[Flag].Quantity[0] = 0;
                                P[index].SupplierName[Flag].POID[0] = "";
                                P[index].SupplierName[Flag].TotalQuan[0] = 0;
                            }
                            TotalPOQuantity[Flag] = P[index].SupplierName[Flag].Quantity[0];
                            _TotalPOQuantity[Flag] = P[index].SupplierName[Flag].TotalQuan[0];
                            POID[Flag] = P[index].SupplierName[Flag].POID[0];
                            table[i][1+Flag] = table[i + P[index].SupplierName[Flag].Duration][1 + Flag];
                        }
                        else{
                            check[Flag] = false;
                        }
                        // Flag = MaxBalanceQty(TotalPOQuantity,no_of_vendors,check);
                        if((ClosingStock+consume) < MaximumStock || (ClosingStock+consume) > MaximumStockMax){
                            goto jump;
                        }
                    }
                }
                else{
                    float diff = MaximumStock - ClosingStock;
                    float ddiff = MaximumStockMax - MaximumStock;
                    if(TotalPOQuantity[Flag] > (diff + (ddiff/10))){
                        if( (ClosingStock + diff + consume + (ddiff/10)) <= MaximumStockMax){
                            table[i + P[index].SupplierName[Flag].Duration][1 + Flag] += diff + (ddiff/10);                      //Lot Size Allocated
                            TotalPOQuantity[Flag] -= diff + (ddiff/10);
                            consume += lotSize[Flag];
                        }
                        else if((ClosingStock + diff + consume) <= MaximumStockMax){
                            table[i + P[index].SupplierName[Flag].Duration][1 + Flag] += diff ;                      //Lot Size Allocated
                            TotalPOQuantity[Flag] -= diff;
                            consume += lotSize[Flag];
                        }
                        else{
                            check[Flag] = false;
                        }
                        // Flag = MaxBalanceQty(TotalPOQuantity,no_of_vendors,check);
                        if((ClosingStock+consume) < MaximumStock || (ClosingStock+consume) > MaximumStockMax){
                            goto jump;
                        }
                    }

                    else if(TotalPOQuantity[Flag] >0){
                        if(TotalPOQuantity[Flag] <= MaximumStockMax){
                            // table[i][1+Flag] = table[i + P[index].SupplierName[Flag].Duration][1 + Flag];
                            table[i + P[index].SupplierName[Flag].Duration][1 + Flag] += TotalPOQuantity[Flag];
                            consume += TotalPOQuantity[Flag];
                            fin << P[index].PlantId<<","<<POID[Flag] <<","<<P[index].SupplierName[Flag].TotalQuan[0]<<","<<P[index].SupplierName[Flag].VendorName <<",1," << P[index].MaterialId <<"," <<P[index].MaterialName<<",EA,SCH_"<<schedule_id<<","<<dateString[i] <<","<< abs(table[i][1+Flag] - table[i + P[index].SupplierName[Flag].Duration][1 + Flag]) <<",No"<<",ASN_"<<schedule_id++<<","<<  endl;
                            // fin << abs(table[i][1+Flag] - table[i + P[index].SupplierName[Flag].Duration][1 + Flag]) << "," <<  dateString[i] << "," << POID[Flag] << "," << P[index].SupplierName[Flag].VendorName << "," << P[index].MaterialId << endl;
                            if(P[index].SupplierName[Flag].Quantity.size()>1){
                                P[index].SupplierName[Flag].Quantity.erase(P[index].SupplierName[Flag].Quantity.begin());
                                P[index].SupplierName[Flag].TotalQuan.erase(P[index].SupplierName[Flag].TotalQuan.begin());
                                P[index].SupplierName[Flag].POID.erase(P[index].SupplierName[Flag].POID.begin());
                                // oldconsume = consume;
                            }
                            else{
                                P[index].SupplierName[Flag].Quantity[0] = 0;
                                P[index].SupplierName[Flag].TotalQuan[0] = 0;
                                P[index].SupplierName[Flag].POID[0] = "";
                            }
                            TotalPOQuantity[Flag] = P[index].SupplierName[Flag].Quantity[0];
                            _TotalPOQuantity[Flag] = P[index].SupplierName[Flag].TotalQuan[0];
                            POID[Flag] = P[index].SupplierName[Flag].POID[0];
                            table[i][1+Flag] = table[i + P[index].SupplierName[Flag].Duration][1 + Flag];
                            // oldconsume = consume;
                        }
                        else{
                            check[Flag] = false;
                        }
                        // Flag = MaxBalanceQty(TotalPOQuantity,no_of_vendors,check);
                        if((ClosingStock+consume) < MaximumStock || (ClosingStock+consume) > MaximumStockMax){
                            goto jump;
                        }
                    }
                }
            }

            for(int j=0;j<no_of_vendors;j++){
                if(abs(table[i][1+j] - table[i + P[index].SupplierName[j].Duration][1 + j])>0){
                    float ll = abs(table[i][1+Flag] - table[i + P[index].SupplierName[Flag].Duration][1 + Flag]) - P[index].SupplierName[j].MaxCapacity;
                    if(ll>0){
                        TotalPOQuantity[Flag] += ll; 
                    }
                    else{
                        ll=0;
                    }
                    fin << P[index].PlantId<<","<<POID[j] <<","<<P[index].SupplierName[j].TotalQuan[0]<<","<<P[index].SupplierName[j].VendorName <<",1," << P[index].MaterialId <<"," <<P[index].MaterialName<<",EA,SCH_"<<schedule_id<<","<<dateString[i] <<","<< abs(table[i][1+Flag] - table[i + P[index].SupplierName[Flag].Duration][1 + Flag]) - ll <<",No"<<",ASN_"<<schedule_id++<<","<< endl;
                    // fin << abs(table[i][1+Flag] - table[i + P[index].SupplierName[Flag].Duration][1 + Flag]) << "," << dateString[i] << "," << POID[j] << "," << P[index].SupplierName[j].VendorName << "," << P[index].MaterialId << endl;
                }
            }
            // cout<<i+1<<" ";
            for(int k=0;k<no_of_vendors;k++){
                // cout<<TotalPOQuantity[k]<<" ";
                check[k] = true;
            }
            // cout<<endl;
        }
        table[i][no_of_vendors + 1] = ClosingStock;
        if(i < (days - 1)){                                                     //days
            table[i+1][0] = ClosingStock;
        }
    }
    fin.close();
    return schedule_id;
}

/*
    Working File : Purchase_Order_Master_File.csv
    Consumption File : Consumption_File.csv
    Opening Stock FIle : Opening_Stock.csv
    Vendor File : Vendor_Master_File.csv
    Storage File : Storage_Capacity.csv
    ASN File : ASN_File .csv
*/


int main(){
    int schedule_id;
    vector<string> row;
    string line, word, temp;
    fstream fworking,fconsumption,fopening,fstorage,fvendor,fasn;
    fstream fschedule;
    fschedule.open("schedule.txt",ios::in);
    getline(fschedule,line);
    row.clear(); 
    stringstream s(line);
    getline(s, word);
    schedule_id = stoi(word);
    fschedule.close();

    fworking.open("Purchase_Order_Master_File.csv", ios::in);
    getline(fworking, line);
    while (getline(fworking, line)) { 
        row.clear(); 
        stringstream s(line);  
        while(getline(s, word, ',')) {
            if(word.size() != 0){
                row.push_back(word);
            }
        }
        // string ss = row[12];
        // if(row[12] == ss){
        if(row.size()>0){
            CheckPlantMaterial(row,row[0],row[8],row[3]);
        }
        // }
    }
    fworking.close();
    fopening.open("Opening_Stock.csv", ios::in);
    getline(fopening, line);
    while (getline(fopening, line)) { 
        row.clear(); 
        stringstream s(line);  
        while(getline(s, word, ',')) {
            if(word.size() != 0){
                row.push_back(word);
            }
        }
        setStocks(row[1],row[3],stof(row[9]));
    }
    fopening.close();

    fconsumption.open("Consumption_File.csv", ios::in);
    getline(fconsumption, line);
    while (getline(fconsumption, line)) { 
        row.clear(); 
        stringstream s(line);  
        while(getline(s, word, ',')) {
            if(word.size() != 0){
                row.push_back(word);
            }
        }
        setDailyConsumption(row[0],row[4],abs(stof(row[6])));
    }
    fconsumption.close();
    
    fvendor.open("Vendor_Master_File.csv", ios::in);
    getline(fvendor, line);
    while (getline(fvendor, line)) { 
        row.clear(); 
        stringstream s(line);  
        while(getline(s, word, ',')) {
            if(word.size() != 0){
                row.push_back(word);
            }
        }
        LEADTIME = max(LEADTIME,stoi(row[4]));
        // cout<<"error"<<endl;
        setVendorLotSize(row[0],row[1],row[3],stoi(row[4]),stof(row[5]),stof(row[6]));
    }
    fvendor.close();
    // return 0;

    fstorage.open("Storage_Capacity.csv", ios::in);
    getline(fstorage, line);
    while (getline(fstorage, line)) { 
        row.clear(); 
        stringstream s(line);  
        while(getline(s, word, ',')) {
            if(word.size() != 0){
                row.push_back(word);
            }
        }
        setStorageCap(row[0],row[1],stof(row[3]),stof(row[4]));
    }
    fstorage.close();

    fasn.open("ASN_File .csv", ios::in);
    getline(fasn, line);
    while (getline(fasn, line)) { 
        row.clear(); 
        stringstream s(line);  
        while(getline(s, word, ',')) {
            // if(word.size() != 0){
                row.push_back(word);
            // }
        }
        // cout<<row.size()<<endl;
        if(row[27].size() != 0 && row[26].size()!=0){
            setASN(row);
            // cout<<"in asn"<<endl;
        }
    }
    fasn.close();

    fstream fin;
    fin.open("Plantnew.csv", ios::out | ios::app);
    fin << "Plant Code,PO Number,PO Qunatity,Vendor Name,PO item No.,Material Number,Part Description,UOM,Schedule ID,Date of Planned Dispatch,Qty to be Dispatched,Reciept Done,ASN No,Vendor Acknowledgement,Transporter,Name of transporter,Acknowledgement,Van No (Editable),LR Number,Driver No.,Driver Name,Invoice No.,Invoice Date,Van No. (Editable),Driver Mobile No.,ASN No. (Auto Generated),Actual Dispatch Date,Actual Qty Dispatched"<<endl;		
    fin.close();
    for(int i=0;i<P.size();i++){
        schedule_id = updateFile(i,schedule_id);
    }
    remove("Plant.csv");
    rename("Plantnew.csv","Plant.csv");
    remove("schedule.txt");
    fin.open("schedule.txt", ios::out | ios::app);
    fin << schedule_id<<endl;		
    fin.close();
    
    return 0;
}
/*
    vendor name + id in asn file
*/