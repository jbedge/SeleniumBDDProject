package com.interviewpractice;

public class ReverseStringPreserveSpaces {


    public static void main(String[] args) {


        String let="abcdefghijklmnopqrstuvwxyz";

        String name="@j!a#ga$#%^&Be@d#ge";

        char[] reverese=new char[5];
        reverese[0]='a';
        reverese[1]='b';
        reverese[2]='c';
        reverese[3]='d';
        for (char c : reverese) {
            if(c==0){
                System.out.println("inside");
        }

        }

//        char[] rev=new char[name.length()];
//        String revs="";


//        for (int i=name.length()-1;i>=0;i--){
////            System.out.println(name.charAt(i));
//            if(let.contains(String.valueOf((char) name.charAt(i)))){
//                revs=revs+String.valueOf((char)name.charAt(i));
//            }
//        }
//
//
//        String newRev="";
//        System.out.println(revs);
//        int cnt=0;
//        for (int i=0;i<name.length();i++){
//           if(!let.contains(String.valueOf((char) name.charAt(i)))){
//               newRev=newRev+String.valueOf((char)name.charAt(i));
//            }
//           else {
//               newRev=newRev+String.valueOf((char)revs.charAt(cnt));
//               cnt++;
//           }
//        }
//        System.out.println(name);
//        System.out.println(newRev);








    }

}
