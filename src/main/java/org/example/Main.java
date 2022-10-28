package org.example;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static final String REQUEST_URI = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";

    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();

        HttpGet request = new HttpGet(REQUEST_URI);
        CloseableHttpResponse response = httpClient.execute(request);


        List<CatInfo> cats = objectMapper.readValue(response.getEntity().getContent(), new TypeReference<>() {
        });
        cats.stream()
                .filter(c -> c.getUpvotes() != null && Integer.parseInt(c.getUpvotes()) > 2)
                .forEach(System.out::println);
    }
}