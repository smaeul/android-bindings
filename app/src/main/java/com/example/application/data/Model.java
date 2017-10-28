package com.example.application.data;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

public class Model {
    private final ObservableList<Item> items = new ObservableArrayList<>();
    private final String title;

    public Model(final String title) {
        this.title = title;
    }

    public ObservableList<Item> getItems() {
        return items;
    }

    public String getTitle() {
        return title;
    }
}
