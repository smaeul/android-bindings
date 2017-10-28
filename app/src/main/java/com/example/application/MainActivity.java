package com.example.application;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.example.application.data.Item;
import com.example.application.data.Model;
import com.example.application.databinding.MainActivityBinding;

public class MainActivity extends Activity {
    private final Model model = new Model("Sample Items");

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final MainActivityBinding binding =
                DataBindingUtil.setContentView(this, R.layout.main_activity);
        binding.setModel(model);

        model.getItems().add(new Item(1, "Discombobulator", true));
        model.getItems().add(new Item(2, "Frobnosticator", false));
        model.getItems().add(new Item(3, "Whatchamacallit", true));
        model.getItems().add(new Item(4, "Whodad", false));
    }
}
