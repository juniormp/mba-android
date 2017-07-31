package com.example.mjunior.fiapimoveis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mjunior.fiapimoveis.dao.ImovelDAO;
import com.example.mjunior.fiapimoveis.modelo.Imovel;

import java.util.List;

public class ListaImoveisActivity extends AppCompatActivity {

    private ListView listaImoveis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_imoveis);

        listaImoveis = (ListView) findViewById(R.id.lista_imoveis);

        listaImoveis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Imovel imovel = (Imovel) listaImoveis.getItemAtPosition(position);

                Intent intentVaiProFormulario = new Intent(ListaImoveisActivity.this, FormActivity.class);
                intentVaiProFormulario.putExtra("imovel", imovel);
                startActivity(intentVaiProFormulario);
            }
        });

        Button novoImovel = (Button) findViewById(R.id.novo_imovel);
        novoImovel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVaiProFirmulario = new Intent(ListaImoveisActivity.this, FormActivity.class);
                startActivity(intentVaiProFirmulario);
            }
        });

        registerForContextMenu(listaImoveis);
    }

    private void carregaLista() {
        ImovelDAO imovelDAO = new ImovelDAO(this);
        List<Imovel> imoveis = imovelDAO.buscaImoveis();
        imovelDAO.close();

        ArrayAdapter<Imovel> adapter = new ArrayAdapter<Imovel>(this, android.R.layout.simple_list_item_1, imoveis);
        listaImoveis.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Imovel imovel = (Imovel) listaImoveis.getItemAtPosition(info.position);

                ImovelDAO imovelDAO = new ImovelDAO(ListaImoveisActivity.this);
                imovelDAO.deletaImovel(imovel);
                imovelDAO.close();

                Toast.makeText(ListaImoveisActivity.this, "Imóvel deletado !", Toast.LENGTH_SHORT).show();
                carregaLista();
                return false;
            }
        });
    }
}
