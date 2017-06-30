package com.king;

import com.adchina.demandsideplatform.model.rtb.IqiyiBidding;
import com.google.protobuf.Message;
import com.googlecode.protobuf.format.JsonFormat;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) throws IOException {

        String path = "/Users/King/Desktop/iqiyi.json";

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));

        StringBuilder stringBuilder = new StringBuilder("");

        String line = null;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        Message.Builder builder = IqiyiBidding.BidRequest.newBuilder();

        JsonFormat.merge(stringBuilder.toString(), builder);

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost post = new HttpPost("http://t.mct01.com/r.htm?t=17");
        post.addHeader("Content-Type", "application/octet-stream;charset=utf-8");
        post.setEntity(new ByteArrayEntity(builder.build().toByteArray()));

        CloseableHttpResponse httpResponse = httpClient.execute(post);

        HttpEntity entity = httpResponse.getEntity();
        InputStream inputStream = entity.getContent();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder respStringBuilder = new StringBuilder();
        String respLine = null;
        while ((respLine = bufferedReader.readLine()) != null) {
            respStringBuilder.append(respLine);
        }

        System.out.println(respStringBuilder.toString());

        bufferedReader.close();
        inputStream.close();

    }

}
