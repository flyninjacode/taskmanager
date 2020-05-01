package com.taskmanager.api.client;

import feign.Feign;
import feign.okhttp.OkHttpClient;
import okhttp3.internal.JavaNetCookieJar;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.http.converter.FormHttpMessageConverter;
import com.taskmanager.api.controller.IRestLoginController;

import java.net.CookieManager;
import java.net.CookiePolicy;

public class AuthResourceClient {
    public static IRestLoginController getInstance(final String resourceUrl) {
        final FormHttpMessageConverter converter = new FormHttpMessageConverter();
        final HttpMessageConverters messageConverters = new HttpMessageConverters(converter);
        final ObjectFactory<HttpMessageConverters> objectFactory = () -> messageConverters;

        final CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        final okhttp3.OkHttpClient.Builder builder = new okhttp3.OkHttpClient().newBuilder();
        builder.cookieJar(new JavaNetCookieJar(cookieManager));

        return Feign.builder()
                .client(new OkHttpClient(builder.build()))
                .contract(new SpringMvcContract())
                .encoder(new SpringEncoder(objectFactory))
                .decoder(new SpringDecoder(objectFactory))
                .target(IRestLoginController.class, resourceUrl);
    }
}
