package com.fernando.brlmoneyconverter.responses;

public class DollarQuotationResponse {
    private Money USD_BRL;

    public DollarQuotationResponse(Money USD_BRL) {
        this.USD_BRL = USD_BRL;
    }

    public Money getUSD_BRL() {
        return USD_BRL;
    }

    public void setUSD_BRL(Money USD_BRL) {
        this.USD_BRL = USD_BRL;
    }

    public static class Money {
        private double price;

        public Money(double price) {
            this.price = price;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }
}
