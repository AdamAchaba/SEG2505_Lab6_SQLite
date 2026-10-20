package com.example.sqlitepractice;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FeedReaderDbHelper dbHelper;
    private EditText editTitle;
    private EditText editSubtitle;
    private TextView textDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new FeedReaderDbHelper(this);

        editTitle = findViewById(R.id.editTitle);
        editSubtitle = findViewById(R.id.editSubtitle);
        textDisplay = findViewById(R.id.textDisplay);

        Button btnInsert = findViewById(R.id.btnInsert);
        Button btnRead = findViewById(R.id.btnRead);
        Button btnDelete = findViewById(R.id.btnDelete);

        // Bouton AJOUTER
        btnInsert.setOnClickListener(v -> {
            String title = editTitle.getText().toString().trim();
            String subtitle = editSubtitle.getText().toString().trim();

            if (title.isEmpty() || subtitle.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir le titre et le sous-titre.", Toast.LENGTH_SHORT).show();
                return;
            }

            long rowId = dbHelper.insertEntry(title, subtitle);
            if (rowId != -1) {
                Toast.makeText(this, "Entrée insérée avec succès (ID: " + rowId + ")", Toast.LENGTH_SHORT).show();
                editTitle.setText("");
                editSubtitle.setText("");
                displayEntries();
            } else {
                Toast.makeText(this, "Erreur lors de l'insertion.", Toast.LENGTH_SHORT).show();
            }
        });

        // Bouton AFFICHER
        btnRead.setOnClickListener(v -> displayEntries());

        // Bouton SUPPRIMER
        btnDelete.setOnClickListener(v -> {
            String title = editTitle.getText().toString().trim();
            if (title.isEmpty()) {
                Toast.makeText(this, "Entrez le titre de l'élément à supprimer.", Toast.LENGTH_SHORT).show();
                return;
            }

            int deletedRows = dbHelper.deleteEntry(title);
            if (deletedRows > 0) {
                Toast.makeText(this, deletedRows + " entrée(s) supprimée(s).", Toast.LENGTH_SHORT).show();
                editTitle.setText("");
                editSubtitle.setText("");
                displayEntries();
            } else {
                Toast.makeText(this, "Aucune entrée trouvée avec ce titre.", Toast.LENGTH_SHORT).show();
            }
        });

        // Affichage initial
        displayEntries();
    }

    private void displayEntries() {
        List<String> entries = dbHelper.getAllEntries();
        if (entries.isEmpty()) {
            textDisplay.setText("La base de données est actuellement vide.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (String entry : entries) {
                sb.append(entry).append("\n\n");
            }
            textDisplay.setText(sb.toString());
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
