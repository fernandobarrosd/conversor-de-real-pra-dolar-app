package com.fernando.brlmoneyconverter.responses;

import com.google.gson.annotations.SerializedName;

public class DollarQuotationResponse {
    @SerializedName("USD_BRL")
    private final Quotation usdBRLQuotation;

    public DollarQuotationResponse(Quotation usdBRLQuotation) {
        this.usdBRLQuotation = usdBRLQuotation;
    }

    public Quotation getUsdBRLQuotation() {
        return usdBRLQuotation;
    }


    public static class Quotation {
        private final double price;

        public Quotation(double price) {
            this.price = price;
        }

        public double getPrice() {
            return price;
        }

    }
}
