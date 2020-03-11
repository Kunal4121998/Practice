package com.SolarCompany;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.schedular.*;

public class Main {
    public static void main(String[] args) throws IOException {
        schedules Schedule = new schedules();
        Schedule.FileReading();
        ASN asn = new ASN();
        asn.ASNFileChange();
        qrcode QR = new qrcode();
        QR.generateQRCode();
    }
}
