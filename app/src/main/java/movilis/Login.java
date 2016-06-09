package movilis;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import co.edu.udea.compumovil.lab4gr9.weather.movilis.R;

/**
 * Created by JULIAN on 03/05/2016.
 */
public class Login extends AppCompatActivity implements View.OnClickListener {
    Button btnLogeo;
    EditText user;
    EditText pass;
    dataBaseManager manager;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        btnLogeo =  (Button) findViewById(R.id.botonLogeo);
        btnLogeo.setOnClickListener(this);
        manager = new dataBaseManager(this);
        user = (EditText) findViewById(R.id.UsuarioLogin);
        pass= (EditText) findViewById(R.id.ContraseñaLogin);
    }
    public void onClick(View v) {
        if(v.getId()==R.id.botonLogeo){
            if (user.getText().toString().equals("test")){
                if(user.getText().toString().equals("test")){
                    logear();
                }
                else{
                    Toast.makeText(this,"Contraseña erronea",Toast.LENGTH_LONG);
                }
            }
            else{
                Toast.makeText(this,"Usuario erroneo",Toast.LENGTH_LONG);
            }
            logear();
        }
    }
    public void logear(){
        boolean insertar = manager.primeraVez();
        if(insertar){
            Toast.makeText(this,"Primer uso, por favor espere a que carguen los datos.",Toast.LENGTH_LONG).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    dataBaseManager manager = new dataBaseManager(getBaseContext());
                    manager.insertarInventario();
                    Intent i = new Intent(getBaseContext(),MainActivity.class);
                    startActivity(i);
                    //Toast.makeText(getBaseContext(),"Los datos han sido cargados.",Toast.LENGTH_LONG).show();
                }
            }).start();
        }
        Intent i = new Intent(getBaseContext(),MainActivity.class);
        startActivity(i);

    }

}
