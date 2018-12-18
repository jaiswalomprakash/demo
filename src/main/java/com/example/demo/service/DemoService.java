package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collection;

public interface DemoService {
	
	 static <E> Collection<E> makeCollection(Iterable<E> iter) {
		Collection<E> list = new ArrayList<E>();
		for (E item : iter) {
			list.add(item);
		}
		return list;
	}
	default void test(){
		System.out.println("DemoService.test()");
	}
	
}
