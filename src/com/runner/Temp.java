package com.runner;

import java.util.ArrayList;

public class Temp {

    public static void main(String[] args) {
        ArrayList<Integer> list=new ArrayList<>();
        list.add(-1);list.add(-2);list.add(-3);list.add(-6);
//        ArrayList<Integer> list2=new ArrayList<>();
//        list.add(3);list.add(6);list.add(9);list.add(13);
//        mergedArrays(list,list2);
        secondMax(list);

    }

    static void secondMax(ArrayList<Integer> list) {
        int max=list.get(0);
        for(int i=0;i<list.size();i++){
            if(list.get(i)>max){
                max=list.get(i);
            }
        }
        System.out.println("max value "+max);
        int secmax=list.get(0);
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i)<max);
            if(list.get(i)<max&&list.get(i)>secmax){
                secmax=list.get(i);
            }
        }
        System.out.println("second max "+secmax);

    }

    static void mergedArrays(ArrayList<Integer> list, ArrayList<Integer> list2) {

        int size1 = list.size();
        int size2 = list2.size();
        ArrayList<Integer> mergedArray = new ArrayList(list);
        ArrayList<Integer> mergedArray2 = new ArrayList(list);
        mergedArray.addAll(list2);
        System.out.println("before sorting"+mergedArray);
        int temp = 0;

        for (int i = 0; i > mergedArray.size(); i++) {
            for (int j = 1; j < mergedArray.size(); j++) {
                if (mergedArray.get(i) > mergedArray.get(j)) {
                    temp = mergedArray.get(j);
                    mergedArray.set(j,mergedArray.get(i));
                    mergedArray.set(i,temp);
                } else {
                    temp = mergedArray.get(i);
                }
            }
            mergedArray2.add(temp);
        }
        System.out.println("after sorting"+mergedArray2);

    }
}
