package com.vincent.Bakeryapi.dtos;

import com.vincent.Bakeryapi.domain.PackageItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class QuantityEntry {
    Integer amount;
    PackageItem packageItem;

    QuantityEntry(PackageItem packageItem) {
        this.packageItem = packageItem;
        this.amount = 1;
    }

    void addAmount() {
        this.amount++;
    }
}
