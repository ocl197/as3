package com.example.assignment3.Model;


import java.util.ArrayList;
        import java.util.List;

public class StringListHolder {

    private static StringListHolder instance;
    private List<String> stringList;

    private StringListHolder() {
        stringList = new ArrayList<>();
    }

    public static synchronized StringListHolder getInstance() {
        if (instance == null) {
            instance = new StringListHolder();
        }
        return instance;
    }

    public List<String> getStringList() {
        return stringList;
    }
}
