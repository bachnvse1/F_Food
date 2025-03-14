package com.example.f_food.Repository;

import android.content.Context;
import com.example.f_food.DAO.ShipperDAO;
import com.example.f_food.DAO.RestaurantRoomDatabase;
import com.example.f_food.Entity.Shipper;
import java.util.Arrays;
import java.util.List;

public class ShipperRepository {
    private ShipperDAO shipperDAO;

    public ShipperRepository(Context context) {
        RestaurantRoomDatabase db = RestaurantRoomDatabase.getInstance(context);
        shipperDAO = db.shipperDAO();

        // Kiểm tra nếu chưa có dữ liệu, thì thêm dữ liệu mẫu
        if (shipperDAO.getAllShippers().isEmpty()) {
            insertSampleData();
        }
    }

    public List<Shipper> getAllShippers() {
        return shipperDAO.getAllShippers();
    }

    public Shipper getShipperById(int id) {
        return shipperDAO.getShipperById(id);
    }

    public List<Shipper> getShippersByStatus(String status) {
        return shipperDAO.getShippersByStatus(status);
    }

    public void deleteById(int id) {
        shipperDAO.deleteById(id);
    }
public  void getShipperByUserId(int userid){
        shipperDAO.getShipperByUserId(userid);
}
    public void insert(Shipper shipper) {
        shipperDAO.insert(shipper);
    }

    public void insertAll(List<Shipper> shipperList) {
        shipperDAO.insertAll(shipperList);
    }

    public void update(Shipper shipper) {
        shipperDAO.update(shipper);
    }

    private void insertSampleData() {
        List<Shipper> sampleShippers = Arrays.asList(
                new Shipper(1, 101, "Active"),
                new Shipper(2, 102, "Inactive"),
                new Shipper(3, 103, "Pending")
        );

        for (Shipper shipper : sampleShippers) {
            shipperDAO.insert(shipper);
        }
    }
}
