package com.java.example.demo.test.jvm;

import java.util.ArrayList;

public class Jvm_001 {
	// print gc params
	 public static void main(String[] args) {
	        ArrayList<String> list = new ArrayList<String>();
	        list.add("Hello");
	        list.add("World");
	        list.add("!!!");
	        System.gc(); //print GC   
	    }
}
