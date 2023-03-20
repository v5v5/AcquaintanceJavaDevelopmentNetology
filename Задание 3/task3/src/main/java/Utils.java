import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class Utils {
    public static final ObjectMapper mapper = new ObjectMapper();

    public static final CloseableHttpClient httpClient = HttpClientBuilder.create()
            .setDefaultRequestConfig(RequestConfig.custom()
                    // максимальное время ожидание подключения к серверу
                    .setConnectTimeout(5000)
                    // максимальное время ожидания получения данных
                    .setSocketTimeout(30000)
                    // возможность следовать редиректу в ответе
                    .setRedirectsEnabled(false)
                    .build())
            .build();

    public static String getURL(String url) throws IOException {
        CloseableHttpResponse response = httpClient.execute(new HttpGet(url));
        NASA nasa = mapper.readValue(response.getEntity().getContent(), NASA.class);
        return nasa.getUrl();
    }
}
