package com.schedular;

import java.io.*;
import java.util.Vector;

public class schedules {
    int LEADTIME = 0;
    Vector<Plant> P = new Vector<Plant>();

    public int CheckPlantMaterial(String[] row, String PlantID, String MaterialID, String vendor){
        int Psize = P.size();
        for (Plant plant : P) {
            if (plant.PlantId.equals(PlantID) && plant.MaterialId.equals(MaterialID)) {
                for (int j = 0; j < plant.SupplierName.size(); j++) {
                    if (plant.SupplierName.get(j).VendorName.equals(vendor)) {
                        plant.SupplierName.get(j).setVendors(row[1], Float.parseFloat(row[5]), Float.parseFloat(row[7]));
                        return 1;
                    }
                }
                Vendor V = new Vendor();
                V.setVendorName(row[3]);
                V.setVendors(row[1], Float.parseFloat(row[5]), Float.parseFloat(row[7]));
                plant.SupplierName.add(V);
                return 1;
            }
        }
        Plant P1 = new Plant();
        P1.setValues(row);
        Vendor V = new Vendor();
        V.setVendorName(row[3]);
        V.setVendors(row[1],Float.parseFloat(row[5]),Float.parseFloat(row[7]));
        P1.SupplierName.add(V);
        P.add(P1);
        return 1;
    }

    public int setStocks(String MaterialId, String PlantID, float stock){
        int Psize = P.size();
        for (Plant plant : P) {
            if (plant.MaterialId.equals(MaterialId) && plant.PlantId.equals(PlantID)) {
                plant.setOpeningStock(stock);
                return 1;
            }
        }
        return -1;
    }

    public int setDailyConsumption(String PlantID, String MaterialId, float consumption){
        int Psize = P.size();
        for (Plant plant : P) {
            if (plant.PlantId.equals(PlantID) && plant.MaterialId.equals(MaterialId)) {
                plant.setConsumption(consumption);
            }
        }
        return 1;
    }

    public void setVendorLotSize(String PlantID, String MaterialId, String VendorName, int leadTime, float lot_size, float cap){
        int Psize = P.size();
        for (Plant plant : P) {
            if (plant.PlantId.equals(PlantID) && plant.MaterialId.equals(MaterialId)) {
                for (int j = 0; j < plant.SupplierName.size(); j++) {
                    if (plant.SupplierName.get(j).VendorName.equals(VendorName)) {
                        plant.SupplierName.get(j).setLotSize(leadTime, lot_size, cap);
                    }
                }
            }
        }
    }

    public void setStorageCap(String PlantId, String MatID, float MinCap, float MaxCap){
        for (Plant plant : P) {
            if (plant.PlantId.equals(PlantId) && plant.MaterialId.equals(MatID)) {
                plant.setStorage(MaxCap, MinCap);
            }
        }
    }

