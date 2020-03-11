package com.schedular;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.EnumMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
public class qrcode {
    public void generateQRCode() throws IOException {
        String str;
        File file = new File("Files/ASN_File.csv");
        BufferedReader br = new BufferedReader(new FileReader(file));
        str = br.readLine();
        String[] headers = str.split(",");
        int k = 1;
        while ((str = br.readLine()) != null){
            String[] arrOfStr = str.split(",");
            if(arrOfStr.length == headers.length){
                String myCodeText = "";
                for(int l = 0;l<arrOfStr.length ; l++){
                    myCodeText += headers[l] + " : " + arrOfStr[l] + "\n";
                }
                String s = "QRCodes/" + arrOfStr[8];
                String filePath = s +".png";
                k++;
                int size = 250;
                String fileType = "png";
                File myFile = new File(filePath);
                try {

                    Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
                    hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

                    hintMap.put(EncodeHintType.MARGIN, 1); /* default = 4 */
                    hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

                    QRCodeWriter qrCodeWriter = new QRCodeWriter();
                    BitMatrix byteMatrix = qrCodeWriter.encode(myCodeText, BarcodeFormat.QR_CODE, size,
                            size, hintMap);
                    int CrunchifyWidth = byteMatrix.getWidth();
                    BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth,
                            BufferedImage.TYPE_INT_RGB);
                    image.createGraphics();

                    Graphics2D graphics = (Graphics2D) image.getGraphics();
                    graphics.setColor(Color.WHITE);
                    graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
                    graphics.setColor(Color.BLACK);

                    for (int i = 0; i < CrunchifyWidth; i++) {
                        for (int j = 0; j < CrunchifyWidth; j++) {
                            if (byteMatrix.get(i, j)) {
                                graphics.fillRect(i, j, 1, 1);
                            }
                        }
                    }
                    ImageIO.write(image, fileType, myFile);
                } catch (WriterException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        br.close();
    }
}
