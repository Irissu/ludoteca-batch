package com.ccsw.tutorial_batch.model;

public class GameAvailability {
    private String title;
    private boolean available;
    private String hasStock;

    public GameAvailability(String title, String hasStock) {
        this.title = title;
        this.hasStock = hasStock;
    }

    public GameAvailability(String title, boolean available) {
        this.title = title;
        this.available = available;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getHasStock() {
        return hasStock;
    }

    public void setHasStock(String hasStock) {
        this.hasStock = hasStock;
    }

    @Override
    public String toString() {
        return "Disponibilidad: {" + "Titulo='" + title + '\'' + ", Disponible=" + hasStock + '}';
    }
}
