package cn.syl.zuul.conf;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class FallBackConf implements FallbackProvider {



    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse() {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.BAD_REQUEST;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.BAD_REQUEST.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return "message";
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream("{\"code\":400,\"message\":\"系统繁忙\"}".getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                return httpHeaders;
            }
        };
    }

    @Override
    public ClientHttpResponse fallbackResponse(Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.BAD_REQUEST;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.BAD_REQUEST.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return "message";
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream(("{\"code\":400,\"message\":\""+cause.getMessage()+"\"}").getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                return httpHeaders;
            }
        };
    }
}
