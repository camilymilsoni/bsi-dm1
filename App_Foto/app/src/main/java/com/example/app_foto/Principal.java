package com.example.app_foto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TextView;

public class Principal extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_principal);

        Button btn = (Button)findViewById(R.id.botao_gerar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostraTelaFoto();
            }
        });

        String[] nomes = {
                "Axé", "Arara", "Brahma", "Tuca", "Tamarindo", "Tucano"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
            this, android.R.layout.simple_dropdown_item_1line, nomes);
        AutoCompleteTextView at = (AutoCompleteTextView)findViewById(R.id.menu_nomes);
        at.setThreshold(1);
        at.setAdapter(adaptador);

        String[] fotos = {
                "Cachorro", "Gato"};

        Spinner sp = (Spinner)findViewById(R.id.lista_fotos);
        ArrayAdapter<String> adaptador2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, fotos);
        sp.setAdapter(adaptador2);

        TrataItemSelec handlerSpinner = new TrataItemSelec();
        handlerSpinner.inicializaAtributos(sp, fotos);
        sp.setOnItemSelectedListener(handlerSpinner);
    }

    public void mostraTelaFoto() {
        AutoCompleteTextView actv = findViewById(R.id.menu_nomes);
        String nome = actv.getText().toString();
        Spinner sp = findViewById(R.id.lista_fotos);
        String categoria = sp.getSelectedItem().toString();

        Intent i = new Intent(this, Foto.class);
        i.putExtra("nome", nome);
        i.putExtra("categoria", categoria);
        startActivity(i);
    }

    class TrataItemSelec implements AdapterView.OnItemSelectedListener {

        private Spinner sp;
        private String[] fotos;

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String selectedItem = fotos[i];
            Toast.makeText(getBaseContext(),
                    selectedItem,
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) { }

        public void inicializaAtributos (Spinner sp, String[] fotos) {
            this.sp = sp;
            this.fotos = fotos;
        }
    }
}