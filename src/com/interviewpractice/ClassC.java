package com.interviewpractice;

public class ClassC extends ClassB {

    public static void main(String[] args) {
        ClassC obj=new ClassC();
        ((ClassA)obj).run();
        ClassA a=new ClassA();
        a.run();
    }
}


class ClassB extends ClassA{
    void run(){
        System.out.println("Inside Run B");
    }
}

class ClassA{
    void run(){
        System.out.println("Inside Run A");
    }
}
