import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class Util {

    public static LocalDate randomDate() {
        long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2015, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        return randomDate;
    }

    public static void main(String[] args) {
        System.out.println(randomDate());
    }
}
