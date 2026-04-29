package com.example.lostfound;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostfound.data.AppDatabase;
import com.example.lostfound.data.Item;

import java.security.AllPermission;
import java.util.List;

public class LostFoundActivity extends AppCompatActivity {

    public void backClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    Spinner filterCategorySpinner;
    RecyclerView recyclerView;
    AppDatabase db;
    List<Item> allItems;
    SearchView searchView;
    String currentCategory = "All categories";
    String currentSearch = "";
    ItemAdapter recyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lost_found);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = AppDatabase.getInstance(this);
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        filterCategorySpinner = findViewById(R.id.filterCategorySpinner);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        allItems = db.itemDao().getAllItems();

        recyclerAdapter = new ItemAdapter(this, allItems);
        recyclerView.setAdapter(recyclerAdapter);

        ArrayAdapter<String> spinnerAdapter = getStringArrayAdapter();
        filterCategorySpinner.setAdapter(spinnerAdapter);

        filterCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentCategory = parent.getItemAtPosition(position).toString();
                applyFilters();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                currentSearch = newText;
                applyFilters();
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                currentSearch = query;
                applyFilters();
                return true;
            }
        });
    }
    private void applyFilters() {
        String searchQuery = currentSearch.isEmpty() ? "%%" : "%" + currentSearch + "%";

        List<Item> filteredItems = db.itemDao().getFilteredItems(
                searchQuery,
                currentCategory
        );

        recyclerAdapter.updateList(filteredItems);
    }
    @NonNull
    private ArrayAdapter<String> getStringArrayAdapter() {
        String[] categories = {
                "All categories",
                "Electronics",
                "Clothing",
                "Jewellery",
                "Documents",
                "Bags",
                "Accessories",
                "Other"
        };

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return spinnerAdapter;
    }
}