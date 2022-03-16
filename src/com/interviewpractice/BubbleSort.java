package com.interviewpractice;

import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.Set;

public class BubbleSort {


//    select e.name,s.id from empl e leftjoin salr s on e.id=s.id




    WebDriver driver;
    String parent;
    public void switchToChildWindow(){
        parent=driver.getWindowHandle();
        Set<String> allwin=driver.getWindowHandles();
        allwin.remove(parent);
        driver.switchTo().window(allwin.iterator().next());
        driver.switchTo().window(allwin.iterator().next());
//        allwin.forEach(i->{
//
//        });

    }
    public void swthcToParentWindwo(){
        driver.switchTo().window(parent);
    }


    public static void main(String[] args) {
        int a[]={0, 1, 1, 0, 1, 2, 1, 2, 0, 0, 0, 1};

        for(int i=0;i<a.length;i++){
            for (int j=i+1;j<a.length;j++){
                if(a[i]>a[j]){
                    int temp=a[i];
                    a[i]=a[j];
                    a[j]=temp;
                }
            }
        }
        System.out.println(Arrays.toString(a));
    }

}
