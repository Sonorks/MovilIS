package movilis;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import co.edu.udea.compumovil.lab4gr9.weather.movilis.R;

public class DevolucionFragment extends android.support.v4.app.Fragment {
    public DevolucionFragment() {
    }
    dataBaseManager manager;
    Button devolucion ;
    EditText etID ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_devolucion,
                container, false);
        devolucion = (Button) view.findViewById(R.id.btnDevolucionDevolver);
        etID = (EditText) view.findViewById(R.id.etDevolucionUsuario);
        devolucion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (v.getId()== R.id.btnDevolucionDevolver){
                    devolverObjeto(etID.getText().toString());
                }
            }
        });

        return view;
    }
    public void devolverObjeto(String id){
        if(id.equals("")){
            Toast.makeText(this.getContext(),"Por favor ingrese el ID del usuario.",Toast.LENGTH_LONG).show();
        }
        else {
            manager = new dataBaseManager(this.getContext());
            String articulo;
            articulo = manager.PrestamoAEliminar(id); //obtiene el nombre
            if (articulo.equals("")) {
                Toast.makeText(this.getContext(), "No se ha encontrado prestamos registrador para : " + id, Toast.LENGTH_LONG).show();
            } else {
                if (devolucion.isEnabled()) {
                    Toast.makeText(this.getContext(), "Se le informará al administrador que el articulo fue entregado en mal estado", Toast.LENGTH_SHORT).show();
                    manager.actualizarArticulo(articulo, "1");
                    String texto = "El usuario con ID " + id + " ha devuelto el objeto " + articulo + " en mal estado.";
                    EmailSender emailSender = new EmailDaños();
                    Intent mail = emailSender.crearCorreoReporte(texto);
                    startActivity(Intent.createChooser(mail, "Email "));
                } else {
                    Toast.makeText(this.getContext(), "prestamo de " + id + " eliminado", Toast.LENGTH_SHORT).show();
                    manager.actualizarArticulo(articulo, "1");
                }
            }
        }
    }
}