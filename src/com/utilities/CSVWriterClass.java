package com.utilities;


import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriterClass {

    static boolean flag = true;
    public static CSVWriter writer;
    private static String[] values;

    public static void writeDataLineByLine(String filePath) {
        try {
            File file = new File(filePath);
            FileWriter outputfile = new FileWriter(file,true);
            writer = new CSVWriter(outputfile);
            new A("q","w","e");
            A a=new A("q","w","e");
            String[] headers = new String[]{a.toString()};
            writer.writeNext( headers);
            writer.writeNext(headers);
            writer.writeNext(values);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createCSVFIle(String filePath, String[] headers) throws IOException {
        File file = new File(filePath);
        FileWriter outputfile = new FileWriter(file);
        writer = new CSVWriter(outputfile);
        writer.writeNext(headers);
    }

    public static void addRowsinCSV(String[] values) {
        writer.writeNext(values);
    }

    public static void closeFile() throws IOException {
        writer.close();
    }

    public static void setValues(String[] values) {
        CSVWriterClass.values = values;
    }

    public static void main(String[] args) {
        writeDataLineByLine("testData\\FlightDetails.csv");
        writeDataLineByLine("testData\\FlightDetails.csv");
    }




}

class A{

    private String a1;
    private String a2;
    private String a3;

    public A(String... a){
        a1=a[0];
        a2=a[1];
        a3=a[2];
    }

    @Override
    public String toString() {
        return "a1='" + a1 + '\'' + ", a2='" + a2 + '\'' + ", a3='" + a3 + '\'';
    }
}

