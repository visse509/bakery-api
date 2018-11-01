package com.vincent.Bakeryapi.dtos;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class PackageResponse {
    String id;
    QuantityEntries quantityEntries;
    BigDecimal totalPrice;

    public BigDecimal getTotalPrice() {
        return quantityEntries.getQuantityEntries()
                .stream()
                .map(quantityEntry -> quantityEntry.getPackageItem().getPrice().multiply(BigDecimal.valueOf(quantityEntry.getAmount())))
                .reduce(BigDecimal::add)
                .map(bigDecimal -> bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP))
                .orElse(BigDecimal.ZERO);
    }
}
