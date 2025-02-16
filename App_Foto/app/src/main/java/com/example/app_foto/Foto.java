package com.example.app_foto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class Foto extends Activity {

    private int[] fotosCachorro = {
            R.drawable.foto1_cachorro, R.drawable.foto2_cachorro,
            R.drawable.foto3_cachorro, R.drawable.foto4_cachorro,
            R.drawable.foto5_cachorro
    };

    private int[] fotosGato = {
            R.drawable.foto1_gato, R.drawable.foto2_gato,
            R.drawable.foto3_gato, R.drawable.foto4_gato,
            R.drawable.foto5_gato
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_foto);

        TextView tv = (TextView)findViewById(R.id.nome);
        ImageView iv = (ImageView)findViewById(R.id.imagem);
        Intent i = getIntent();
        String nome = i.getStringExtra("nome");
        String categoria = i.getStringExtra("categoria");

        tv.setText(nome);

        int fotoAleatoria;
        Random random = new Random();
        if ("Cachorro".equals(categoria)) {
            int randomIndex = random.nextInt(fotosCachorro.length);
            fotoAleatoria = fotosCachorro[randomIndex];
        } else {
            int randomIndex = random.nextInt(fotosGato.length);
            fotoAleatoria = fotosGato[randomIndex];
        }
        iv.setImageResource(fotoAleatoria);

        Button btn = (Button)findViewById(R.id.botao_voltar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostraTelaPrincipal();
            }
        });
    }

    public void mostraTelaPrincipal() {
        Intent i = new Intent(this, Principal.class);
        startActivity(i);
    }
}