package com.stringhashing;
// package stringhashing.src.main.java.com.stringhashing;
import java.io.FileWriter;
import java.io.IOException;
import com.opencsv.CSVWriter;

public class CSVFileWriterExample {

    public static void main(String[] args) {
        String csvFileName = "example.csv";

        // Create a FileWriter object to write data to the CSV file
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFileName))) {
            // Define data to be written to the CSV file
            String[] header = {"Name", "Age", "Country"};
            String[] data1 = {"John Doe", "30", "USA"};
            String[] data2 = {"Jane Smith", "25", "Canada"};

            // Write the header and data to the CSV file
            writer.writeNext(header);
            writer.writeNext(data1);
            writer.writeNext(data2);

            System.out.println("CSV file created successfully: " + csvFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
