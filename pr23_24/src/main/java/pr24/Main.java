package pr24;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;


public class Main {
    public static void main(String[] args) {
        try {
            InputStream in;
            String filename;
            String url = "https://www.mirea.ru/";
            HashMap<String, String> hashMap = new HashMap<>();
            Elements images = Jsoup.connect(url).get().select("img");
            for (Element image : images) {
                url = image.attr("abs:src");
                if (url.contains(".svg") || url.contains(".png") || url.contains(".jpg") || url.contains(".webp")) {
                    filename = url.split("/")[url.split("/").length - 1];
                    if (!hashMap.containsKey(filename))
                        hashMap.put(filename, url);
                }
            }
            for (String name : hashMap.keySet()) {
                in = new URL(hashMap.get(name)).openStream();
                Files.copy(in, Paths.get("pr23_24\\src\\main\\java\\pr24\\images\\" + name), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
