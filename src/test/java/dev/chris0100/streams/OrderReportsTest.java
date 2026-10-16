package dev.chris0100.streams;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrderReportsTest {

    private final List<Order> orders = List.of(
            new Order("o1", "ana", "CO", List.of("A", "B"), new BigDecimal("100"), true),
            new Order("o2", "ana", "CO", List.of("A"), new BigDecimal("250"), true),
            new Order("o3", "luis", "MX", List.of("C"), new BigDecimal("80"), true),
            new Order("o4", "maria", "AR", List.of("A", "C"), new BigDecimal("300"), false),
            new Order("o5", "luis", "MX", List.of("B"), new BigDecimal("120"), true));

    @Test
    void revenueByCountryOnlyCountsPaidOrders() {
        assertEquals(Map.of("CO", new BigDecimal("350"), "MX", new BigDecimal("200")),
                OrderReports.revenueByCountry(orders));
    }

    @Test
    void partitionAndFlatMap() {
        assertEquals(Map.of(true, 4L, false, 1L), OrderReports.paidVsUnpaid(orders));
        assertEquals(Map.of("A", 3L, "B", 2L, "C", 2L), OrderReports.skuPopularity(orders));
    }

    @Test
    void toMapWithMergeKeepsTheBiggestOrder() {
        assertEquals("o2", OrderReports.biggestOrderPerCustomer(orders).get("ana").id());
        assertEquals("o5", OrderReports.biggestOrderPerCustomer(orders).get("luis").id());
    }

    @Test
    void topCustomersAndTeeing() {
        assertEquals(List.of("ana", "maria"), OrderReports.topCustomers(orders, 2));
        assertEquals("avg=137.50 max=250", OrderReports.averageAndMax(orders));
        assertEquals(5, OrderReports.revenueStats(orders).getCount());
        assertEquals(300.0, OrderReports.revenueStats(orders).getMax(), 0.001);
    }
}
