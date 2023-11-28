import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        HttpGet request = new HttpGet("https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");
        CloseableHttpResponse response = httpClient.execute(request);

        String json = EntityUtils.toString(response.getEntity());
        ObjectMapper mapper = new ObjectMapper();

        Cat[] cats = mapper.readValue(json, Cat[].class);
        List<Cat> filteredCats = Arrays.stream(cats)
                .filter(cat -> cat.getUpvotes() != null && cat.getUpvotes() > 0)
                .collect(Collectors.toList());

        System.out.println(filteredCats);

        response.close();
        httpClient.close();
    }

    public static class Cat {
        private String id;
        private String text;
        private String type;
        private String user;
        private Integer upvotes;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public Integer getUpvotes() {
            return upvotes;
        }

        public void setUpvotes(Integer upvotes) {
            this.upvotes = upvotes;
        }

        @Override
        public String toString() {
            return "{" +
                    '\n' +
                    "   id='" + id + '\'' + ',' +
                    "\n   text='" + text + '\'' + ',' +
                    "\n   type='" + type + '\'' + ',' +
                    "\n   user='" + user + '\'' + ',' +
                    "\n   upvotes=" + upvotes +
                    "\n" + '}' + ',' +
                    '\n';
        }
    }
}