package com.example.databaseproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView idView;
    private EditText productBox;
    private EditText skuBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idView = findViewById(R.id.productID);
        productBox = findViewById(R.id.productName);
        skuBox = findViewById(R.id.productSku);
    }

    public void newProduct(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this);
        String name = productBox.getText().toString().trim();
        String skuText = skuBox.getText().toString().trim();

        if (name.isEmpty() || skuText.isEmpty()) {
            Toast.makeText(this, "Veuillez entrer un nom et un SKU.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int sku = Integer.parseInt(skuText);
            Product product = new Product(name, sku);
            dbHandler.addProduct(product);

            productBox.setText("");
            skuBox.setText("");
            idView.setText("Produit ajouté avec succès !");
            Toast.makeText(this, "Produit ajouté !", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Le SKU doit être un entier valide.", Toast.LENGTH_SHORT).show();
        }
    }

    public void lookupProduct(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this);
        String name = productBox.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Veuillez entrer le nom du produit à rechercher.", Toast.LENGTH_SHORT).show();
            return;
        }

        Product product = dbHandler.findProduct(name);

        if (product != null) {
            idView.setText(String.valueOf(product.getID()));
            skuBox.setText(String.valueOf(product.getSku()));
            Toast.makeText(this, "Produit trouvé !", Toast.LENGTH_SHORT).show();
        } else {
            idView.setText("Aucune correspondance trouvée");
            Toast.makeText(this, "Aucun résultat", Toast.LENGTH_SHORT).show();
        }
    }

    public void removeProduct(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this);
        String name = productBox.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Veuillez entrer le nom du produit à supprimer.", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean result = dbHandler.deleteProduct(name);

        if (result) {
            idView.setText("Enregistrement supprimé");
            productBox.setText("");
            skuBox.setText("");
            Toast.makeText(this, "Produit supprimé !", Toast.LENGTH_SHORT).show();
        } else {
            idView.setText("Aucune correspondance trouvée");
            Toast.makeText(this, "Suppression impossible : introuvable", Toast.LENGTH_SHORT).show();
        }
    }
}
