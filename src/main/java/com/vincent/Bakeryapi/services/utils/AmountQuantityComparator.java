package com.vincent.Bakeryapi.services.utils;

import com.vincent.Bakeryapi.dtos.QuantityEntries;

import java.util.Comparator;

public class AmountQuantityComparator implements Comparator<QuantityEntries> {

    private Integer quantity;

    public AmountQuantityComparator(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public int compare(QuantityEntries first, QuantityEntries second) {
        //Sort first on the difference of the amount of packages to the specified quantity
        Integer differenceFirst = getDiffereneFromQuantity(first);
        Integer differenceSecond = getDiffereneFromQuantity(second);
        int distanceComparison = differenceSecond.compareTo(differenceFirst);

        if (distanceComparison != 0) {
            return distanceComparison;
        } else {
            //Sort secondly on how many packages are used in the solution.
            Integer amountOfPackagesFirst = first.getQuantityEntries().stream()
                    .map(quantityEntry -> quantityEntry.getPackageItem().getQuantity())
                    .reduce(Integer::sum)
                    .orElse(0);
            Integer amountOfPackagesSecond = second.getQuantityEntries().stream()
                    .map(quantityEntry -> quantityEntry.getPackageItem().getQuantity())
                    .reduce(Integer::sum)
                    .orElse(0);
            int packageAmountComparison = amountOfPackagesSecond.compareTo(amountOfPackagesFirst);
            if (packageAmountComparison != 0) {
                return packageAmountComparison;
            } else {
                //Sort thirdly on the solution which uses the biggest package.
                Integer biggestPackageFirst = first.findBiggestPackage();
                Integer biggestPackageSecond = second.findBiggestPackage();
                return biggestPackageSecond.compareTo(biggestPackageFirst);
            }
        }
    }

    private int getDiffereneFromQuantity(QuantityEntries first) {
        return first.getQuantityEntries().stream()
                .map(quantityEntry -> quantityEntry.getAmount() * quantityEntry.getPackageItem().getQuantity())
                .reduce(Integer::sum)
                .orElse(0) - quantity;
    }
}
