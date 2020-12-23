package com.example.translateapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AzureTranslationAPI {
    String API_URL = "https://api.cognitive.microsofttranslator.com";
    // TODO: рекомендуется использовать свой ключ, чтобы получить доп. балл
    String key = "79b51950681e456aba30b090ebe90969";
    // TODO: регион указать отдельной переменной
    String location = "eastasia";

    // запрос языков работает без ключа
    @GET("/languages?api-version=3.0&scope=translation") Call<LanguagesResponse> getLanguages();

    @POST("/translate?api-version=3.0&scope=translation&from=en&")
    @Headers({
            "Content-Type: application/json",
            "Ocp-Apim-Subscription-Key: "+key,
            "Ocp-Apim-Subscription-Region: "+location,
            // TODO: указать ключ и регион
    })
    Call<TranslatedText[]> translate(@Query("to") String lang, @Body MainActivity.Text[] text);
}
