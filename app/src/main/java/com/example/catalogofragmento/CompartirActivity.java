package com.example.catalogofragmento;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CompartirActivity extends AppCompatActivity {

    private String nombre = "";
    private String descripcion = "";
    private double precio = 0.0;
    private int imagen = 0;
    private String marca = "";
    private String categoria = "";
    private boolean favorito = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_compartir);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.compartir_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtener datos del intent
        Intent intent = getIntent();
        if (intent != null) {
            nombre = intent.getStringExtra("nombre");
            if (nombre == null) nombre = "";
            descripcion = intent.getStringExtra("descripcion");
            if (descripcion == null) descripcion = "";
            precio = intent.getDoubleExtra("precio", 0.0);
            imagen = intent.getIntExtra("imagen", 0);
            marca = intent.getStringExtra("marca");
            if (marca == null) marca = "";
            categoria = intent.getStringExtra("categoria");
            if (categoria == null) categoria = "";
            favorito = intent.getBooleanExtra("favorito", false);
        }

        // Vincular vistas
        ImageButton btnVolver = findViewById(R.id.btnVolver);
        ImageView ivCompartirImagen = findViewById(R.id.ivCompartirImagen);
        TextView tvCompartirCategoria = findViewById(R.id.tvCompartirCategoria);
        TextView tvCompartirMarca = findViewById(R.id.tvCompartirMarca);
        TextView tvCompartirNombre = findViewById(R.id.tvCompartirNombre);
        TextView tvCompartirPrecio = findViewById(R.id.tvCompartirPrecio);
        TextView tvCompartirDescripcion = findViewById(R.id.tvCompartirDescripcion);
        Button btnEnviar = findViewById(R.id.btnEnviar);

        // Asignar datos
        tvCompartirNombre.setText(nombre);
        tvCompartirDescripcion.setText(descripcion);
        tvCompartirPrecio.setText(String.format("$%,.2f", precio));
        if (favorito) {
            tvCompartirPrecio.setTextColor(ContextCompat.getColor(this, R.color.amarillo_favorito));
        }
        tvCompartirCategoria.setText(categoria.isEmpty() ? "General" : categoria);
        tvCompartirMarca.setText(marca.isEmpty() ? "Sin marca" : marca);

        if (imagen != 0) {
            ivCompartirImagen.setImageResource(imagen);
        }

        // Botón volver
        btnVolver.setOnClickListener(v -> finish());

        // Botón azul Enviar: lanza el selector nativo de compartir
        btnEnviar.setOnClickListener(v -> {
            String textoCompartir = nombre
                    + "\n" + descripcion
                    + "\nPrecio: $" + String.format("%,.2f", precio)
                    + "\nMarca: " + marca;

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, textoCompartir);
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, "Compartir artículo");
            startActivity(shareIntent);
        });
    }
}
