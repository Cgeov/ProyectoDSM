package com.example.proyectocatedra.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.proyectocatedra.CardData;

import java.util.ArrayList;

public class DbRegistros extends DatabaseHelper{

    Context context;

    public DbRegistros(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    //Función que ingresa registros
    public long InsertReg(String nombre, String area, String tipo, String salB, String deducciones, String aguinaldo, String slNeto){
        //Instanciando la variable ID para poder retornarla
        long id = 0;

        try{
            //Instanciando la clase DatabaseHelper e instanciando SQLiteDatabase para
            //Que permita insertar datos en una tabla especifica
            DatabaseHelper dbHelper = new DatabaseHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            //Guardando todos los parametros en una instancia de la class ContentValues
            ContentValues values = new ContentValues();

            values.put("nombre", nombre);
            values.put("area", area);
            values.put("tipo", tipo);
            values.put("salario_base", salB);
            values.put("deducciones", deducciones);
            values.put("aguinaldo", aguinaldo);
            values.put("salario_neto", slNeto);

            //Llamando la función insert de SQLiteDatabase, como es dbRegistros es una clase Heredada
            //de DatabaseHelper, podemos invocar parametros de DatabaseHelper
            //Y se insertan todos los datos que se encuentren en values
            id = db.insert(TABLE_REGISTROS,null,values);

            //NOTA: db.insert devuelve el id generado al momento de ingresar el valor en la BD
            
        }
        catch (Exception ex){
            ex.toString();
        }


        return id;
    }

    public ArrayList<CardData> showReg(){

        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        ArrayList<CardData> listaRegistros = new ArrayList<>();
        CardData registro = null;
        Cursor cursorRegistros = null;

        cursorRegistros = db.rawQuery("SELECT id, salario_neto,deducciones,salario_base FROM "+ TABLE_REGISTROS +" ORDER BY id", null);

        if (cursorRegistros.moveToFirst()){
            do{
                registro = new CardData();
                registro.setId(cursorRegistros.getInt(0));
                registro.setnetSalary(cursorRegistros.getString(1));
                registro.setdeductions(cursorRegistros.getString(2));
                registro.setbaseSalary(cursorRegistros.getString(3));

                listaRegistros.add(registro);
            } while(cursorRegistros.moveToNext());
        }

        cursorRegistros.close();
        return listaRegistros;

    }

    public CardData seeReg(int id){

        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        CardData registro = null;
        Cursor cursorRegistros = null;

        cursorRegistros = db.rawQuery("SELECT*FROM "+ TABLE_REGISTROS +" WHERE id = " + id + " LIMIT 1", null);

        if (cursorRegistros.moveToFirst()){

                registro = new CardData();
                registro.setId(cursorRegistros.getInt(0));
                registro.setNombre(cursorRegistros.getString(1));
                registro.setArea(cursorRegistros.getString(2));
                registro.setTipo(cursorRegistros.getString(3));
                registro.setBaseSalary(cursorRegistros.getString(4));
        }

        cursorRegistros.close();
        return registro;
    }

    public boolean UpdateReg(int id, String nombre, String area, String tipo, String salB, String deducciones, String aguinaldo, String slNeto){
        //Instanciando la variable ID para poder retornarla
        boolean right = false;

        //Instanciando la clase DatabaseHelper e instanciando SQLiteDatabase para
        //Que permita insertar datos en una tabla especifica
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try{
            db.execSQL("UPDATE " + TABLE_REGISTROS + " SET nombre = '" + nombre + "', area = '" + area + "'," +
                    "tipo = '" + tipo + "', salario_base = '" + salB + "', deducciones = '" + deducciones + "'," +
                    "aguinaldo = '" + aguinaldo + "', salario_neto = '" + slNeto + "' WHERE id = '" + id + "' ");

            right = true;

        }
        catch (Exception ex){
            ex.toString();
            right = false;
        }
        finally {
            db.close();
        }

        return right;
    }

    public boolean DeleteReg(int id){
        //Instanciando la variable ID para poder retornarla
        boolean right = false;

        //Instanciando la clase DatabaseHelper e instanciando SQLiteDatabase para
        //Que permita insertar datos en una tabla especifica
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try{
            db.execSQL("DELETE FROM " + TABLE_REGISTROS + " WHERE id = '" + id + "'");

            right = true;

        }
        catch (Exception ex){
            ex.toString();
            right = false;
        }
        finally {
            db.close();
        }

        return right;
    }
}
