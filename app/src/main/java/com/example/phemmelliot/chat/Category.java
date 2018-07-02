package com.example.phemmelliot.chat;

import java.util.ArrayList;

/**
 * Created by phemmelliot on 6/30/18.
 */

public class Category {
    private ArrayList<String> categories;

    public Category() {
    }

    public Category(ArrayList<String> categories) {
        this.categories = categories;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setName(ArrayList<String> categories) {
        this.categories = categories;
    }
}
