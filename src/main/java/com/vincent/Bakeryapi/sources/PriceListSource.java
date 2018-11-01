package com.vincent.Bakeryapi.sources;
import com.vincent.Bakeryapi.domain.Product;

import java.util.Map;

public interface PriceListSource {
    Map<String, Product> getPriceList();
}
