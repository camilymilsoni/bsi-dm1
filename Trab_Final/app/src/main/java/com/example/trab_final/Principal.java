package com.example.trab_final;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class Principal extends Activity {

    private List<Nota> notas;
    private NotaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_principal);

        ListView lista = (ListView)findViewById(R.id.listaNotas);
        Button btnAddNota = (Button)findViewById(R.id.btnNovaNota);

        notas = carregarNotas();
        adapter = new NotaAdapter(notas);
        lista.setAdapter(adapter);

        registerForContextMenu(lista);

        btnAddNota.setOnClickListener(v -> {
            Intent intent = new Intent(Principal.this, Add.class);
            startActivity(intent);
        });
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflado = getMenuInflater();
        inflado.inflate(R.menu.context_menu, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        if (item.getItemId() == R.id.item_excluir) {
            excluirNota(position);
            Toast.makeText(Principal.this, "Nota excluída!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    private List<Nota> carregarNotas() {
        SharedPreferences prefs = getSharedPreferences("NotasPrefs", MODE_PRIVATE);
        int contadorNotas = prefs.getInt("contadorNotas", 0);

        List<Nota> notas = new ArrayList<>();

        for (int i = 0; i < contadorNotas; i++) {
            String chaveBase = "nota_" + i;

            String titulo = prefs.getString(chaveBase + "_titulo", null);
            String categoria = prefs.getString(chaveBase + "_categoria", null);
            String descricao = prefs.getString(chaveBase + "_descricao", null);
            String dataHora = prefs.getString(chaveBase + "_dataHora", null);

            if (titulo != null && categoria != null && descricao != null && dataHora != null) {
                notas.add(new Nota(titulo, categoria, descricao, dataHora));
            }
        }
        return notas;
    }

    private void salvarNotas() {
        SharedPreferences prefs = getSharedPreferences("NotasPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        int contadorNotas = prefs.getInt("contadorNotas", 0);
        for (int i = 0; i < contadorNotas; i++) {
            editor.remove("nota_" + i);
        }

        for (int i = 0; i < notas.size(); i++) {
            Nota nota = notas.get(i);
            editor.putString("nota_" + i + "_titulo", nota.getTitulo());
            editor.putString("nota_" + i + "_categoria", nota.getCategoria());
            editor.putString("nota_" + i + "_descricao", nota.getDescricao());
            editor.putString("nota_" + i + "_dataHora", nota.getDataHora());
        }

        editor.putInt("contadorNotas", notas.size());
        editor.apply();
    }

    private void excluirNota(int position) {
        notas.remove(position);
        adapter.notifyDataSetChanged();
        salvarNotas();
    }

    @Override
    protected void onResume() {
        super.onResume();
        notas.clear();
        notas.addAll(carregarNotas());
        adapter.notifyDataSetChanged();
    }

    private static class Nota {
        private final String titulo;
        private final String categoria;
        private final String descricao;
        private final String dataHora;

        public Nota(String titulo, String categoria, String descricao, String dataHora) {
            this.titulo = titulo;
            this.categoria = categoria;
            this.descricao = descricao;
            this.dataHora = dataHora;
        }

        public String getTitulo() {
            return titulo;
        }

        public String getCategoria() {
            return categoria;
        }

        public String getDescricao() {
            return descricao;
        }

        public String getDataHora() {
            return dataHora;
        }
    }

    private class NotaAdapter extends BaseAdapter {
        private final List<Nota> dados;

        public NotaAdapter(List<Nota> dados) {
            this.dados = dados;
        }

        @Override
        public int getCount() {
            return dados.size();
        }

        @Override
        public Object getItem(int position) {
            return dados.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.layout_item_lista, parent, false);
            }

            Nota nota = dados.get(position);

            TextView titulo = convertView.findViewById(R.id.item_titulo);
            TextView categoria = convertView.findViewById(R.id.item_categoria);
            TextView descricao = convertView.findViewById(R.id.item_descricao);
            TextView dataHora = convertView.findViewById(R.id.item_datahora);

            titulo.setText(nota.getTitulo());
            categoria.setText(nota.getCategoria());
            descricao.setText(nota.getDescricao());
            dataHora.setText(nota.getDataHora());

            return convertView;
        }
    }
}