package com.keyinclient;

import com.keyinclient.services.CityService;

public class ClientApplication {
    public static void main(String[] args) {
        CityService cityService = new CityService();
        
        
        cityService.getAllCities();
        cityService.getCityById(1L);
    }
}