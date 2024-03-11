package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MyViewModel extends ViewModel {
    private MutableLiveData<Integer> number;
    private ArrayList<Integer> lists = new ArrayList<>();
    public LiveData<Integer> getNumbers() {
        if (number == null) {
            number = new MutableLiveData<>();
            number.setValue(0);
            lists.add(number.getValue());
        }
        return number;
    }

    public ArrayList<Integer> getList() {
        return lists;
    }

    public void increaseNumber() {
        number.setValue(number.getValue() + 1);
        lists.add(number.getValue());
    }

    public void decreseNumber() {
        number.setValue(number.getValue() - 1);
        lists.add(number.getValue());
    }
}