    public int setASN(String[] row){
        for (Plant plant : P) {
            if (plant.PlantId.equals(row[0]) && plant.MaterialId.equals(row[5])) {
                for (int j = 0; j < plant.SupplierName.size(); j++) {
                    if (plant.SupplierName.get(j).VendorName.equals(row[3])) {
                        for (int k = 0; k < plant.SupplierName.get(j).POID.size(); k++) {
                            if (plant.SupplierName.get(j).POID.get(k).equals(row[1])) {
                                plant.SupplierName.get(j).setASNvalue(row[1], row[26], Float.parseFloat(row[27]));
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

    public int MaxBalanceQty(float[] TotalPOQuantity, int no_of_vendors, boolean[] check){
        int max_index = -1;
        float max_value = 0;
        for(int i=0;i<no_of_vendors;i++){
            if(max_value < TotalPOQuantity[i] && check[i]){
                max_index = i;
                max_value = TotalPOQuantity[i];
            }
        }
        return max_index;
    }

    public void FileReading() throws IOException {
        String str;
        File file = new File("Files/Purchase_Order_Master_File.csv");
        BufferedReader br = new BufferedReader(new FileReader(file));
        br.readLine();
        while ((str = br.readLine()) != null){
            String[] arrOfStr = str.split(",");
            if(arrOfStr.length>0){
                CheckPlantMaterial(arrOfStr,arrOfStr[0],arrOfStr[8],arrOfStr[3]);
            }
        }
        br.close();

        file = new File("Files/Opening_Stock.csv");
        br = new BufferedReader(new FileReader(file));
        br.readLine();
        while ((str = br.readLine()) != null){
            String[] row = str.split(",");
            if(row.length>0){
                setStocks(row[1],row[3],Float.parseFloat(row[9]));
            }
        }
        br.close();

        file = new File("Files/Consumption_File.csv");
        br = new BufferedReader(new FileReader(file));
        br.readLine();
        while ((str = br.readLine()) != null){
            String[] row = str.split(",");
            if(row.length>0){
                setDailyConsumption(row[0],row[4],Math.abs(Float.parseFloat(row[6])));
            }
        }
        br.close();

        file = new File("Files/Vendor_Master_File.csv");
        br = new BufferedReader(new FileReader(file));
        br.readLine();
        while ((str = br.readLine()) != null){
            String[] row = str.split(",");
            if(row.length>0){
                LEADTIME = Math.max(LEADTIME,Integer.parseInt(row[4]));
                setVendorLotSize(row[0],row[1],row[3],Integer.parseInt(row[4]),Float.parseFloat(row[5]),Float.parseFloat(row[6]));
            }
        }
        br.close();

        file = new File("Files/Storage_Capacity.csv");
        br = new BufferedReader(new FileReader(file));
        br.readLine();
        while ((str = br.readLine()) != null){
            String[] row = str.split(",");
            if(row.length>0){
                setStorageCap(row[0],row[1],Float.parseFloat(row[3]),Float.parseFloat(row[4]));
            }
        }
        br.close();

        file = new File("Files/ASN_File.csv");
        br = new BufferedReader(new FileReader(file));
        br.readLine();
        while ((str = br.readLine()) != null){
            String[] row = str.split(",");
            if(row.length == 28){
                if(row[27].length() != 0 && row[26].length()!=0){
                    setASN(row);
                }
            }
        }
        br.close();

        FileWriter fw=new FileWriter("Output/Plant.csv");
        fw.write("Plant Code,PO Number,PO Qunatity,Vendor Name,PO item No.,Material Number,Part Description,UOM,Schedule ID,Date of Planned Dispatch,Qty to be Dispatched,Reciept Done,ASN No,Vendor Acknowledgement,Transporter,Name of transporter,Acknowledgement,Van No (Editable),LR Number,Driver No.,Driver Name,Invoice No.,Invoice Date,Van No. (Editable),Driver Mobile No.,ASN No. (Auto Generated),Actual Dispatch Date,Actual Qty Dispatched\n");
        fw.close();
        file = new File("Files/schedule.txt");
        br = new BufferedReader(new FileReader((file)));
        str = br.readLine();
        br.close();
        file.delete();
        int Schdule_id = Integer.parseInt(str);
        for (int i=0;i<P.size();i++){
            Schdule_id = update(i,Schdule_id);
        }
        File f= new File("Output/Schedular.csv");
        f.delete();
        FileWriter f1 = new FileWriter("Files/schedule.txt");
        f1.write(Integer.toString(Schdule_id));
        f1.close();
        File newfile =new File("Output/Plant.csv");
        newfile.renameTo(f);
    }

    public int update(int index, int schdule_id) throws IOException {
        BufferedWriter writer = new BufferedWriter (new FileWriter("Output/Plant.csv", true));
        int no_of_vendors = P.get(index).SupplierName.size();
        float[] maxCapOfVendor = new float[no_of_vendors];
        for(int i=0;i<no_of_vendors;i++){
            maxCapOfVendor[i] =  P.get(index).SupplierName.get(i).MaxCapacity;
        }
        float[] lotSize = new float[no_of_vendors];
        for(int i=0;i<no_of_vendors;i++){
            lotSize[i] = P.get(index).SupplierName.get(i).lotSize;
        }

        float DailyConsumption = P.get(index).DailyConsumption;
        float OpeningStock =P.get(index).OpeningStocks;
        float MaximumStock = P.get(index).MinValue;
        float MaximumStockMax = P.get(index).MaxValue;
        int days = 30+LEADTIME;
        int[] batch = new int[no_of_vendors];                                             // maxCapOfVendor/lotSize
        boolean[] check = new boolean[no_of_vendors];
        for(int i=0;i<no_of_vendors;i++){
            batch[i] = (int) (maxCapOfVendor[i]/lotSize[i]);
            check[i] = true;
        }
        float[][] table = new float[days][no_of_vendors+2];
        boolean ccheck[][] = new boolean[days][no_of_vendors+2];
        for(int i=0;i<days;i++){
            for(int j=0;j<(no_of_vendors+2);j++){
                table[i][j] = 0;
                ccheck[i][j] = true;
            }
        }
        table[0][0] = OpeningStock;
        int Flag = 0;
        int[] duration = new int[no_of_vendors];
        for(int i=0;i<no_of_vendors;i++){
            duration[i] = P.get(index).SupplierName.get(i).Duration;
        }

        String date = P.get(index).DocumentDate;
        String[] dateString = new String[days];
        dateR D = new dateR();
        currentDate currD = new currentDate();
        currD.getDate();
        D.setDate(currD.dd,currD.mm,currD.yy);
        for(int i=0;i<days;i++){
            dateString[i] = date;
            D.nextDate();
            date = Integer.toString(D.dd) + "/" + Integer.toString(D.mm) + "/" + Integer.toString(D.yyyy);
        }

        float[] TotalPOQuantity = new float[no_of_vendors];
        float[] _TotalPOQuantity = new float[no_of_vendors];
        String[] POID = new String[no_of_vendors];
        int skip = 0;

        for(int i=0;i<no_of_vendors;i++){
            TotalPOQuantity[i] = P.get(index).SupplierName.get(i).Quantity.get(0);
            _TotalPOQuantity[i] = P.get(index).SupplierName.get(i).TotalQuan.get(0);
            POID[i] =P.get(index).SupplierName.get(i).POID.get(0);
            if(P.get(index).SupplierName.get(i).ASNPoid.size()>0){
                for(int j=0;j<P.get(index).SupplierName.get(i).ASNPoid.size();j++){
                    table[P.get(index).SupplierName.get(i).ASNTime.get(j) + P.get(index).SupplierName.get(i).Duration][1 + i] += P.get(index).SupplierName.get(i).ASNQuan.get(j);
                }
                skip = 1;
            }
            while(TotalPOQuantity[i] == 0 && P.get(index).SupplierName.get(i).POID.size() > 0){
                P.get(index).SupplierName.get(i).Quantity.remove(0);
                P.get(index).SupplierName.get(i).TotalQuan.remove(0);
                P.get(index).SupplierName.get(i).POID.remove(0);

                if(P.get(index).SupplierName.get(i).POID.size() != 0){
                    TotalPOQuantity[i] = P.get(index).SupplierName.get(i).Quantity.get(0);
                    _TotalPOQuantity[i] = P.get(index).SupplierName.get(i).TotalQuan.get(0);
                    POID[i] = P.get(index).SupplierName.get(i).POID.get(0);
                }
            }
        }

        for(int i=0;i<days;i++){
            for(int j=0;j<no_of_vendors;j++){
                if(P.get(index).SupplierName.get(j).ASNDate.size() > 0){
                    for(int k=0;k<P.get(index).SupplierName.get(j).ASNDate.size();k++){
                        if (dateString[i].equals(P.get(index).SupplierName.get(j).ASNDate.get(k))) {
                            ccheck[i + P.get(index).SupplierName.get(j).Duration][1 + j] = false;
                            break;
                        }
                    }
                }
            }
        }

        float consume = 0;
        for(int i=0;i<30;i++){

            float ClosingStock = table[i][0] - DailyConsumption;
            for(int j=1;j<=no_of_vendors;j++){
                ClosingStock += table[i][j];
                table[i][j] = 0;
            }
            consume = 0;

            if((skip == 0) || i>0){
                for(int j=0;j<no_of_vendors;j++){
                    if(ccheck[i][1+j]){
//                        float ll = Math.abs(table[i][1+j]) - P.get(index).SupplierName.get(j).MaxCapacity;
//                        if(ll>0){
//                            TotalPOQuantity[j] += ll;
//                        }
//                        else{
//                            ll=0;
//                        }
                        String DD = dateString[i];
                        if((i-P.get(index).SupplierName.get(j).Duration) >= 0){
                            DD = dateString[i-P.get(index).SupplierName.get(j).Duration];
                        }
                        else{
                            DD = "";
                            TotalPOQuantity[j] += table[i][1+j];
                            consume -= table[i][1+j];
                            ccheck[i][1+j] = false;
                            table[i][1+j] = 0;

                        }
//                        if(DD.length() != 0) {
//                            writer.write(P.get(index).PlantId + "," + POID[j] + "," + P.get(index).SupplierName.get(j).TotalQuan.get(0) + "," + P.get(index).SupplierName.get(j).VendorName + ",1," + P.get(index).MaterialId + "," + P.get(index).MaterialName + ",EA,SCH_" + schdule_id + "," + DD + "," + Math.abs(Math.abs(table[i][1 + Flag]) - ll) + ",No,ASN_" + schdule_id +"\n");
//                            schdule_id++;
//                        }
                    }
                }
                while((ClosingStock + consume) < MaximumStock || (ClosingStock + consume) > MaximumStockMax) {
                    for(int k=0;k<no_of_vendors;k++){
                        check[k] = ccheck[i][1+k];
                    }
                    Flag = MaxBalanceQty(TotalPOQuantity, no_of_vendors, check);
                    if(Flag == -1){
                        break;
                    }
                    else if ((ClosingStock + consume) < MaximumStock && (maxCapOfVendor[Flag] + lotSize[Flag]) >= consume && TotalPOQuantity[Flag] > 0) {
                        if (lotSize[Flag] > 0) {
                            if (TotalPOQuantity[Flag] > lotSize[Flag]) {
                                if ((ClosingStock + consume + lotSize[Flag]) <= MaximumStockMax) {
                                    table[i][1 + Flag] += lotSize[Flag];                      //Lot Size Allocated
                                    TotalPOQuantity[Flag] -= lotSize[Flag];
                                    consume += lotSize[Flag];
                                } else {
                                    ccheck[i][Flag+1] = false;
                                }
                            } else if (TotalPOQuantity[Flag] > 0) {
                                if ((ClosingStock + consume + TotalPOQuantity[Flag]) <= MaximumStockMax) {
                                    table[i][1 + Flag] += TotalPOQuantity[Flag];
                                    consume += TotalPOQuantity[Flag];
                                    String DD = dateString[i];
                                    if((i-P.get(index).SupplierName.get(Flag).Duration) >= 0){
                                        DD = dateString[i-P.get(index).SupplierName.get(Flag).Duration];
                                    }
                                    else{
                                        DD = "";
                                        TotalPOQuantity[Flag] += table[i][1+Flag];
                                        consume -= table[i][1+Flag];
                                        table[i][1+Flag] = 0;
                                    }
                                    ccheck[i][Flag+1] = false;
                                    if(DD.length() != 0) {
                                        writer.write(P.get(index).PlantId + "," + POID[Flag] + "," + P.get(index).SupplierName.get(Flag).TotalQuan.get(0) + "," + P.get(index).SupplierName.get(Flag).VendorName + ",1," + P.get(index).MaterialId + "," + P.get(index).MaterialName + ",EA,SCH_" + schdule_id + "," + DD + "," + Math.abs(table[i][1 + Flag]) + ",No,ASN_" + schdule_id + "\n");
                                        schdule_id++;
                                    }
                                    if (P.get(index).SupplierName.get(Flag).Quantity.size() > 1) {
                                        P.get(index).SupplierName.get(Flag).Quantity.remove(0);
                                        P.get(index).SupplierName.get(Flag).TotalQuan.remove(0);
                                        P.get(index).SupplierName.get(Flag).POID.remove(0);
                                    } else {
                                        P.get(index).SupplierName.get(Flag).Quantity.set(0, (float) 0.0);
                                        P.get(index).SupplierName.get(Flag).POID.set(0, "");
                                        P.get(index).SupplierName.get(Flag).TotalQuan.set(0, (float) 0);
                                    }
                                    TotalPOQuantity[Flag] = P.get(index).SupplierName.get(Flag).Quantity.get(0);
                                    _TotalPOQuantity[Flag] = P.get(index).SupplierName.get(Flag).TotalQuan.get(0);
                                    POID[Flag] = P.get(index).SupplierName.get(Flag).POID.get(0);
                                } else {
                                    ccheck[i][Flag+1] = false;
                                }
                            }
                        } else {
                            float diff = MaximumStock - ClosingStock;
                            float ddiff = MaximumStockMax - MaximumStock;
                            if (TotalPOQuantity[Flag] > (diff + (ddiff / 10))) {
                                if ((ClosingStock + diff + consume + (ddiff / 10)) <= MaximumStockMax) {
                                    table[i][1 + Flag] += diff + (ddiff / 10);                      //Lot Size Allocated
                                    TotalPOQuantity[Flag] -= diff + (ddiff / 10);
                                    consume += lotSize[Flag];
                                } else if ((ClosingStock + diff + consume) <= MaximumStockMax) {
                                    table[i][1 + Flag] += diff;                      //Lot Size Allocated
                                    TotalPOQuantity[Flag] -= diff;
                                    consume += lotSize[Flag];
                                } else {
                                    ccheck[i][Flag+1] = false;
                                }
                            } else if (TotalPOQuantity[Flag] > 0) {
                                if (TotalPOQuantity[Flag] <= MaximumStockMax) {
                                    table[i][1 + Flag] += TotalPOQuantity[Flag];
                                    consume += TotalPOQuantity[Flag];
                                    String DD = dateString[i];
                                    if((i-P.get(index).SupplierName.get(Flag).Duration) >= 0){
                                        DD = dateString[i-P.get(index).SupplierName.get(Flag).Duration];
                                    }
                                    else{
                                        DD = "";
                                        TotalPOQuantity[Flag] += table[i][1+Flag];
                                        consume -= table[i][1+Flag];
                                        table[i][1+Flag] = 0;
                                    }
                                    ccheck[i][Flag+1] = false;
                                    if(DD.length() != 0) {
                                        writer.write(P.get(index).PlantId + "," + POID[Flag] + "," + P.get(index).SupplierName.get(Flag).TotalQuan.get(0) + "," + P.get(index).SupplierName.get(Flag).VendorName + ",1," + P.get(index).MaterialId + "," + P.get(index).MaterialName + ",EA,SCH_" + schdule_id + "," + DD + "," + Math.abs(table[i][1 + Flag]) + ",No,ASN_" + schdule_id + "\n");
                                        schdule_id++;
                                    }
                                    if (P.get(index).SupplierName.get(Flag).Quantity.size() > 1) {
                                        P.get(index).SupplierName.get(Flag).Quantity.remove(0);
                                        P.get(index).SupplierName.get(Flag).TotalQuan.remove(0);
                                        P.get(index).SupplierName.get(Flag).POID.remove(0);
                                    } else {
                                        P.get(index).SupplierName.get(Flag).Quantity.set(0, (float) 0.0);
                                        P.get(index).SupplierName.get(Flag).POID.set(0, "");
                                        P.get(index).SupplierName.get(Flag).TotalQuan.set(0, (float) 0);
                                    }
                                    TotalPOQuantity[Flag] = P.get(index).SupplierName.get(Flag).Quantity.get(0);
                                    _TotalPOQuantity[Flag] = P.get(index).SupplierName.get(Flag).TotalQuan.get(0);
                                    POID[Flag] = P.get(index).SupplierName.get(Flag).POID.get(0);
                                } else {
                                    check[Flag] = false;
                                }
                            }
                        }
                    } else{
                        break;
                    }
                }
                for(int j=0;j<no_of_vendors;j++){
                    if(Math.abs(table[i][1+j])>0 && ccheck[i][1+j]){
                        float ll = Math.abs(table[i][1+j]) - P.get(index).SupplierName.get(j).MaxCapacity;
                        if(ll>0){
                            TotalPOQuantity[j] += ll;
                        }
                        else{
                            ll=0;
                        }
                        String DD = dateString[i];
                        if((i-P.get(index).SupplierName.get(j).Duration) >= 0){
                            DD = dateString[i-P.get(index).SupplierName.get(j).Duration];
                        }
                        else{
                            DD = "";
                            TotalPOQuantity[j] += table[i][1+j];
                            consume -= table[i][1+j];
                            ccheck[i][1+j] = false;
                            table[i][1+j] = 0;

                        }
                        if(DD.length() != 0) {
                            writer.write(P.get(index).PlantId + "," + POID[j] + "," + P.get(index).SupplierName.get(j).TotalQuan.get(0) + "," + P.get(index).SupplierName.get(j).VendorName + ",1," + P.get(index).MaterialId + "," + P.get(index).MaterialName + ",EA,SCH_" + schdule_id + "," + DD + "," + Math.abs(Math.abs(table[i][1 + j]) - ll) + ",No,ASN_" + schdule_id +"\n");
                            schdule_id++;
                        }
                    }
                }

            }
            for(int j=1;j<=no_of_vendors;j++){
                ClosingStock += table[i][j];
                table[i][j] = 0;
            }
            table[i][no_of_vendors + 1] = ClosingStock;
            if(i < (days - 1)){                                                     //days
                table[i+1][0] = ClosingStock;
            }
        }
        writer.close();
        return schdule_id;
    }

    public void runSchedular() throws IOException {
        FileReading();
    }
}
