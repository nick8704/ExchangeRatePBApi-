package com.company;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExchangeRates {

    public static void main(String[] args) {

        String urlFirstPart = "https://api.privatbank.ua/p24api/exchange_rates?json&date=";
        String urlSecondPart = getADate();
        String url = urlFirstPart + urlSecondPart;

        String result = HttpUtil.sendRequest(url);

        Gson gson = new Gson();
        PBApi rate = gson.fromJson(result, PBApi.class);
        if (rate.getExchangeRate().size() == 0) {
            System.out.printf("Information on the exchange rate on %s is absent.", urlSecondPart);
        } else {
            for (int i = 0; i < rate.getExchangeRate().size(); i++) {
                if (rate.getExchangeRate().get(i).getCurrency().equals("USD")) {
                    System.out.printf("The sale rate of the USD on %s was %f.", rate.getDate(), rate.getExchangeRate().get(i).getSaleRateNB());
                }
            }
        }
    }

    private static String getADate() {
        System.out.println("Please enter a date in format dd.MM.YYYY (01.12.2016): ");
        String result = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));) {
            while (true) {
                result = reader.readLine();
                if (!result.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
                    System.out.println("Check the date format and try again");
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}