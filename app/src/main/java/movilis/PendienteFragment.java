package movilis;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import co.edu.udea.compumovil.lab4gr9.weather.movilis.R;

public class PendienteFragment extends android.support.v4.app.Fragment implements View.OnClickListener{
    public PendienteFragment() {
    }
    ListView lv;
    dataBaseManager manager;
    Button enviarReporte;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pendientes,
                container, false);
        dataBaseManager manager = new dataBaseManager(this.getContext());
        SimpleCursorAdapter adapter;

        lv = (ListView) view.findViewById(R.id.lvPendientes);
        String[] from = new String[]{manager.cn_id,manager.cn_item,manager.cn_userID,manager.cn_tipo};
        int[] to = new int[]{R.id.IdLvPendiente,R.id.ItemLvPendiente,R.id.UserLvPendiente};
        Cursor cursor = manager.cargarCursorPrestamos();
        if(cursor != null) {
            cursor.moveToFirst();
            adapter = new SimpleCursorAdapter(this.getContext(), R.layout.listviewpendientes, cursor, from, to, 0);
            lv.setAdapter(adapter);
        }
        enviarReporte = (Button) view.findViewById(R.id.btnPendientesReporte);
        enviarReporte.setOnClickListener(this);
        return view;
    }
    public void onClick(View v){

        if(v.getId() == R.id.btnPendientesReporte) {
            enviarReporte();
        }

    }
    public void enviarReporte(){
        String texto="La información de los prestamos pendientes: \n";
        manager = new dataBaseManager(this.getContext());
        boolean existeReporte=false;
        Cursor cursor = manager.cargarCursorPrestamos();
        if(cursor.moveToFirst()){
            do{
                Log.d("test",cursor.getString(3));
                if(cursor.getString(3).equals("1")){
                    texto = texto.concat("-El usuario con ID "+cursor.getString(2)+" tiene el articulo "+cursor.getString(1) + "\n");
                    existeReporte=true;
                }
            }while (cursor.moveToNext());
        }
        if(existeReporte) {
            EmailSender emailSender = new EmailSender();
            emailSender.enviarCorreo(texto, "Reporte prestamos pendientes");
            Intent mail = emailSender.enviarCorreo(texto, "Reporte entrega de articulo en mal estado");
            startActivity(Intent.createChooser(mail,"Email "));
        }
        else{
            Toast.makeText(this.getContext(),"No hay articulos con prestamos externos para reportar.",Toast.LENGTH_LONG).show();
        }
    }

}