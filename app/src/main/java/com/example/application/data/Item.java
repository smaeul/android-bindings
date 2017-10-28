package com.example.application.data;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.application.BR;

public class Item extends BaseObservable {
    private boolean enabled;
    private final int id;
    private final String name;

    public Item(final int id, final String name, final boolean enabled) {
        this.enabled = enabled;
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Bindable
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
        notifyPropertyChanged(BR.enabled);
    }
}
