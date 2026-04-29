package com.example.lostfound;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lostfound.data.AppDatabase;
import com.example.lostfound.data.Item;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateAdvert extends AppCompatActivity {

    public void backClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    Button saveBtn;
    EditText nameTxt;
    EditText phoneTxt;
    EditText descriptionTxt;
    EditText dateTxt;
    EditText locationTxt;
    ToggleButton lostFoundToggleButton;
    Button uploadImageBtn;
    ImageView imagePreview;
    String imageUriString = "";
    Spinner categorySpinner;

    ActivityResultLauncher<String> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(uri);
                        File imageFile = new File(getFilesDir(), "img_" + System.currentTimeMillis() + ".jpg");
                        FileOutputStream outputStream = new FileOutputStream(imageFile);

                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = inputStream.read(buffer)) > 0) {
                            outputStream.write(buffer, 0, length);
                        }

                        outputStream.close();
                        inputStream.close();

                        imageUriString = imageFile.getAbsolutePath();
                        imagePreview.setImageBitmap(BitmapFactory.decodeFile(imageUriString));

                    } catch (Exception e) {
                        Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private boolean isEmpty(String value, String fieldName) {
        if (value.isEmpty()) {
            Toast.makeText(this, "Please enter" + fieldName, Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_advert);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AppDatabase db = AppDatabase.getInstance(this);

        saveBtn = findViewById(R.id.saveBtn);
        nameTxt = findViewById(R.id.nameTxt);
        phoneTxt = findViewById(R.id.phoneTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        dateTxt = findViewById(R.id.dateTxt);
        locationTxt = findViewById(R.id.locationTxt);
        lostFoundToggleButton = findViewById(R.id.lostFoundToggleButton);
        uploadImageBtn = findViewById(R.id.uploadImageBtn);
        imagePreview = findViewById(R.id.imagePreview);
        categorySpinner = findViewById(R.id.categorySpinner);

        String[] categories = {
                "Electronics",
                "Clothing",
                "Jewellery",
                "Documents",
                "Bags",
                "Accessories",
                "Other"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);


        uploadImageBtn.setOnClickListener(v -> {
            imagePickerLauncher.launch("image/*");
        });

        saveBtn.setOnClickListener(v -> {
            String type;
            if (lostFoundToggleButton.isChecked()){
                type = "FOUND";
            } else {
                type = "LOST";
            }

            String category = categorySpinner.getSelectedItem().toString();
            String name = nameTxt.getText().toString();
            String phone = phoneTxt.getText().toString();
            String description = descriptionTxt.getText().toString();
            String date = dateTxt.getText().toString();
            String location = locationTxt.getText().toString();

            if (isEmpty(name, "a name")) return;
            if (isEmpty(phone, "a phone")) return;
            if (isEmpty(description, "a description")) return;
            if (isEmpty(date, "a date")) return;
            if (isEmpty(location, "a location")) return;
            if (isEmpty(imageUriString, "an image")) return;

            String timeStamp = new SimpleDateFormat(
                    "dd/MM/yyyy HH:mm",
                    Locale.getDefault()
            ).format(new Date());

            Item item = new Item(type, name, phone, description, date, timeStamp, location, imageUriString, category);
            db.itemDao().insert(item);

            Toast.makeText(this, "Item Saved!", Toast.LENGTH_SHORT).show();

            nameTxt.setText("");
            phoneTxt.setText("");
            descriptionTxt.setText("");
            dateTxt.setText("");
            locationTxt.setText("");
        });
    }


}