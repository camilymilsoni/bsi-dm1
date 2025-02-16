package com.example.app_receitas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Receita extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_receita);

        Intent intent = getIntent();
        String receitaNome = intent.getStringExtra("receita");

        TextView nomeReceita = (TextView) findViewById(R.id.nome_receita);
        ImageView imagemReceita = (ImageView) findViewById(R.id.imagem_receita);
        TextView ingredientesReceita = (TextView) findViewById(R.id.ingredientes);
        TextView modoPreparoReceita = (TextView)findViewById(R.id.modo_preparo);
        Button btnVoltar = (Button)findViewById(R.id.botao_voltar);

        switch (receitaNome) {
            case "Bolo de Cenoura":
                nomeReceita.setText("Bolo de Cenoura");
                imagemReceita.setImageResource(R.drawable.foto_bolo);
                ingredientesReceita.setText("" +
                        "• 3 cenouras médias;\n" +
                        "• 4 ovos;\n" +
                        "• 2 xícaras de açúcar;\n" +
                        "• 2 xícaras de farinha de trigo;\n" +
                        "• 1 xícara de óleo;\n" +
                        "• 1 colher (sopa) de fermento em pó.");
                modoPreparoReceita.setText("" +
                        "1. Bata as cenouras, ovos e óleo no liquidificador;\n" +
                        "2. Misture os ingredientes secos e adicione a mistura do liquidificador;\n" +
                        "3. Asse em forno pré-aquecido a 180ºC por cerca de 40 minutos.");
                break;

            case "Mousse de Maracujá":
                nomeReceita.setText("Mousse de Maracujá");
                imagemReceita.setImageResource(R.drawable.foto_mousse);
                ingredientesReceita.setText("" +
                        "• 1 caixa de leite condensado;\n" +
                        "• 1 caixa de creme de leite;\n" +
                        "• 1 xícara de suco de maracujá.");
                modoPreparoReceita.setText("" +
                        "1. Bata todos os ingredientes no liquidificador até ficar homogêneo;\n" +
                        "2. Coloque na geladeira por, no mínimo, 2 horas antes de servir.");
                break;

            case "Macarrão à Carbonara":
                nomeReceita.setText("Macarrão à Carbonara");
                imagemReceita.setImageResource(R.drawable.foto_macarrao);
                ingredientesReceita.setText("" +
                        "• 200g de macarrão;\n" +
                        "• 100g de bacon;\n" +
                        "• 2 ovos;\n" +
                        "• 50g de queijo parmesão;\n" +
                        "• Sal e pimenta a gosto.");
                modoPreparoReceita.setText("" +
                        "1. Cozinhe o macarrão;\n" +
                        "2. Frite o bacon;\n" +
                        "3. Misture os ovos com o queijo e pimenta;\n" +
                        "4. Misture tudo e sirva.");
                break;

            case "Frango à Parmegiana":
                nomeReceita.setText("Frango à Parmegiana");
                imagemReceita.setImageResource(R.drawable.foto_frango);
                ingredientesReceita.setText("" +
                        "• 2 filés de frango;\n" +
                        "• 2 ovos;\n" +
                        "• Farinha de rosca para empanar;\n" +
                        "• Queijo e molho de tomate.");
                modoPreparoReceita.setText("" +
                        "1. Tempere e empane os filés de frango;\n" +
                        "2. Frite os filés;\n" +
                        "3. Cubra com molho e queijo e leve ao forno até gratinar.");
                break;
        }

        btnVoltar.setOnClickListener(new View.OnClickListener() {
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