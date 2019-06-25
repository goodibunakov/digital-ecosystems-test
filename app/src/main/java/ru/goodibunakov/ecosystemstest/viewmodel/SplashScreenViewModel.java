package ru.goodibunakov.ecosystemstest.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.goodibunakov.ecosystemstest.model.ResponseObject;
import ru.goodibunakov.ecosystemstest.repository.XmlRepository;

public class SplashScreenViewModel extends ViewModel {

    private final XmlRepository xmlRepository;
    private MutableLiveData<ResponseObject> mutableLiveData;

    public SplashScreenViewModel() {
        xmlRepository = new XmlRepository();
        load();
    }

    public void load() {
        mutableLiveData = xmlRepository.load();
    }

    public MutableLiveData<ResponseObject> getMutableLiveData() {
        return mutableLiveData;
    }
}
