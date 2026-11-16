package com.rentmen.app.services.imp;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UrlShortenerService {

    private static final String TINYURL_API_URL = "http://tinyurl.com/api-create.php?url=";
    public static String shortenUrl(String longUrl) {
        String shortUrl = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String encodedUrl = URLEncoder.encode(longUrl, StandardCharsets.UTF_8.toString());
//            HttpPost httpPost = new HttpPost(TINYURL_API_URL + encodedUrl);
            HttpGet httpGet = new HttpGet(TINYURL_API_URL + longUrl);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                shortUrl = EntityUtils.toString(response.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return shortUrl;
    }
}
