import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            String lineName, lineNumber,
                    savePath = "pr25_26/src/main/java/json/output.json",
                    url = "https://www.moscowmap.ru/metro.html#lines";
            Document document = Jsoup.connect(url).maxBodySize(0).get();
            Line line;
            Elements stationElements, lineElements = document.select("span[data-line]");
            ArrayList<String> stations;
            Metro metro = new Metro();

            for (Element lineElement : lineElements)
            {
                lineNumber = lineElement.attr("data-line");
                lineName = lineElement.text();
                line = new Line(lineNumber, lineName);

                stations = new ArrayList<>();
                stationElements = document.select("div[data-line='" +
                        lineElement.attr("data-line") + "'] span.name");

                for (Element stationElement : stationElements)
                    stations.add(stationElement.text());

                metro.addStations(lineNumber, stations);
                metro.addLine(line);
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Writer writer = new OutputStreamWriter(new FileOutputStream(savePath),
                    StandardCharsets.UTF_8);

            writer.write(gson.toJson(metro));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
