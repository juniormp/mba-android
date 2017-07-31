package com.example.mjunior.fiapimoveis;

import android.widget.EditText;
import android.widget.RatingBar;

import com.example.mjunior.fiapimoveis.modelo.Imovel;

public class FormularioHelper {

    private final EditText campoContato;
    private final EditText campoEndereco;
    private final EditText campoTipo;
    private final RatingBar campoNota;
    private Imovel imovel;

    public FormularioHelper(FormActivity activity){
        campoContato = (EditText) activity.findViewById(R.id.formulario_contato);
        campoEndereco = (EditText) activity.findViewById(R.id.formulario_endereco);
        campoTipo = (EditText) activity.findViewById(R.id.formulario_tipo);
        campoNota = (RatingBar) activity.findViewById(R.id.formulario_nota);
        imovel = new Imovel();
    }

    public Imovel pegaImovel(){
        imovel.setContato(campoContato.getText().toString());
        imovel.setEndereco(campoEndereco.getText().toString());
        imovel.setTipo(campoTipo.getText().toString());
        imovel.setNota(Double.valueOf(campoNota.getProgress()));

        return imovel;
    }

    public void preencheFormulario(Imovel imovel) {
        campoContato.setText(imovel.getContato());
        campoEndereco.setText(imovel.getEndereco());
        campoTipo.setText(imovel.getTipo());
        campoNota.setProgress(imovel.getNota().intValue());
        this.imovel = imovel;
    }
}
