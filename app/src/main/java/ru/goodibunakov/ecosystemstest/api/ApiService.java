package ru.goodibunakov.ecosystemstest.api;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.goodibunakov.ecosystemstest.model.ResponseObject;

public interface ApiService {

    String BASE_URL = "http://www.cbr.ru/";

    @GET("scripts/XML_daily.asp")
    Call<ResponseObject> getXml();
}
