package com.example.app_receitas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Principal extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_principal);

        String[] receitas = {"Bolo de Cenoura", "Mousse de Maracujá", "Macarrão à Carbonara", "Frango à Parmegiana"};

        Spinner sp = findViewById(R.id.listaReceitas);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, receitas);
        sp.setAdapter(adapter);

        Button btnVerReceita = findViewById(R.id.btnReceita);
        btnVerReceita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostraTelaReceita();
            }
        });
    }

    public void mostraTelaReceita() {
        Spinner sp = findViewById(R.id.listaReceitas);
        String receitaSelecionada = sp.getSelectedItem().toString();

        Intent i = new Intent(this, Receita.class);
        i.putExtra("receita", receitaSelecionada);
        startActivity(i);
    }
}