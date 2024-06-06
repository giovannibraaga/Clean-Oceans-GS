package com.oceans.pollution.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseConnection {

    public static Object[][] getPlasticCollectionData() {
        ArrayList<Object[]> dataList = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/marinedb", "postgres", "masterkey");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM plastic_collection");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String collectionDate = resultSet.getString("collection_date");
                String location = resultSet.getString("location");
                String plasticType = resultSet.getString("plastic_type");
                int quantity = resultSet.getInt("quantity");
                dataList.add(new Object[]{id, collectionDate, location, plasticType, quantity});
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Object[][] dataArray = new Object[dataList.size()][];
        return dataList.toArray(dataArray);
    }
}
