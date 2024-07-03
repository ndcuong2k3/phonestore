package com.example.phonestoreapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.example.phonestoreapplication.common.HandleFileUploadListener;
import com.example.phonestoreapplication.Model.Product;
import com.example.phonestoreapplication.Reference.Reference;
import com.example.phonestoreapplication.services.FirebaseStorageUploader;
import com.google.firebase.database.ValueEventListener;

public class ThemSanPhamMoi extends AppCompatActivity {
    private final Reference reference = new Reference();
    private final DatabaseReference products_ref = reference.getProducts();
    private Button btn_action;

    private EditText edit_text_product_name, edit_text_product_price, edit_text_product_quantity, edit_text_product_description;
    private ImageView image_view_product_image;

    private String buff_image = "";

    private boolean is_updated_image = false;

    private boolean isUpdateProduct;

    private FirebaseStorageUploader uploader = new FirebaseStorageUploader(this);

    ImageButton image_button_home;

    private HandleFileUploadListener handle_upload = new HandleFileUploadListener() {
        @Override
        public void on_upload_success(String download_url) {
            buff_image = download_url;
            is_updated_image = true;
            //saveProductToFirebase();
        }

        @Override
        public void on_upload_failure(String error_message) {
            Toast.makeText(ThemSanPhamMoi.this, "Image upload failed: " + error_message, Toast.LENGTH_SHORT).show();
        }
    };

    private String product_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham_moi);

        mapping_client();

        product_id = getIntent().getStringExtra("product_id");
        isUpdateProduct = product_id != null;

//        if (!isUpdateProduct) {
//            btn_action.setText("Thêm");
//        } else {
//            btn_action.setText("Lưu");
//
//            products_ref.child(product_id).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot data_snapshot) {
//                    Product product = data_snapshot.getValue(Product.class);
//
//                    edit_text_product_name.setText(product.getName());
//                    edit_text_product_price.setText(Float.toString(product.getPrice()));
//                    edit_text_product_quantity.setText(Integer.toString(product.getWarehouse()));
//                    edit_text_product_description.setText(product.getDescription());
//                    Glide.with(ThemSanPhamMoi.this).load(product.getImage()).into(image_view_product_image);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                }
//            });
//        }

        btn_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProductToFirebase();
            }
        });

        // Declare to pick an image
        ActivityResultLauncher<PickVisualMediaRequest> pick_media = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                Glide.with(ThemSanPhamMoi.this).load(uri).into(image_view_product_image);
                uploader.upload_file(uri, handle_upload);
            } else {
                Log.d("PhotoPicker", "No media selected");
            }
        });

        // Click to choose image
        image_view_product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pick_media.launch(new PickVisualMediaRequest.Builder().setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE).build());
            }
        });
    }



    private void saveProductToFirebase() {
        String name = edit_text_product_name.getText().toString().trim();
        float price = Float.parseFloat(edit_text_product_price.getText().toString().trim());
        int warehouse = Integer.parseInt(edit_text_product_quantity.getText().toString().trim());
        String description = edit_text_product_description.getText().toString().trim();
        String newProductId = products_ref.push().getKey();
        Product newProduct = new Product(newProductId, name, description, 0, warehouse, price, 0.0f, buff_image, "", "", "");
        products_ref.child(newProductId).setValue(newProduct);
        Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show();

    }

    private void mapping_client() {
        this.btn_action = findViewById(R.id.btn_action);
        this.edit_text_product_name = findViewById(R.id.editTextProductName);
        this.edit_text_product_price = findViewById(R.id.editTextPrice);
        this.edit_text_product_quantity = findViewById(R.id.editTextQuantity);
        this.edit_text_product_description = findViewById(R.id.editTextDescription);
        this.image_view_product_image = findViewById(R.id.imageView);
        this.image_button_home = findViewById(R.id.button1);
    }
}
