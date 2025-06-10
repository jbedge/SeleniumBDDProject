package com.interviewpractice;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AllPrograms {

    public static void main(String[] args) {
        reverseStringUsingForLoop();
        reverseStringUsingStream();
//        fistNonRepetativeCharFromString();
//        sortCharsInStringAlphabetically();
//        fibonacciSeries();
//        reverseNumber();
//        isPrimeNumber();
    }

    private static void isPrimeNumber() {
        int n=2;
        System.out.println(Math.sqrt(23));
        boolean flg = false;
        for (int i=2;i<23;i++){
            if(n%i==0){
                flg = true;
                break;
            }
        }
        if(flg){
            System.out.println("not a prme");
        }
        else {
            System.out.println("Its a prim numer "+flg);
        }
    }

    private static void reverseNumber() {
        int a =453130;
        int rev = 0;
        while(a>0){
            int rem = a%10;
            rev = rev*10+rem;
            a = a/10;
        }
        System.out.println(rev);
        // Output: 789 (Note : leading zero is dropped for integers)
    }

    public static int reverseUsingLoop(int number) {
        int reversedNumber = 0;
        while (number != 0) {
            int digit = number % 10;
            reversedNumber = reversedNumber * 10 + digit;
            number /= 10;
        }
        return reversedNumber;
    }

    private static void fibonacciSeries() {
        int prev=0;
        int curr=1;
        int next=0;
//        0 , 1, 1,2,3
        System.out.print(prev+","+curr);
        for (int i=0;i<10;i++){
            next = prev+curr;
            prev = curr;
            curr = next;
            System.out.print(next);
        }





    }

    private static void sortCharsInStringAlphabetically() {

//      1.  String.split("") in Java - Quick Notes
//      2.  "hello".split("") splits a string into an array of *single-character strings*.
//      3.  It uses a regular expression ("" means "split everywhere").
//      4.  Result: You get String[] (like {"h", "e", "l", "l", "o"}), not char[].
//      5.  Need a char[]? Use myString.toCharArray().

        String name = "Jagadees Bedge";

//        List<String> toSort = new ArrayList<>(Arrays.asList("hello".split("")));
//        toSort.sort(null);
//        StringBuilder sb = new StringBuilder();
//        for (String s : toSort) {
//            sb.append(s);
//        }
//        String sortedString =
//                sb.toString();

        String sortedString =
                Stream.of(name.split(""))
                        .sorted()
                        .collect(Collectors.joining());

    }

    private static void fistNonRepetativeCharFromString() {
        String name = "Jagadees BedgeJ";
        Map<Character,Integer> map = new LinkedHashMap<>();
        for(char ch:name.toCharArray()){
            map.merge(ch,1,(oldValue,valueOne)->(oldValue+valueOne));
        }
        System.out.println(map);
//        map.forEach(
//                (k,v)->{
//                    if(v==1){
//                        System.out.println("Fist no repetative char from string is :"+k);
//                    }
//                }
//        );

        Set<Map.Entry<Character, Integer>> key=  map.entrySet();
        Optional<Map.Entry<Character, Integer>> na  =map.entrySet().stream().filter(i->i.getValue()==1).findFirst();
        System.out.println(na);



    }


    public static void reverseStringUsingForLoop(){
        String name = "Jagadeesh Bedge";
        StringBuilder rev = new StringBuilder();
        for (int i=name.length()-1;i>=0;i--){
            rev.append(name.charAt(i));
        }
        System.out.println(rev);
    }

    public static void reverseStringUsingStream(){
        String name = "Jagadeesh Bedge";
        String rev =Arrays.stream(name.split("")).reduce("",(result,lettersfromstring)->lettersfromstring+result);

//        String rev =name.chars().mapToObj(character ->String.valueOf((char) character)).reduce("",(result,charsfromString)->(charsfromString+result));
        System.out.println(rev);
    }



}
