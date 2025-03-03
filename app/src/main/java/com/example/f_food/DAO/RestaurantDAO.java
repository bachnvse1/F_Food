package com.example.f_food.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.f_food.Entity.Restaurant;

import java.util.List;

@Dao
public interface RestaurantDAO {

    @Query("SELECT * FROM Restaurants")
    List<Restaurant> getAllRestaurants();

    @Query("SELECT * FROM Restaurants WHERE restaurant_id = :id")
    Restaurant getRestaurantById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Restaurant restaurant);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Restaurant> restaurants);

    @Update
    void update(Restaurant restaurant);

    @Delete
    void delete(Restaurant restaurant);

    @Query("DELETE FROM Restaurants WHERE restaurant_id = :id")
    void deleteById(int id);

    @Query("DELETE FROM Restaurants")
    void deleteAll();
}
