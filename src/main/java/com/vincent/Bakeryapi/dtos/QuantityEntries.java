package com.vincent.Bakeryapi.dtos;

import com.vincent.Bakeryapi.domain.PackageItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class QuantityEntries {
    private Set<QuantityEntry> quantityEntries;

    public QuantityEntries() {
        this.quantityEntries = new HashSet<>();
    }

    public void addPackageItem(PackageItem packageItem) {
        Optional<QuantityEntry> quantityEntryOptional = quantityEntries.stream()
                .filter(quantityEntry -> quantityEntry.getPackageItem().equals(packageItem))
                .findFirst();
        if(quantityEntryOptional.isPresent()) {
            quantityEntryOptional.get().addAmount();
        } else {
            quantityEntries.add(new QuantityEntry(packageItem));
        }
    }

    public Integer findBiggestPackage() {
        return quantityEntries.stream()
                .map(quantityEntry -> quantityEntry.getPackageItem().getQuantity())
                .sorted(Comparator.reverseOrder())
                .findFirst()
                .orElse(0);
    }
}
