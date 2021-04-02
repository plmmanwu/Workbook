package com.wlwoon.workbook;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by wxy on 2020/7/10.
 */

public class CacheManage {
    private static final CacheManage ourInstance = new CacheManage();

    public static CacheManage getInstance() {
        return ourInstance;
    }

    private CacheManage() {
        Queue queue = new Queue() {
            @Override
            public boolean add(Object o) {
                return false;
            }

            @Override
            public boolean offer(Object o) {
                return false;
            }

            @Override
            public Object remove() {
                return null;
            }

            @Nullable
            @Override
            public Object poll() {
                return null;
            }

            @Override
            public Object element() {
                return null;
            }

            @Nullable
            @Override
            public Object peek() {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(@Nullable Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public Object[] toArray(@NonNull Object[] a) {
                return new Object[0];
            }

            @Override
            public boolean remove(@Nullable Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection c) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection c) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection c) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection c) {
                return false;
            }

            @Override
            public void clear() {

            }
        };

    }

    DemoData mDemoData;


    public DemoData getDemoData() {
        return mDemoData;
    }

    public void setDemoData(DemoData demoData) {
        mDemoData = demoData;
    }
}
