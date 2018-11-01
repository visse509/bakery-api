package com.vincent.Bakeryapi.sources;

import com.vincent.Bakeryapi.domain.PackageItem;
import com.vincent.Bakeryapi.domain.Product;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LocalPriceListSource implements PriceListSource {

    @Override
    public Map<String, Product> getPriceList() {
        Map<String, Product> priceItems = new HashMap<>();
        addVS5(priceItems);
        addMB11(priceItems);
        addCF(priceItems);
        return priceItems;
    }

    private void addCF(Map<String, Product> priceItems) {
        List<PackageItem> packageList = new ArrayList<>();
        packageList.add(new PackageItem(3, new BigDecimal("5.95")));
        packageList.add(new PackageItem(5, new BigDecimal("9.95")));
        packageList.add(new PackageItem(9, new BigDecimal("16.99")));
        Product product = Product.builder()
                .id("CF")
                .name("Croissant")
                .packages(packageList)
                .build();
        priceItems.put("CF", product);
    }


    private void addMB11(Map<String, Product> priceItems) {
        List<PackageItem> packageList = new ArrayList<>();
        packageList.add(new PackageItem(2, new BigDecimal("9.95")));
        packageList.add(new PackageItem(5, new BigDecimal("16.95")));
        packageList.add(new PackageItem(8, new BigDecimal("24.95")));
        Product product = Product.builder()
                .id("MB11")
                .name("Blueberry Muffin")
                .packages(packageList)
                .build();
        priceItems.put("MB11", product);
    }

    private void addVS5(Map<String, Product> priceItems) {
        List<PackageItem> packageList = new ArrayList<>();
        packageList.add(new PackageItem(3, new BigDecimal("6.99")));
        packageList.add(new PackageItem(5, new BigDecimal("8.99")));
        Product product = Product.builder()
                .id("VS5")
                .name("Vegemite Scroll")
                .packages(packageList)
                .build();
        priceItems.put("VS5", product);
    }
}
