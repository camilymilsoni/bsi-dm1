package com.example.trab_final_armint;

import android.app.Activity;
import android.content.Intent;
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
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Principal extends Activity {

    private List<Nota> notas; // Lista que armazena as notas
    private NotaAdapter adapter; // Adapter para exibir as notas na ListView
    String FILENAME = "notas.txt"; // Nome do arquivo onde as notas são armazenadas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_principal);

        // Inicializa os componentes da interface
        ListView lista = (ListView)findViewById(R.id.listaNotas);
        Button btnAddNota = (Button)findViewById(R.id.btnNovaNota);

        // Carrega as notas salvas no arquivo e configura o adapter para a ListView
        notas = carregarNotas();
        adapter = new NotaAdapter(notas);
        lista.setAdapter(adapter);

        // Registra o menu de contexto para a ListView
        registerForContextMenu(lista);

        // Configura o botão para adicionar nova nota
        btnAddNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Principal.this, Add.class);
                startActivity(intent);
            }
        });
    }

    // Cria o menu de contexto quando usuário pressiona longamente um item
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflado = getMenuInflater();
        inflado.inflate(R.menu.context_menu, menu);
    }

    // Trata a seleção de itens do menu de contexto
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

    // Carrega as notas do arquivo para a lista
    // Cada nota é armazenada em uma linha no formato: titulo|categoria|descricao|dataHora
    private List<Nota> carregarNotas() {
        List<Nota> notas = new ArrayList<>();
        try {
            FileInputStream fis = openFileInput(FILENAME); // Abre o arquivo
            byte[] dados = new byte[fis.available()];
            fis.read(dados);
            fis.close();

            String conteudo = new String(dados); // Converte os dados para uma string
            String[] linhas = conteudo.split("\n"); // Divide o conteúdo por linhas

            // Processa cada linha para criar objetos Nota
            for (String linha : linhas) {
                if (!linha.isEmpty()) {
                    String[] campos = linha.split("\\|"); // Divide os campos por "|"
                    if (campos.length == 4) {
                        notas.add(new Nota(campos[0], campos[1], campos[2], campos[3]));
                    }
                }
            }
        } catch (FileNotFoundException erro1) {
            // Arquivo ainda não existe, então a lista de notas começa vazia
        }
          catch (IOException erro2) {
            erro2.printStackTrace(); // Exibe o erro caso ocorra
        }
        return notas;
    }

    // Salva a lista de notas no arquivo
    private void salvarNotas() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, MODE_PRIVATE); // Abre o arquivo para escrita
            StringBuilder conteudo = new StringBuilder();

            // Concatena todas as notas em uma string formatada
            for (Nota nota : notas) {
                String linha = String.format("%s|%s|%s|%s\n",
                        nota.getTitulo(),
                        nota.getCategoria(),
                        nota.getDescricao(),
                        nota.getDataHora());
                conteudo.append(linha);
            }

            fos.write(conteudo.toString().getBytes()); // Escreve no arquivo
            fos.close();
        } catch (FileNotFoundException erro1) {
            erro1.printStackTrace();
        } catch (IOException erro2) {
            erro2.printStackTrace();
        }
    }

    // Remove uma nota da lista e salva alteração no arquivo
    private void excluirNota(int position) {
        notas.remove(position); // Remove a nota da posição especificada
        adapter.notifyDataSetChanged(); // Atualiza a ListView
        salvarNotas(); // Salva as alterações no arquivo
    }

    // Recarrega as notas quando a atividade volta ao primeiro plano
    // Isso garante que novas notas sejam exibidas após serem criadas
    @Override
    protected void onResume() {
        super.onResume();
        notas.clear(); // Limpa a lista atual
        notas.addAll(carregarNotas()); // Carrega as notas novamente
        adapter.notifyDataSetChanged(); // Atualiza a ListView
    }

    // Classe que representa uma nota
    // Implementada como classe interna estática para encapsulamento
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

    // Adapter personalizado para exibir as notas na ListView
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

        // Cria ou reutiliza uma view para exibir uma nota
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Reutiliza view existente ou infla uma nova
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.layout_item_lista, parent, false);
            }

            // Obtém a nota para a posição atual
            Nota nota = dados.get(position);

            // Encontra as views do layout
            TextView titulo = convertView.findViewById(R.id.item_titulo);
            TextView categoria = convertView.findViewById(R.id.item_categoria);
            TextView descricao = convertView.findViewById(R.id.item_descricao);
            TextView dataHora = convertView.findViewById(R.id.item_datahora);

            // Preenche os dados da nota nas views
            titulo.setText(nota.getTitulo());
            categoria.setText(nota.getCategoria());
            descricao.setText(nota.getDescricao());
            dataHora.setText(nota.getDataHora());

            return convertView; // Retorna a View configurada para o item
        }
    }
}