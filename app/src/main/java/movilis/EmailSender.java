package movilis;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public interface EmailSender {
    String[] to = {"julian.vasquezg@udea.edu.co"};
    String[] cc = {"wildey.gallego@udea.edu.co","leon.arango@udea.edu.co"};
    Intent crearCorreoReporte(String texto);
}

