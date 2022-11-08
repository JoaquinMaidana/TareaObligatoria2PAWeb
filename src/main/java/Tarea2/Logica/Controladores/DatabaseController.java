package Tarea2.Logica.Controladores;

import Tarea2.Logica.Interfaces.IDatabase;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.ibatis.jdbc.ScriptRunner;
import sun.misc.IOUtils;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseController implements IDatabase {

    private static DatabaseController instance = null;
    private static final String apiUrl = "https://upload-image-to-imgur.vercel.app/upload";

    private DatabaseController() {}

    public static DatabaseController getInstance() {
        if (instance == null) {
            instance = new DatabaseController();
        }
        return instance;
    }
    @Override
    public String guardarImagen(FileInputStream imagen){
        String url = "";
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(apiUrl);

            byte[] imagen_bytes = IOUtils.readFully(imagen, -1, false); // Convierte el archivo a bytes

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody(
                    "file",  imagen_bytes, ContentType.APPLICATION_OCTET_STREAM, "imagen.png");

            HttpEntity multipart = builder.build();
            httpPost.setEntity(multipart);

            CloseableHttpResponse response = client.execute(httpPost);
            client.close();
            String responseString = new BasicResponseHandler().handleResponse(response);

            url = responseString.substring(responseString.indexOf("https://i.imgur.com/"));
            url = url.substring(0, url.indexOf("\""));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al guardar la imagen", e);
        }

        return url;
    }

    /**
     * Metodo que permite obtener una imagen de la base de datos
     * @param imagen de tipo File
     *                de la imagen que se guardar en la base de datos
     * @return String
     */
    @Override
    public String guardarImagen(File imagen){
        String url = "";
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(apiUrl);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody(
                    "file",  imagen, ContentType.APPLICATION_OCTET_STREAM, "imagen.png");

            HttpEntity multipart = builder.build();
            httpPost.setEntity(multipart);

            CloseableHttpResponse response = client.execute(httpPost);
            client.close();
            String responseString = new BasicResponseHandler().handleResponse(response);

            url = responseString.substring(responseString.indexOf("https://i.imgur.com/"));
            url = url.substring(0, url.indexOf("\""));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al guardar la imagen", e);
        }

        return url;
    }
}
