import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.FileOutputStream;
import java.io.IOException;

public class Main {

    public static final String url = "https://api.nasa.gov/planetary/apod?api_key=2M1rlk4xCyq57D8BtfeKsJc4q374YArKOLfGNFKp";
    public static final ObjectMapper mapper = new ObjectMapper();

    //        https://api.nasa.gov/
    public static void main(String[] args) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        // максимальное время ожидание подключения к серверу
                        .setConnectTimeout(5000)
                        // максимальное время ожидания получения данных
                        .setSocketTimeout(30000)
                        // возможность следовать редиректу в ответе
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        CloseableHttpResponse response = httpClient.execute(new HttpGet(url));

//        Scanner sc = new Scanner(response.getEntity().getContent());
//        System.out.println(sc.nextLine());

        NASA nasa = mapper.readValue(response.getEntity().getContent(), NASA.class);
//        System.out.println(nasa);

        CloseableHttpResponse image = httpClient.execute(new HttpGet(nasa.getHdurl()));
        String[] nasaArray = nasa.getHdurl().split("/");
        String fileName = nasaArray[nasaArray.length - 1];

        HttpEntity entity = image.getEntity();
        if (entity != null) {
            FileOutputStream fos = new FileOutputStream(fileName);
            entity.writeTo(fos);
            fos.close();
        }
    }
}
