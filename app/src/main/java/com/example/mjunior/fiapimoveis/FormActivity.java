package com.example.mjunior.fiapimoveis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mjunior.fiapimoveis.dao.ImovelDAO;
import com.example.mjunior.fiapimoveis.modelo.Imovel;

public class FormActivity extends AppCompatActivity {

    private FormularioHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        helper = new FormularioHelper(this);

        Intent intent = getIntent();
        Imovel imovel = (Imovel) intent.getSerializableExtra("imovel");
        if (imovel != null){
            helper.preencheFormulario(imovel);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_ok:
                Imovel imovel = helper.pegaImovel();
                ImovelDAO imovelDAO = new ImovelDAO(this);

                if(imovel.getId() != null){
                    imovelDAO.altera(imovel);
                }else{
                    imovelDAO.insere(imovel);
                }
                imovelDAO.close();

                Toast.makeText(FormActivity.this, "Imóvel salvo!", Toast.LENGTH_SHORT).show();

                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
