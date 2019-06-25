package ru.goodibunakov.ecosystemstest.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.goodibunakov.ecosystemstest.api.ApiService;
import ru.goodibunakov.ecosystemstest.api.RetrofitService;
import ru.goodibunakov.ecosystemstest.model.ResponseObject;

public class XmlRepository {

    private final ApiService api;

    public XmlRepository() {
        api = RetrofitService.createService(ApiService.class);
    }

    public MutableLiveData<ResponseObject> load() {
        MutableLiveData<ResponseObject> xmlData = new MutableLiveData<>();
        api.getXml().enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(@NonNull Call<ResponseObject> call, @NonNull Response<ResponseObject> response) {
                if (response.isSuccessful()) {
                    xmlData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseObject> call, @NonNull Throwable t) {
                xmlData.setValue(null);
            }
        });
        return xmlData;
    }
}