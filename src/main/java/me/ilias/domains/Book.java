package me.ilias.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private String title;
    private boolean available;
    private LocalDate availableDate;
    private double minPrice;
    private double maxPrice;
    private double defaultTVA;
}
