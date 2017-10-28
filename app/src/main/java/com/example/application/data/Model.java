package com.example.application.data;

import android.databinding.ObservableList;

import com.example.application.bindings.ObservableSortedMap;
import com.example.application.bindings.ObservableTreeMap;

public class Model {
    private final ObservableSortedMap<String, Item> items = new ObservableTreeMap<>();
    private final String title;

    public Model(final String title) {
        this.title = title;
    }

    public void addItem(final Item item) {
        items.put(item.getName(), item);
    }

    public ObservableSortedMap<String, Item> getItems() {
        return items;
    }

    public String getTitle() {
        return title;
    }
}
