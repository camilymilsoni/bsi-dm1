package com.example.layout_app2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Principal extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_principal);

        ImageButton btn = (ImageButton)findViewById(R.id.imagemBotao);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostraMensagem();
            }
        });

        LinearLayout layoutPrincipal = (LinearLayout) findViewById(R.id.layout_principal);
        HandlerToque toq = new HandlerToque();
        layoutPrincipal.setOnTouchListener(toq);

        EditText edtNome = (EditText)findViewById(R.id.campoNome);
        EditText edtRegiao = (EditText)findViewById(R.id.campoRegiao);
        HandlerFocoNome focoNome = new HandlerFocoNome();
        HandlerFocoRegiao focoRegiao = new HandlerFocoRegiao();
        edtNome.setOnFocusChangeListener(focoNome);
        edtRegiao.setOnFocusChangeListener(focoRegiao);
    }

    public void mostraMensagem(){
        Intent i = new Intent(this, Mensagem.class);
        startActivity(i);
    }

    public void notificacao(String mensagem) {
        Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_SHORT).show();
    }

    class HandlerToque implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            notificacao("Clique em um dos componentes!");
            view.performClick();
            return true;
        }
    }

    class HandlerFocoNome implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View view, boolean b) {
            if(b)
                notificacao("Nome recebeu foco");
            else
                notificacao("Nome perdeu foco");
        }
    }

    class HandlerFocoRegiao implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View view, boolean b) {
            if(b)
                notificacao("Região recebeu foco");
            else
                notificacao("Região perdeu foco");
        }
    }
}
