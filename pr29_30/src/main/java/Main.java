import java.util.Scanner;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        Stream.
                of(str.split("[\\p{Punct}\\s]+"))
                .collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()))
                .entrySet().stream()
                .sorted((o1, o2) -> (o1.getValue().equals(o2.getValue())) ? o1.getKey().compareTo(o2.getKey()) :
                        o2.getValue().compareTo(o1.getValue()))
                .limit(10)
                .forEach(s -> System.out.println(s.getKey()));
    }
}
