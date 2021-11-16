package com.example.phonecontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editText,pass,telefono;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=(EditText)findViewById(R.id.id);
        pass=(EditText)findViewById(R.id.nombre);
        telefono=(EditText)findViewById(R.id.telefono);
    }
    public void Agregar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "admnistracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String ID = editText.getText().toString();
        String nombre = pass.getText().toString();
        String numerocel = telefono.getText().toString();

        if(!ID.isEmpty() && !nombre.isEmpty() && !numerocel.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("id", ID);
            registro.put("nombre", nombre);
            registro.put("telefono", numerocel);
            BaseDeDatos.insert("agenda", null, registro);
            BaseDeDatos.close();
            editText.setText("");
            pass.setText("");
            telefono.setText("");
            Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();

        }

    }

    public void Modificar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "admnistracion", null, 1);
        SQLiteDatabase BaseDatabase = admin.getWritableDatabase();
        String ID = editText.getText().toString();
        String nombre = pass.getText().toString();
        String numerocel = telefono.getText().toString();

        if(!ID.isEmpty() && !nombre.isEmpty() && !numerocel.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("id", ID);
            registro.put("nombre", nombre);
            registro.put("telefono", numerocel);
            int cantidad  = BaseDatabase.update("agenda", registro, "id =" + ID, null);
            BaseDatabase.close();
            if(cantidad == 1){
                Toast.makeText(this, "Persona modificado exitosamente", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this, "La persona no existe", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();

        }
    }
    public void Eliminar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        String ID = editText.getText().toString();
        if(!ID.isEmpty()){
            int cantidad = BaseDeDatos.delete("agenda", "id=" + ID, null);
            BaseDeDatos.close();
            editText.setText("");
            pass.setText("");
            telefono.setText("");
            if(cantidad == 1){
                Toast.makeText(this, "Persona eliminada exitosamente", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this, "La persona no existe", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Debes introducir un id correcto", Toast.LENGTH_SHORT).show();
        }
    }
    public void Buscar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();
        String ID = editText.getText().toString();
        if(!ID.isEmpty()) {
            Cursor fila = BaseDatos.rawQuery("select nombre, telefono from agenda where id=" + ID, null  );
            if(fila.moveToFirst()){
                pass.setText(fila.getString(0));
                telefono.setText(fila.getString(1));
                BaseDatos.close();
            }
            else{
                Toast.makeText(this,"No existe la persona", Toast.LENGTH_SHORT).show();
                BaseDatos.close();
            }
        } else {
            Toast.makeText(this, "Debes introducir un id de persona", Toast.LENGTH_SHORT).show();
        }
    }
}