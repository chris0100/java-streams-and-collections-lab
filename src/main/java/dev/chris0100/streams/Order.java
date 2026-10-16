package dev.chris0100.streams;

import java.math.BigDecimal;
import java.util.List;

public record Order(String id, String customer, String country, List<String> skus, BigDecimal total, boolean paid) {
}
