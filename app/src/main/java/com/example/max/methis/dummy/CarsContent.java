package com.example.max.methis.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Car class for providing sample content
 * autor: Maxime Sabran
 */
public class CarsContent {

    /**
     * An array of sample Cars items.
     */
    public static final List<Cars> ITEMS = new ArrayList<Cars>();
    /**
     * A map of sample Cars items, by ID.
     * * autor: Max
     */
    public static final Map<String, Cars> ITEM_MAP = new HashMap<String, Cars>();

    private static final int COUNT = 10;
    /**
     * Building a data structure containing all information for the sample of cars
     */

    private static ArrayList<ArrayList<String>> DataProvider() {
        ArrayList<String> carsName = new ArrayList<String>();
        ArrayList<String> carsColor = new ArrayList<String>();
        ArrayList<String> carsYear = new ArrayList<String>();
        carsName.add("Peugeot 206");
        carsYear.add("1998");
        carsColor.add("Blue");
        carsName.add("Lotus Elise");
        carsYear.add("1996");
        carsColor.add("Red");
        carsName.add("Aston Martin DB4");
        carsYear.add("1958");
        carsColor.add("Green");
        carsName.add("Wolkswagen Kombi");
        carsYear.add("1950");
        carsColor.add("Flowers");
        carsName.add("Jaguar E Type");
        carsYear.add("1961");
        carsColor.add("Red");
        carsName.add("Citroën DS");
        carsYear.add("1955");
        carsColor.add("White");
        carsName.add("Trabant 601");
        carsYear.add("1964");
        carsColor.add("Grey");
        carsName.add("Lada Samara");
        carsYear.add("1984");
        carsColor.add("Grey");
        carsName.add("Ferrari 250");
        carsYear.add("1952");
        carsColor.add("Red");
        carsName.add("Lincoln Continental");
        carsYear.add("1939");
        carsColor.add("Black");
        ArrayList<ArrayList<String>> carsData = new ArrayList<>();
        carsData.add(carsName);
        carsData.add(carsYear);
        carsData.add(carsColor);


        return carsData;

    }

    /**
     * Create the cars items using the sample of data.
     */
    static {
        // Add some sample items.
        ArrayList<ArrayList<String>> data = DataProvider();
        for (int i = 0; i < COUNT; i++) {

            addItem(createCars(i,data));
        }
    }

    private static void addItem(Cars item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.name, item);
    }

    private static Cars createCars(int position, ArrayList<ArrayList<String>> data) {

        return new Cars(data.get(0).get(position), data.get(1).get(position),data.get(2).get(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A Car class containing information on the name, year of construction and the color.
     */
    public static class Cars {
        public final String name;
        public final String year;
        public final String color;

        public Cars(String name, String year, String color) {
            this.name = name;
            this.year = year;
            this.color = color;
        }
    }
}
