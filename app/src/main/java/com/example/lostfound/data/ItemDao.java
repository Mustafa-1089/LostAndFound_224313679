package com.example.lostfound.data;

import android.media.metrics.Event;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import java.util.List;

@Dao
public interface ItemDao {
    @Insert
    void insert (Item item);

    @Query("SELECT * FROM events")
    List<Item> getAllItems();

    @Query("SELECT * FROM events WHERE " + "(name LIKE :search OR description LIKE :search OR location LIKE :search) AND " + "(:category = 'All categories' OR category = :category)")
    List<Item> getFilteredItems(String search, String category);

    @Delete
    void delete(Item item);
}
