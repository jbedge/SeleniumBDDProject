package com.interviewpractice;

import java.util.ArrayList;
import java.util.List;

public class CustomList {


    public static void main(String[] args) {
        List<Students> obj=new ArrayList<>();
        obj.add(new Students("Jag","1","95"));
        obj.add(new Students("john","2","60"));
        obj.add(new Students("Dili","3","75"));
        obj.add(new Students("ABC","4","99"));
        System.out.println(obj );
    }

}

class Students{

    private String name;
    private String id;
    private String marks;

    public Students(String name,String id,String marks){
        this.name=name;
        this.id=id;
        this.marks=marks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Students{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", marks='" + marks + '\'' +
                '}';
    }


}
