package com.vincent.Bakeryapi.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Product {
    String id;
    String name;
    List<PackageItem> packages;
}
