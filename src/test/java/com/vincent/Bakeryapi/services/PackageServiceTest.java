package com.vincent.Bakeryapi.services;

import com.vincent.Bakeryapi.domain.PackageItem;
import com.vincent.Bakeryapi.dtos.PackageRequest;
import com.vincent.Bakeryapi.dtos.PackageResponse;
import com.vincent.Bakeryapi.dtos.QuantityEntries;
import com.vincent.Bakeryapi.dtos.QuantityEntry;
import com.vincent.Bakeryapi.sources.LocalPriceListSource;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class PackageServiceTest {

    private PackageService packageService;

    @Before
    public void setup() {
        packageService = new PackageService(new LocalPriceListSource());
    }

    @Test
    public void testVS5package() throws Exception {
        PackageRequest packageRequest = getPackageRequest("VS5", 10);
        List<PackageResponse> packageResponses = packageService.getPackageResponses(packageRequest);
        packageResponses.forEach(packageResponse -> assertPackageResponse(packageResponse, getVS5ExpectedPackageResponse()));
    }

    @Test
    public void testMB11package() throws Exception {
        PackageRequest packageRequest = getPackageRequest("MB11", 14);
        List<PackageResponse> packageResponses = packageService.getPackageResponses(packageRequest);
        packageResponses.forEach(packageResponse -> assertPackageResponse(packageResponse, getMB11ExpectedPackageResponse()));
    }

    @Test
    public void testCFpackage() throws Exception {
        PackageRequest packageRequest = getPackageRequest("CF", 13);
        List<PackageResponse> packageResponses = packageService.getPackageResponses(packageRequest);
        packageResponses.forEach(packageResponse -> assertPackageResponse(packageResponse, getCFExpectedPackageResponse()));
    }

    private void assertPackageResponse(PackageResponse actualPackageResponse, PackageResponse expectedPackageResponse) {
        Assertions.assertThat(getAmount(expectedPackageResponse)).isEqualTo(getAmount(actualPackageResponse));
        Assertions.assertThat(expectedPackageResponse.getTotalPrice()).isEqualTo(actualPackageResponse.getTotalPrice());
    }

    private PackageResponse getVS5ExpectedPackageResponse() {
        HashSet<QuantityEntry> quantityEntries = new HashSet<>();
        QuantityEntry quantityEntry = QuantityEntry.builder()
                .amount(2)
                .packageItem(new PackageItem(5, new BigDecimal(8.99)))
                .build();
        quantityEntries.add(quantityEntry);
        return PackageResponse.builder()
                .id("VS5")
                .quantityEntries(QuantityEntries.builder().quantityEntries(quantityEntries).build())
                .build();
    }

    private PackageResponse getCFExpectedPackageResponse() {
        HashSet<QuantityEntry> quantityEntries = new HashSet<>();
        QuantityEntry quantityEntry = QuantityEntry.builder()
                .amount(2)
                .packageItem(new PackageItem(5, new BigDecimal(9.95)))
                .build();
        quantityEntries.add(quantityEntry);
        quantityEntry = QuantityEntry.builder()
                .amount(1)
                .packageItem(new PackageItem(3, new BigDecimal(5.95)))
                .build();
        quantityEntries.add(quantityEntry);
        return PackageResponse.builder()
                .id("CF")
                .quantityEntries(QuantityEntries.builder().quantityEntries(quantityEntries).build())
                .build();
    }
    private PackageResponse getMB11ExpectedPackageResponse() {
        HashSet<QuantityEntry> quantityEntries = new HashSet<>();
        QuantityEntry quantityEntry = QuantityEntry.builder()
                .amount(1)
                .packageItem(new PackageItem(8, new BigDecimal(24.95)))
                .build();
        quantityEntries.add(quantityEntry);
        quantityEntry = QuantityEntry.builder()
                .amount(3)
                .packageItem(new PackageItem(2, new BigDecimal(9.95)))
                .build();
        quantityEntries.add(quantityEntry);
        return PackageResponse.builder()
                .id("CF")
                .quantityEntries(QuantityEntries.builder().quantityEntries(quantityEntries).build())
                .build();
    }

    private Integer getAmount(PackageResponse expectedPackageResponse) {
        return expectedPackageResponse
                .getQuantityEntries()
                .getQuantityEntries()
                .stream()
                .map(QuantityEntry::getAmount)
                .reduce(Integer::sum)
                .orElse(0);
    }

    private PackageRequest getPackageRequest(String id, int quantity) {
        PackageRequest packageRequest = new PackageRequest();
        Map<String, Integer> itemMap = new HashMap<>();
        itemMap.put(id, quantity);
        packageRequest.setItems(itemMap);
        return packageRequest;
    }
}