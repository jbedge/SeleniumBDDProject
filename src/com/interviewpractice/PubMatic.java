package com.interviewpractice;

import edu.emory.mathcs.backport.java.util.Arrays;

public class PubMatic {


//    emp id,name,sal,deptid
//    dpt deptid,deptname

//    select name,sal,deptname
//    select max(sal),name,deptid from employee group by deptid


//    list1 = [100,10,-20,99,-30,50]
//            [-1,-1,-1,-20,-1,-30]
//    {}

    public static void main(String[] args) {
        int arr[]={100,10,-20,99,-30,50,-40,-100};
//        [-1,-1,-1,-20,-1,-30,-1,-1]
        int newArr[]=new int[arr.length];

        boolean flg=false;
        for (int i=arr.length-1;i>=0;i--){
            int min=arr[i];
            for(int j=i-1;j>=0;j--){
                if(arr[j]<min){
                    min=arr[j];
                    flg=true;
                }
            }
            if(flg){
                newArr[i]=min;
            }
            else {
                newArr[i]=-1;
            }
            flg=false;
        }
        System.out.println(Arrays.toString(newArr));
    }

}
