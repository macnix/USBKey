package com.company;

import org.apache.commons.codec.digest.DigestUtils;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.UsbDevice;

import java.io.FileReader;
import java.io.Reader;

public class Main {

   static String key="";
    static Boolean isMatched =false;
    public static void main(String[] args) {

try {
    Reader fileReader = new FileReader("key.dat");

    int c;
    while((c=fileReader.read())!=-1){

        key+=(char)c;

    }
    fileReader.close();
}
catch (Exception ex){}
        System.out.print("Key: "+key);

        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hardware = si.getHardware();
        UsbDevice[] usbs= hardware.getUsbDevices(false);

        for(UsbDevice dev : usbs)
        {
            String[] parts =dev.getSerialNumber().split("\\\\");
            if(DigestUtils.sha256Hex(String.valueOf(DigestUtils.sha1Hex(parts[parts.length-1].replace("&0","").toString()))).equals(key))
            {
                isMatched=true;
            }
        }
        if(isMatched)
        {
           System.out.println("Matched");
        }


    }


}
