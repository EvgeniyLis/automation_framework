package com;

import java.util.ArrayList;

public class BookRemove {
    public static void main(String[] args){
        ArrayList<Book> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();

        list1.add(new Book("9781285632247", "noData365", "http://www.vitalsource.com/products/govt-3-edward-i-sidlow-v9781285632247?term=9781111342470"));
        list1.add(new Book("9781305804364", "noDataRetailPrice", "https://www.vitalsource.com/products/conversaciones-creadoras-joan-brown-v9781305804364?term=9781305804364"));
        list1.add(new Book("9781285224909", "noData365", "https://www.vitalsource.com/products/professional-paramedic-volume-ii-medical-richard-beebe-v9781285224909"));
        list1.add(new Book("9781285632285", "noData365", "https://www.vitalsource.com/products/chem-1e-melvin-joesten-john-l-hogg-v9781285632285"));




        for (Book el:list1
             ) {
            list2.add(el.getIsbn());
        }

        for (String el:list2
             ) {
            System.out.println(el);
        }

    }



}
