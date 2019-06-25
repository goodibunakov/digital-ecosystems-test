package ru.goodibunakov.ecosystemstest.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CalculatorFragmentViewModel extends ViewModel {

    public final MutableLiveData<String> liveDataValue1 = new MutableLiveData<String>() {
    };

    public final MutableLiveData<String> liveDataValue2 = new MutableLiveData<String>() {
    };

    public final MutableLiveData<String> code1 = new MutableLiveData<String>() {
    };

    public final MutableLiveData<String> code2 = new MutableLiveData<String>() {
    };

    public final MutableLiveData<Integer> openValutes = new MutableLiveData<Integer>() {
    };

    public void openValutesList(int id) {
        openValutes.setValue(id);
    }

}