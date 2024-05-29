package com.ibilet.entities;

import java.util.Map;
import java.util.UUID;

public class Flight {
    private final String code = UUID.randomUUID().toString();
    private String planeType;
    private int totalSeats;
    private Map<String, Double> prices;
    private String route;


    public String getCode() {
        return code;
    }

    public String getPlaneType() {
        return planeType;
    }

    public void setPlaneType(String planeType) {
        this.planeType = planeType;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Map<String, Double> getPrices() {
        return prices;
    }

    public void setPrices(Map<String, Double> prices) {
        this.prices = prices;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
}
