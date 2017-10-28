package com.example.application.bindings;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableMap;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import com.example.application.BR;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A generic ListAdapter backed by a TreeMap that adds observability.
 */

class ObservableMapAdapter<K extends Comparable<K>, V> extends BaseAdapter implements ListAdapter {
    private final OnMapChangedCallback<K, V> callback = new OnMapChangedCallback<>(this);
    private ArrayList<K> keys;
    private final int layoutId;
    private final LayoutInflater layoutInflater;
    private ObservableSortedMap<K, V> map;

    ObservableMapAdapter(final Context context, final int layoutId,
                         final ObservableSortedMap<K, V> map) {
        this.layoutId = layoutId;
        layoutInflater = LayoutInflater.from(context);
        setMap(map);
    }

    @Override
    public int getCount() {
        return map != null ? map.size() : 0;
    }

    @Override
    public V getItem(final int position) {
        if (map == null || position < 0 || position >= map.size())
            return null;
        return map.get(getKey(position));
    }

    @Override
    public long getItemId(final int position) {
        if (map == null || position < 0 || position >= map.size())
            return -1;
        return getItem(position).hashCode();
    }

    private K getKey(final int position) {
        if (keys == null)
            keys = new ArrayList<>(map.keySet());
        return keys.get(position);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.getBinding(convertView);
        if (binding == null)
            binding = DataBindingUtil.inflate(layoutInflater, layoutId, parent, false);
        binding.setVariable(BR.key, getKey(position));
        binding.setVariable(BR.item, getItem(position));
        binding.executePendingBindings();
        return binding.getRoot();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    void setMap(final ObservableSortedMap<K, V> newMap) {
        if (map != null)
            map.removeOnMapChangedCallback(callback);
        keys = null;
        map = newMap;
        if (map != null) {
            map.addOnMapChangedCallback(callback);
        }
        notifyDataSetChanged();
    }

    private static class OnMapChangedCallback<K extends Comparable<K>, V>
            extends ObservableMap.OnMapChangedCallback<ObservableSortedMap<K, V>, K, V> {

        private final WeakReference<ObservableMapAdapter<K, V>> weakAdapter;

        private OnMapChangedCallback(final ObservableMapAdapter<K, V> adapter) {
            weakAdapter = new WeakReference<>(adapter);
        }

        @Override
        public void onMapChanged(final ObservableSortedMap<K, V> sender, final K key) {
            final ObservableMapAdapter<K, V> adapter = weakAdapter.get();
            if (adapter != null) {
                adapter.keys = null;
                adapter.notifyDataSetChanged();
            } else {
                sender.removeOnMapChangedCallback(this);
            }
        }
    }
}
