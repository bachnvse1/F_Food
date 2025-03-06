package com.example.f_food.DAO;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.f_food.Entity.Food;
import com.example.f_food.Entity.Policy;
import com.example.f_food.Entity.Restaurant;

@Database(entities = {Restaurant.class, Food.class, Policy.class}, version = 3, exportSchema = false)
public abstract class RestaurantRoomDatabase extends RoomDatabase {

    public abstract RestaurantDAO restaurantDAO();

    public abstract FoodDAO foodDAO();
    public  abstract  PolicyDAO policyDAO();

    private static volatile RestaurantRoomDatabase INSTANCE;

    public static RestaurantRoomDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (RestaurantRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    RestaurantRoomDatabase.class, "restaurant_database")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
