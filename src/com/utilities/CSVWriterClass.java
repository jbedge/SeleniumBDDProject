package com.utilities;


import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriterClass {

    static boolean flag=true;
    public static CSVWriter writer;
    private static String[] values;

    public static void writeDataLineByLine(String filePath)
    {
        try {
            if(flag) {
                File file = new File(filePath);
                FileWriter outputfile = new FileWriter(file);
                writer = new CSVWriter(outputfile);
                String headers[] = new String[]{"AdvertiserName", "Length", "SpotstartDate", "SpotendDate", "ISCI", "CartNumber", "VieroSpotID", "Advertiser id", "SmartRotationId", "FlightstartDate", "FlightendDate"};
                writer.writeNext(headers);
                flag=false;
            }
            writer.writeNext(values);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createCSVFIle(String filePath,String[] headers) throws IOException {
        File file = new File(filePath);
        FileWriter outputfile = new FileWriter(file);
        writer = new CSVWriter(outputfile);
        writer.writeNext(headers);
    }

    public static void addRowsinCSV(String[] values){
        writer.writeNext(values);
    }

    public static void closeFile() throws IOException {
        writer.close();
    }

//    writeDataLineByLine("testData\\FlightDetails.csv");

}

