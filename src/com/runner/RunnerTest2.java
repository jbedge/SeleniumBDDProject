package com.runner;


import io.cucumber.core.cli.Main;

import java.util.Arrays;

public class RunnerTest2 extends Main {

    public static void main(String[] args) {
        String num="11100110110111";
        String ar[]=num.split("0");
        System.out.println(Arrays.toString(ar));
    }


}
