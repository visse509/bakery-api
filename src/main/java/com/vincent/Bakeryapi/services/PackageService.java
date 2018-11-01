package com.vincent.Bakeryapi.services;

import com.vincent.Bakeryapi.domain.PackageItem;
import com.vincent.Bakeryapi.domain.Product;
import com.vincent.Bakeryapi.dtos.PackageRequest;
import com.vincent.Bakeryapi.dtos.PackageResponse;
import com.vincent.Bakeryapi.dtos.QuantityEntries;
import com.vincent.Bakeryapi.services.utils.AmountQuantityComparator;
import com.vincent.Bakeryapi.sources.PriceListSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PackageService {

    private PriceListSource priceListSource;

    @Autowired
    public PackageService(PriceListSource priceListSource) {
        this.priceListSource = priceListSource;
    }

    //Get all requested packages
    public List<PackageResponse> getPackageResponses(PackageRequest packageRequest) {
        List<PackageResponse> packageItems = new ArrayList<>();
        packageRequest.getItems().forEach((id, key) -> packageItems.add(getPackageResponse(id, key)));
        return packageItems;
    }

    //Get requested package by id and quantity
    private PackageResponse getPackageResponse(String id, Integer quantity) {
        Product product = priceListSource.getPriceList().get(id);
        List<PackageItem> pricePackageList = product.getPackages();

        return PackageResponse.builder()
                .id(id)
                .quantityEntries(getQuantityEntries(quantity, pricePackageList))
                .build();
    }

    //Get the most efficient QuantityEntries
    private QuantityEntries getQuantityEntries(Integer quantity, List<PackageItem> pricePackageList) {
        List<QuantityEntries> solutions = new ArrayList<>();

        for (PackageItem packageItem : pricePackageList) {
            for (PackageItem packageItem2 : pricePackageList) {
                Integer quantitySoFar = 0;
                QuantityEntries quantityEntries = new QuantityEntries();

                next:
                for (int i = 0; i <= Math.floorDiv(quantity, packageItem.getQuantity()); i++) {
                    for (int j = 0; j <= Math.floorDiv(quantity, packageItem2.getQuantity()); j++) {

                        if (quantity - quantitySoFar >= packageItem.getQuantity()) {
                            quantityEntries.addPackageItem(packageItem);
                            quantitySoFar += (packageItem.getQuantity());
                        }
                        if (quantity - quantitySoFar >= packageItem2.getQuantity()) {
                            quantityEntries.addPackageItem(packageItem2);
                            quantitySoFar += (packageItem2.getQuantity());
                        } else {
                            break next;
                        }
                        solutions.add(quantityEntries);
                    }
                }
            }
        }
        solutions.sort(new AmountQuantityComparator(quantity));
        return solutions.stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No solutions found for the package request"));
    }
}
