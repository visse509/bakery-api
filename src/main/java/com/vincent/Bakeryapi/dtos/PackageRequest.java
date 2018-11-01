package com.vincent.Bakeryapi.dtos;

import lombok.Data;

import java.util.Map;

@Data
public class PackageRequest {
    Map<String, Integer> items;
}
