package movilis;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by sonorks on 30/05/16.
 */
public class EmailSender extends AppCompatActivity {
    public EmailSender(){
    }
    String[] to = {"julian.vasquezg@udea.edu.co"};
    public Intent enviarCorreo(String texto, String asunto){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        String[] cc ={};
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, asunto);
        emailIntent.putExtra(Intent.EXTRA_TEXT, texto);
        Log.d("test", "el texto deberia ser " + texto);
        //this.getBaseContext().startActivity(Intent.createChooser(emailIntent, "Email "));
        return emailIntent;
    }
}
