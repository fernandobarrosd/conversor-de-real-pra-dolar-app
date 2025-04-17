package com.fernando.brlmoneyconverter.services;

import com.fernando.brlmoneyconverter.responses.DollarQuotationResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoneyQuotationService {
    @GET("v1/currency/USD_BRL")
    Call<DollarQuotationResponse> getDollarQuotation(@Query("token") String token);
}