import java.util.ArrayList;
import java.util.HashMap;

public class Metro {

    HashMap<String, ArrayList<String>> stations = new HashMap<>();
    ArrayList<Line> lines = new ArrayList<>();

    public void addLine(Line line) {
        lines.add(line);
    }
    public void addStations(String lineName, ArrayList<String> stations) {
        this.stations.put(lineName, stations);
    }
}
