package com.example.mjunior.fiapimoveis.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mjunior.fiapimoveis.modelo.Imovel;

import java.util.ArrayList;
import java.util.List;

public class ImovelDAO extends SQLiteOpenHelper {

    public ImovelDAO(Context context) {
        super(context, "Imovel", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Imoveis (id INTEGER PRIMARY KEY, contato TEXT NOT NULL, endereco TEXT, tipo TEXT, nota REAL);";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Imoveis";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(Imovel imovel) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoImovel(imovel);

        db.insert("Imoveis", null, dados);
    }



    public List<Imovel> buscaImoveis() {
        String sql = "SELECT * FROM Imoveis;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Imovel> imoveis = new ArrayList<Imovel>();
        while (c.moveToNext()) {
            Imovel imovel = new Imovel();
            imovel.setId(c.getLong(c.getColumnIndex("id")));
            imovel.setContato(c.getString(c.getColumnIndex("contato")));
            imovel.setEndereco(c.getString(c.getColumnIndex("endereco")));
            imovel.setTipo(c.getString(c.getColumnIndex("tipo")));
            imovel.setNota(c.getDouble(c.getColumnIndex("nota")));

            imoveis.add(imovel);
        }
        c.close();
        return imoveis;
    }

    public void deletaImovel(Imovel imovel) {
        SQLiteDatabase db = getReadableDatabase();

        String[] params = {imovel.getId().toString()};
        db.delete("Imoveis", "id = ?", params);
    }

    public void altera(Imovel imovel) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoImovel(imovel);

        String[] params = {imovel.getId().toString()};
        db.update("Imoveis", dados, "id = ?", params);
    }

    public ContentValues pegaDadosDoImovel(Imovel imovel) {
        ContentValues dados = new ContentValues();
        dados.put("contato", imovel.getContato());
        dados.put("endereco", imovel.getEndereco());
        dados.put("tipo", imovel.getTipo());
        dados.put("nota", imovel.getNota());

        return dados;
    }
}
