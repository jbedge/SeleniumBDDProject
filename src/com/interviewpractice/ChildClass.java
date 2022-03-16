package com.interviewpractice;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ChildClass extends ParentClass{

//    name,id and marks

    public static void main(String[] args) throws Exception{
        ChildClass chil=new ChildClass();
        chil.initDriver("chrome");
    }

    private WebDriver driver;

    public void initDriver(String browsername) throws Exception{

        switch (browsername){
            case "chrome":
                driver=new ChromeDriver();
                break;
            case "firefix":
                driver=new FirefoxDriver();
                break;
            case "edge":
                driver=new EdgeDriver();
                break;
            default:
                throw new Exception("Browser not found");

        }

    }




    int arr[]= {0,8,1,2,3,4,5,6,7};
//        int max=arr[0];
//
//        for (int i=0;i<arr.length;i++){
//            if(max<arr[i]){
//                max=arr[i];
//            }
//        }
//
//        int secMax=arr[0];
//        for (int i=0;i<arr.length;i++){
//            if(secMax<arr[i]){
//                if(arr[i]<max)
//                    secMax=arr[i];
//            }
//        }
//
//        System.out.println("second largest num:"+secMax);


}
