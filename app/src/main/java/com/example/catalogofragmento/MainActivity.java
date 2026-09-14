package com.example.catalogofragmento;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View;
import android.widget.AdapterView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity
        implements FragmentLista.OnArticuloSeleccionadoListener,
                   FragmentoDetalle.OnArticuloCompradoListener,
                   FragmentoDetalle.OnFavoritoCambiadoListener {

    private EditText etBuscador;
    private Spinner spCategorias;

    private String filtroTexto = "";
    private String filtroCategoria = "Todos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Configurar buscador
        etBuscador = findViewById(R.id.etBuscador);
        etBuscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtroTexto = s.toString();
                aplicarFiltros();
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        // Configurar spinner de categorías
        spCategorias = findViewById(R.id.spCategorias);
        String[] categorias = {"Todos", "Favoritos", "Computadoras", "Accesorios", "Audio", "Almacenamiento"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, categorias);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategorias.setAdapter(spinnerAdapter);

        spCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filtroCategoria = categorias[position];
                aplicarFiltros();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void aplicarFiltros() {
        FragmentLista listaFragment = (FragmentLista) getSupportFragmentManager()
                .findFragmentById(R.id.contenedorLista);

        if (listaFragment != null) {
            listaFragment.filtrar(filtroTexto, filtroCategoria);
        }
    }

    @Override
    public void onArticuloSeleccionado(Articulo articulo) {
        FragmentoDetalle detalleFragment = (FragmentoDetalle) getSupportFragmentManager()
                .findFragmentById(R.id.contenedorDetalle);

        if (detalleFragment != null) {
            detalleFragment.mostrarArticulo(articulo);
        }
    }

    @Override
    public void onArticuloComprado(Articulo articulo, int cantidad) {
        FragmentCarrito carritoFragment = (FragmentCarrito) getSupportFragmentManager()
                .findFragmentById(R.id.contenedorCarrito);

        if (carritoFragment != null) {
            carritoFragment.agregarItem(articulo, cantidad);
        }
    }

    @Override
    public void onFavoritoCambiado(Articulo articulo) {
        aplicarFiltros();
    }
}