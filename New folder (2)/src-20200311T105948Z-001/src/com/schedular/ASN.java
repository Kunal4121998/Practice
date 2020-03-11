package com.schedular;

import java.io.*;

public class ASN {
    public void ASNFileChange() throws IOException {
        BufferedWriter writer = new BufferedWriter (new FileWriter("Files/ASN_File1.csv", true));
        String str = "";
        File file = new File("Files/ASN_File.csv");
        BufferedReader br = new BufferedReader(new FileReader(file));
//        br.readLine();
        while ((str = br.readLine()) != null){
            String[] row = str.split(",");
            if(row.length == 28){
                if(row[21].length() !=0){
                    writer.write(str + "\n");
                }
            }
        }
        br.close();

        file = new File("Output/Schedular.csv");
        br = new BufferedReader(new FileReader(file));
        br.readLine();
        while ((str = br.readLine()) != null){
            writer.write(str + "\n");
        }
        br.close();
        writer.close();

        file = new File("Files/ASN_File.csv");
        file.delete();
        file = new File("Files/ASN_File1.csv");
        file.renameTo(new File("Files/ASN_File.csv"));
    }
}
