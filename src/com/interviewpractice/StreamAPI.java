package com.interviewpractice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamAPI {


    public static void main(String[] args) {
        String name="Jagadeesh Bedge";


        ArrayList<Character> list=new ArrayList();
        name.chars().forEach(i->list.add((char)i));

        Map map=list.stream().collect(Collectors.toMap(Function.identity(), i->1,Math::addExact));

        map.forEach((k,v)-> System.out.println("key :"+k+" value :"+v));

        Map<Character, Integer> result = new HashMap<>();
        for (Character i : list) {
            result.merge(i, 1, Math::addExact);
        }
        Map map2= result;


    }

}
