package com.vincent.Bakeryapi.controllers;


import com.vincent.Bakeryapi.dtos.PackageRequest;
import com.vincent.Bakeryapi.dtos.PackageResponse;
import com.vincent.Bakeryapi.services.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
public class PackageController {

    private PackageService packageService;

    @Autowired
    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @RequestMapping(value = "/packages", method = POST)
    @PostMapping
    @ResponseBody
    List<PackageResponse> packages(@RequestBody PackageRequest packageRequest) {
        return packageService.getPackageResponses(packageRequest);
    }
}
