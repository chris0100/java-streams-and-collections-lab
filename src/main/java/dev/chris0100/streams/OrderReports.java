package dev.chris0100.streams;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/** Typical reporting questions answered with streams. */
public final class OrderReports {

    private OrderReports() {
    }

    /** Revenue per country, sorted by country name. */
    public static Map<String, BigDecimal> revenueByCountry(List<Order> orders) {
        return orders.stream()
                .filter(Order::paid)
                .collect(Collectors.groupingBy(Order::country, TreeMap::new,
                        Collectors.reducing(BigDecimal.ZERO, Order::total, BigDecimal::add)));
    }

    /** Paid vs unpaid count. */
    public static Map<Boolean, Long> paidVsUnpaid(List<Order> orders) {
        return orders.stream().collect(Collectors.partitioningBy(Order::paid, Collectors.counting()));
    }

    /** How many times each SKU was ordered (flatMap + counting). */
    public static Map<String, Long> skuPopularity(List<Order> orders) {
        return orders.stream()
                .flatMap(o -> o.skus().stream())
                .collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.counting()));
    }

    /** Biggest order per customer; toMap with a merge function to resolve duplicate keys. */
    public static Map<String, Order> biggestOrderPerCustomer(List<Order> orders) {
        return orders.stream().collect(Collectors.toMap(
                Order::customer,
                Function.identity(),
                (a, b) -> a.total().compareTo(b.total()) >= 0 ? a : b,
                TreeMap::new));
    }

    /** Top N customers by total spent. */
    public static List<String> topCustomers(List<Order> orders, int n) {
        return orders.stream()
                .collect(Collectors.groupingBy(Order::customer,
                        Collectors.reducing(BigDecimal.ZERO, Order::total, BigDecimal::add)))
                .entrySet().stream()
                .sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(n)
                .map(Map.Entry::getKey)
                .toList();
    }

    /** Average and max of paid orders in a single pass, using teeing (Java 12+). */
    public static String averageAndMax(List<Order> orders) {
        return orders.stream()
                .filter(Order::paid)
                .map(Order::total)
                .collect(Collectors.teeing(
                        Collectors.averagingDouble(BigDecimal::doubleValue),
                        Collectors.maxBy(Comparator.<BigDecimal>naturalOrder()),
                        (avg, max) -> String.format(java.util.Locale.ROOT, "avg=%.2f max=%s", avg, max.orElse(BigDecimal.ZERO))));
    }

    /** Custom collector: summary statistics over order totals. */
    public static DoubleSummaryStatistics revenueStats(List<Order> orders) {
        return orders.stream().collect(
                DoubleSummaryStatistics::new,
                (stats, order) -> stats.accept(order.total().doubleValue()),
                DoubleSummaryStatistics::combine);
    }
}
