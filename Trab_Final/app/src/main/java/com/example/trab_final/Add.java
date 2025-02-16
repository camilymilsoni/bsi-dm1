package com.example.trab_final;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.Calendar;

public class Add extends Activity {

    private EditText edtTitulo, edtDescricao;
    private Spinner spCategoria;
    private String categoriaSelecionada;
    private TextView textoDataHora;
    private ImageView btnDataHora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add);

        edtTitulo = (EditText)findViewById(R.id.titulo);
        edtDescricao = (EditText)findViewById(R.id.descricao);
        spCategoria = (Spinner)findViewById(R.id.spinnerCategoria);
        textoDataHora = (TextView)findViewById(R.id.txtDataHora);
        btnDataHora = (ImageView)findViewById(R.id.btnDataHora);
        Button btnSalvar = (Button)findViewById(R.id.btnSalvar);
        Button btnVoltar = (Button)findViewById(R.id.btnVoltar);

        String[] categorias = {"Trabalho", "Pessoal", "Estudos", "Outros"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categorias);
        spCategoria.setAdapter(adaptador);
        spCategoria.setOnItemSelectedListener(new TrataItemSelec());

        btnDataHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarDataHora();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarNota();
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voltarParaPrincipal();
            }
        });
    }

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

    private void selecionarDataHora() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    calendar.set(Calendar.YEAR, selectedYear);
                    calendar.set(Calendar.MONTH, selectedMonth);
                    calendar.set(Calendar.DAY_OF_MONTH, selectedDay);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                            (timeView, selectedHour, selectedMinute) -> {
                                calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                                calendar.set(Calendar.MINUTE, selectedMinute);

                                String dataHora = String.format("%02d/%02d/%d %02d:%02d",
                                        selectedDay, selectedMonth + 1, selectedYear,
                                        selectedHour, selectedMinute);
                                textoDataHora.setText(dataHora);
                            }, hour, minute, true);
                    timePickerDialog.show();
                }, year, month, day);
        datePickerDialog.show();
    }

    private void salvarNota() {
        String titulo = edtTitulo.getText().toString().trim();
        String descricao = edtDescricao.getText().toString().trim();
        String dataHora = textoDataHora.getText().toString().trim();

        if (!titulo.isEmpty() && !descricao.isEmpty() && categoriaSelecionada != null && !dataHora.isEmpty()) {
            SharedPreferences prefs = getSharedPreferences("NotasPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            int contadorNotas = prefs.getInt("contadorNotas", 0);
            String chaveBase = "nota_" + contadorNotas;

            editor.putString(chaveBase + "_titulo", titulo);
            editor.putString(chaveBase + "_categoria", categoriaSelecionada);
            editor.putString(chaveBase + "_descricao", descricao);
            editor.putString(chaveBase + "_dataHora", dataHora);

            editor.putInt("contadorNotas", contadorNotas + 1);
            editor.apply();
            Toast.makeText(this, "Nota adicionada!", Toast.LENGTH_SHORT).show();
            voltarParaPrincipal();
        } else {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
        }
    }

    private void voltarParaPrincipal() {
        Intent intent = new Intent(Add.this, Principal.class);
        startActivity(intent);
        finish();
    }
}