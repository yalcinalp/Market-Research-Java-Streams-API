import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Streams {
    private static String inputFile;
    private static int q;

    public static void main(String[] args) throws IOException {
        inputFile = args[0];
        q = Integer.parseInt(args[1]);
        process();
    }

    public static Stream<Row> getRowStream() throws IOException {
        return Files.lines(Paths.get(inputFile))
            .skip(1)
            .map(line -> line.split(",", -1))
            .map(Row::toRow);
    }

    public static long getFrequency(Predicate<Row> datePredicate,
        Predicate<Row> presencePredicate) throws IOException {
        return getRowStream()
            .filter(datePredicate)
            .filter(presencePredicate)
            .count();
    }

    public static long getNumberOfPurchases(Predicate<Row> datePredicate,
        Function<Row, Integer> mapFunction) throws IOException {
        return getRowStream()
            .filter(datePredicate)
            .map(mapFunction)
            .reduce(Integer::sum)
            .orElse(0);
    }

    public static double getAverageAge(Predicate<Row> presencePredicate)
        throws IOException {
        return getRowStream()
            .filter(presencePredicate)
            .mapToInt(row -> row.age)
            .average()
            .orElse(0.0);
    }

    public static double getInflation(Predicate<Row> presencePredicate,
        Function<Row, Double> mapFunction) throws IOException {
        return getRowStream()
                   .filter(presencePredicate)
                   .max(Comparator.comparing(row -> row.date))
                   .map(mapFunction)
                   .orElse(0.0)

            -

            getRowStream()
                .filter(presencePredicate)
                .min(Comparator.comparing(row -> row.date))
                .map(mapFunction)
                .orElse(0.0);
    }

    public static void process() throws IOException {
        Stream<Row> rows = getRowStream();

        if (q == 1) { // OK
            int answer1 = rows.filter(row -> row.name.startsWith("A"))
                              .mapToInt(Row::getQuantities)
                              .sum();
            System.out.println(answer1);

        } else if (q == 2) { // OK
            double answer2 = rows.mapToDouble(Row::getMostExpensiveProductPrice)
                                 .max()
                                 .orElse(0.0);
            System.out.println(answer2);

        } else if (q == 3) { // OK
            LocalDate answer3 =
                rows.max(Comparator.comparingDouble(Row::getPurchaseAmount))
                    .map(row -> row.date)
                    .orElse(null);
            System.out.println(answer3);

        } else if (q == 4) { // OK
            String answer4 = "";
            Predicate<Row> datePredicate =
                row -> row.date.isBefore(LocalDate.of(2000, 1, 1));

            long breadFreq =
                getFrequency(datePredicate, row -> row.quantityOfBread > 0);
            long milkFreq =
                getFrequency(datePredicate, row -> row.quantityOfMilk > 0);
            long eggFreq =
                getFrequency(datePredicate, row -> row.quantityOfEgg > 0);
            long potatoesFreq =
                getFrequency(datePredicate, row -> row.quantityOfPotatoes > 0);
            long tomatoesFreq =
                getFrequency(datePredicate, row -> row.quantityOfTomatoes > 0);

            long maxFreq = Collections.max(List.of(
                breadFreq, milkFreq, eggFreq, potatoesFreq, tomatoesFreq));

            if (maxFreq == breadFreq) {
                answer4 = "bread";
            } else if (maxFreq == milkFreq) {
                answer4 = "milk";
            } else if (maxFreq == eggFreq) {
                answer4 = "egg";
            } else if (maxFreq == potatoesFreq) {
                answer4 = "potatoes";
            } else if (maxFreq == tomatoesFreq) {
                answer4 = "tomatoes";
            }
            System.out.println(answer4);

        } else if (q == 5) { // OK
            String answer5 = "";
            Predicate<Row> datePredicate =
                row -> row.date.isAfter(LocalDate.of(1999, 12, 31));

            long breadPurchases =
                getNumberOfPurchases(datePredicate, row -> row.quantityOfBread);
            long milkPurchases =
                getNumberOfPurchases(datePredicate, row -> row.quantityOfMilk);
            long eggPurchases =
                getNumberOfPurchases(datePredicate, row -> row.quantityOfEgg);
            long potatoesPurchases = getNumberOfPurchases(
                datePredicate, row -> row.quantityOfPotatoes);
            long tomatoesPurchases = getNumberOfPurchases(
                datePredicate, row -> row.quantityOfTomatoes);

            long minPurchase =
                Collections.min(List.of(breadPurchases, milkPurchases,
                    eggPurchases, potatoesPurchases, tomatoesPurchases));

            if (minPurchase == breadPurchases) {
                answer5 = "bread";
            } else if (minPurchase == milkPurchases) {
                answer5 = "milk";
            } else if (minPurchase == eggPurchases) {
                answer5 = "egg";
            } else if (minPurchase == potatoesPurchases) {
                answer5 = "potatoes";
            } else if (minPurchase == tomatoesPurchases) {
                answer5 = "tomatoes";
            }
            System.out.println(answer5);

        } else if (q == 6) { // OK
            String answer6 = "";

            double breadAverageAge =
                getAverageAge(row -> row.quantityOfBread > 0);
            double milkAverageAge =
                getAverageAge(row -> row.quantityOfMilk > 0);
            double eggAverageAge = getAverageAge(row -> row.quantityOfEgg > 0);
            double potatoesAverageAge =
                getAverageAge(row -> row.quantityOfPotatoes > 0);
            double tomatoesAverageAge =
                getAverageAge(row -> row.quantityOfTomatoes > 0);

            double minAverageAge =
                Collections.min(List.of(breadAverageAge, milkAverageAge,
                    eggAverageAge, potatoesAverageAge, tomatoesAverageAge));

            if (minAverageAge == breadAverageAge) {
                answer6 = "bread";
            } else if (minAverageAge == milkAverageAge) {
                answer6 = "milk";
            } else if (minAverageAge == eggAverageAge) {
                answer6 = "egg";
            } else if (minAverageAge == potatoesAverageAge) {
                answer6 = "potatoes";
            } else if (minAverageAge == tomatoesAverageAge) {
                answer6 = "tomatoes";
            }
            System.out.println(answer6);
        }
        if (q == 7) { 
            // Efficent single stream read (But a bit ugly currently)
            // TODO: write a helper func to get rid of duplicates...

            // Create a holder for oldest and newest prices
            double[] breadPrices = new double[2]; // [0]: oldest, [1]: newest
            double[] milkPrices = new double[2];
            double[] eggPrices = new double[2];
            double[] potatoesPrices = new double[2];
            double[] tomatoesPrices = new double[2];

            // Initialize dates
            LocalDate[] breadDates =
                new LocalDate[2]; // [0]: oldest, [1]: newest
            LocalDate[] milkDates = new LocalDate[2];
            LocalDate[] eggDates = new LocalDate[2];
            LocalDate[] potatoesDates = new LocalDate[2];
            LocalDate[] tomatoesDates = new LocalDate[2];

            getRowStream().forEach(row -> {
                // Update oldest and newest prices for bread
                if (row.quantityOfBread > 0) {
                    if (breadDates[0] == null
                        || row.date.isBefore(breadDates[0])) {
                        breadDates[0] = row.date;
                        breadPrices[0] = row.priceOfBread;
                    }
                    if (breadDates[1] == null
                        || row.date.isAfter(breadDates[1])) {
                        breadDates[1] = row.date;
                        breadPrices[1] = row.priceOfBread;
                    }
                }

                // Update oldest and newest prices for milk
                if (row.quantityOfMilk > 0) {
                    if (milkDates[0] == null
                        || row.date.isBefore(milkDates[0])) {
                        milkDates[0] = row.date;
                        milkPrices[0] = row.priceOfMilk;
                    }
                    if (milkDates[1] == null
                        || row.date.isAfter(milkDates[1])) {
                        milkDates[1] = row.date;
                        milkPrices[1] = row.priceOfMilk;
                    }
                }

                // Update oldest and newest prices for eggs
                if (row.quantityOfEgg > 0) {
                    if (eggDates[0] == null || row.date.isBefore(eggDates[0])) {
                        eggDates[0] = row.date;
                        eggPrices[0] = row.priceOfEgg;
                    }
                    if (eggDates[1] == null || row.date.isAfter(eggDates[1])) {
                        eggDates[1] = row.date;
                        eggPrices[1] = row.priceOfEgg;
                    }
                }

                // Update oldest and newest prices for potatoes
                if (row.quantityOfPotatoes > 0) {
                    if (potatoesDates[0] == null
                        || row.date.isBefore(potatoesDates[0])) {
                        potatoesDates[0] = row.date;
                        potatoesPrices[0] = row.priceOfPotatoes;
                    }
                    if (potatoesDates[1] == null
                        || row.date.isAfter(potatoesDates[1])) {
                        potatoesDates[1] = row.date;
                        potatoesPrices[1] = row.priceOfPotatoes;
                    }
                }

                // Update oldest and newest prices for tomatoes
                if (row.quantityOfTomatoes > 0) {
                    if (tomatoesDates[0] == null
                        || row.date.isBefore(tomatoesDates[0])) {
                        tomatoesDates[0] = row.date;
                        tomatoesPrices[0] = row.priceOfTomatoes;
                    }
                    if (tomatoesDates[1] == null
                        || row.date.isAfter(tomatoesDates[1])) {
                        tomatoesDates[1] = row.date;
                        tomatoesPrices[1] = row.priceOfTomatoes;
                    }
                }
            });

            // Calculate inflation
            double breadInflation = breadPrices[1] - breadPrices[0];
            double milkInflation = milkPrices[1] - milkPrices[0];
            double eggInflation = eggPrices[1] - eggPrices[0];
            double potatoesInflation = potatoesPrices[1] - potatoesPrices[0];
            double tomatoesInflation = tomatoesPrices[1] - tomatoesPrices[0];

            // Find the product with the highest inflation
            String answer7 = "";
            double maxInflation =
                Collections.max(List.of(breadInflation, milkInflation,
                    eggInflation, potatoesInflation, tomatoesInflation));
            if (maxInflation == breadInflation) {
                answer7 = "bread";
            } else if (maxInflation == milkInflation) {
                answer7 = "milk";
            } else if (maxInflation == eggInflation) {
                answer7 = "egg";
            } else if (maxInflation == potatoesInflation) {
                answer7 = "potatoes";
            } else if (maxInflation == tomatoesInflation) {
                answer7 = "tomatoes";
            }

            System.out.println(answer7);
        }
    }
}
