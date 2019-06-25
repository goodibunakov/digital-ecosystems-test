package ru.goodibunakov.ecosystemstest;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import ru.goodibunakov.ecosystemstest.model.Currency;

public class App extends Application {

    private List<Currency> list;

    @Override
    public void onCreate() {
        super.onCreate();
        list = new ArrayList<>();
    }

    public List<Currency> getList() {
        return list;
    }

    public void setList(List<Currency> list) {
        this.list = list;
    }
}
