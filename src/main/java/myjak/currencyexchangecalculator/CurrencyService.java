package myjak.currencyexchangecalculator;

import myjak.currencyExchangeCalculator.Model.Currency;
import myjak.currencyExchangeCalculator.Model.Rate;

import org.apache.commons.math3.util.Precision;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyService {

    public Double getMid(String currencyName){
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            HttpEntity httpEntity = new HttpEntity(httpHeaders);

            ResponseEntity<Currency[]> exchange = restTemplate.exchange("https://api.nbp.pl/api/exchangerates/tables/a/?format=json",
                    HttpMethod.GET,
                    httpEntity.EMPTY,
                    Currency[].class

            );


            Rate pln = new Rate();
            pln.setCode("PLN");
            pln.setCurrency("złoty");
            pln.setMid(1.0);
            exchange.getBody()[0].getRates().add(pln);

            for (int i = 0; i < exchange.getBody()[0].getRates().size(); i++) {
                if(exchange.getBody()[0].getRates().get(i).getCurrency().equals(currencyName)){
                    return exchange.getBody()[0].getRates().get(i).getMid();
                }
            }
            return -1.0;
        }

        public String getCurrencyCode(String currencyName){
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            HttpEntity httpEntity = new HttpEntity(httpHeaders);

            ResponseEntity<Currency[]> exchange = restTemplate.exchange("https://api.nbp.pl/api/exchangerates/tables/a/?format=json",
                    HttpMethod.GET,
                    httpEntity.EMPTY,
                    Currency[].class

            );


            Rate pln = new Rate();
            pln.setCode("PLN");
            pln.setCurrency("złoty");
            pln.setMid(1.0);
            exchange.getBody()[0].getRates().add(pln);

            for (int i = 0; i < exchange.getBody()[0].getRates().size(); i++) {
                if(exchange.getBody()[0].getRates().get(i).getCurrency().equals(currencyName)){
                    return exchange.getBody()[0].getRates().get(i).getCode();
                }
            }

            return "błąd";
        }

        public List<String> getCurrenciesNames(){

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            HttpEntity httpEntity = new HttpEntity(httpHeaders);

            ResponseEntity<Currency[]> exchange = restTemplate.exchange("https://api.nbp.pl/api/exchangerates/tables/a/?format=json",
                    HttpMethod.GET,
                    httpEntity.EMPTY,
                    Currency[].class

            );

            Rate pln = new Rate();
            pln.setCode("PLN");
            pln.setCurrency("złoty");
            pln.setMid(1.0);
            exchange.getBody()[0].getRates().add(pln);

            List<String> currenciesNames = new ArrayList<>();
            for (int i = 0; i < exchange.getBody()[0].getRates().size(); i++) {
                currenciesNames.add(exchange.getBody()[0].getRates().get(i).getCurrency());
            }
            return currenciesNames;
        }
        public String convertCurrency(String currency1, String currency2, Double value){

            for (int i = 0; i < getCurrenciesNames().size(); i++) {
                if (currency1.equals(getCurrenciesNames().get(i))){
                    return Precision.round(getMid(currency1) * value / getMid(currency2), 2) + "";
                }
            }
            return "błąd";
        }

        public String getEffectiveDate(){
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            HttpEntity httpEntity = new HttpEntity(httpHeaders);

            ResponseEntity<Currency[]> exchange = restTemplate.exchange("https://api.nbp.pl/api/exchangerates/tables/a/?format=json",
                    HttpMethod.GET,
                    httpEntity.EMPTY,
                    Currency[].class

            );
            return exchange.getBody()[0].getEffectiveDate().toString();
        }
}
