package com.example.trab_final_armint;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.*;
import java.util.Calendar;

public class Add extends Activity {

    // Declaração de componentes e variáveis globais
    private EditText edtTitulo, edtDescricao;
    private Spinner spCategoria;
    private String categoriaSelecionada;
    private TextView textoDataHora;
    private ImageView btnDataHora;
    String FILENAME = "notas.txt"; // Nome do arquivo onde as notas são armazenadas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add);

        // Inicializa os componentes da interface
        edtTitulo = (EditText)findViewById(R.id.titulo);
        edtDescricao = (EditText)findViewById(R.id.descricao);
        spCategoria = (Spinner)findViewById(R.id.spinnerCategoria);
        textoDataHora = (TextView)findViewById(R.id.txtDataHora);
        btnDataHora = (ImageView)findViewById(R.id.btnDataHora);
        Button btnSalvar = (Button)findViewById(R.id.btnSalvar);
        Button btnVoltar = (Button)findViewById(R.id.btnVoltar);

        // Configuração do Spinner de categorias
        String[] categorias = {"Trabalho", "Pessoal", "Estudos", "Outros"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categorias);
        spCategoria.setAdapter(adaptador);
        spCategoria.setOnItemSelectedListener(new TrataItemSelec());

        // Configuração do botão para abrir o seletor de data e hora
        btnDataHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarDataHora();
            }
        });

        // Configuração do botão para salvar a nota
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarNota();
            }
        });

        // Configuração do botão para voltar à tela principal
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voltarParaPrincipal();
            }
        });
    }

    // Classe interna para tratar a seleção de itens no Spinner
    class TrataItemSelec implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            categoriaSelecionada = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            categoriaSelecionada = null;
        }
    }

    // Método para exibir os seletores de data e hora
    private void selecionarDataHora() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Exibe o DatePicker para selecionar a data
        new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            // Atualiza o calendário com a data selecionada
            calendar.set(Calendar.YEAR, selectedYear);
            calendar.set(Calendar.MONTH, selectedMonth);
            calendar.set(Calendar.DAY_OF_MONTH, selectedDay);

            // Exibe o TimePicker para selecionar a hora
            new TimePickerDialog(this, (timeView, selectedHour, selectedMinute) -> {
                // Atualiza o calendário com a hora selecionada
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                calendar.set(Calendar.MINUTE, selectedMinute);

                // Formata e exibe a data e hora selecionadas no TextView
                String dataHora = String.format("%02d/%02d/%d %02d:%02d",
                        selectedDay, selectedMonth + 1, selectedYear, selectedHour, selectedMinute);
                textoDataHora.setText(dataHora);

            }, hour, minute, true).show();

        }, year, month, day).show();
    }

    // Método para salvar uma nova nota no arquivo
    private void salvarNota() {
        // Obtém os valores dos campos
        String titulo = edtTitulo.getText().toString().trim();
        String descricao = edtDescricao.getText().toString().trim();
        String dataHora = textoDataHora.getText().toString().trim();

        // Verifica se todos os campos estão preenchidos
        if (!titulo.isEmpty() && !descricao.isEmpty() && categoriaSelecionada != null && !dataHora.isEmpty()) {
            try {
                String conteudoExistente = ""; // Lê o conteúdo existente do arquivo
                // Tenta abrir o arquivo para leitura (se existir)
                try {
                    FileInputStream fis = openFileInput(FILENAME);
                    byte[] dados = new byte[fis.available()];
                    fis.read(dados);
                    fis.close();
                    conteudoExistente = new String(dados);
                } catch (FileNotFoundException e) {
                    // Arquivo ainda não existe - conteúdo continua vazio
                }

                // Abre o arquivo para escrita
                FileOutputStream fos = openFileOutput(FILENAME, MODE_PRIVATE);
                // Formata a nova nota
                String novaNota = String.format("%s|%s|%s|%s\n",
                        titulo,
                        categoriaSelecionada,
                        descricao,
                        dataHora);

                // Concatena a nova nota ao conteúdo existente e escreve no arquivo
                String conteudoCompleto = conteudoExistente + novaNota;
                fos.write(conteudoCompleto.getBytes());
                fos.close();

                // Exibe uma mensagem de sucesso e retorna à tela principal
                Toast.makeText(this, "Nota adicionada!", Toast.LENGTH_SHORT).show();
                voltarParaPrincipal();
            } catch (IOException erro) {
                // Trata erros de escrita/leitura no arquivo
                erro.printStackTrace();
                Toast.makeText(this, "Erro ao salvar nota!", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Exibe uma mensagem de erro se houver campos vazios
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para retornar à tela principal
    private void voltarParaPrincipal() {
        Intent intent = new Intent(Add.this, Principal.class);
        startActivity(intent);
        finish();
    }
}