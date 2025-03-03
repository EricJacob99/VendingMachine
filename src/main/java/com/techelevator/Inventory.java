package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;

public class Inventory {

    //creating a new file reader object
    FileReader fileReader = new FileReader();

    //creating a new treemap
    private TreeMap <String,Item> inventoryMap;

    //a constructor that assigns the treemap method from FileReader, to Inventory class's new treemap, called inventoryMap
    public Inventory() {
        this.inventoryMap = fileReader.restockVendingMachine();
    }

    public TreeMap<String, Item> getInventoryMap() {
        return inventoryMap;
    }


    public String displayItemsForCustomer() {
        //a for-each loop to extract everything from the inventoryMap treemap, and assign it to a string called "item"
        //where the vending machine items are displayed in a row, w/ item code first followed by name, price, and quantity remaining
        //this is for the customer, AKA console user
        String items = "";
        for (Map.Entry<String,Item> entry : inventoryMap.entrySet()) {
           String item = (entry.getKey() + " " + entry.getValue().getName() + " $" + entry.getValue().getPrice() + " " + entry.getValue().getQuantity() + "\n");
           items = items.concat(item);
        }
        return items;
    }



    public String displaySalesReport() {
        //Displays inventory in rows with slot number, name, price, and quantity remaining for all of the vending machine sales
        String items = "";
        double totalSales = 0.0;
        for (Map.Entry<String,Item> entry : inventoryMap.entrySet()) {
            String item = (entry.getValue().getName() + "|" + (10 - entry.getValue().getQuantity()) + "\n");
            items = items.concat(item);
            double itemSales = Math.floor(entry.getValue().getPrice() * (10 - entry.getValue().getQuantity()));
            totalSales = totalSales + itemSales;
        }
        items = items.concat("\n ** TOTAL SALES ** $" + (totalSales));
        return items;
    }


    public String dispenseItem(String itemCode, int quantityToRemove, CashBox cashBox) {
        //Lowers item quantity by selected quantity and displays quantity, name, cost, and updates balance by subtracting cost of purchase
        inventoryMap.get(itemCode).setQuantity(inventoryMap.get(itemCode).getQuantity() - quantityToRemove);
        double newBalance = cashBox.getBalance() - Math.floor(inventoryMap.get(itemCode).getPrice() * quantityToRemove);
        cashBox.setBalance(newBalance);
//        String filePath = "Log.txt";
//        File logFile = new File(filePath);
//        PrintWriter fileInput = new PrintWriter(logFile);
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
//        LocalDateTime now = LocalDateTime.now();
//        String formattedDate = dateTimeFormatter.format(now);
//        fileInput.println(formattedDate + inventoryMap.get(itemCode).getName() + itemCode + Math.floor(inventoryMap.get(itemCode).getPrice() * quantityToRemove) + " $" + cashBox.getBalance());
        return (quantityToRemove + " " + inventoryMap.get(itemCode).getName() + " Price: $" + inventoryMap.get(itemCode).getPrice() + " Balance: $" + cashBox.getBalance() + "\n" + inventoryMap.get(itemCode).getEatingNoise() + "\n");
    }
}
