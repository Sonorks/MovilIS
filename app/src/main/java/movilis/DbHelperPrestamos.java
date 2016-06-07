package movilis;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by sonorks on 10/05/16.
 */
public class DbHelperPrestamos extends SQLiteOpenHelper {

    public DbHelperPrestamos(Context context) {
        super(context, db_name, null, db_scheme_version);
    }

    private static final String db_name= "prestamos";
    private static int db_scheme_version=1;

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("Voy a crear la tabla de prestamos");
        db.execSQL(dataBaseManager.CREATE_TABLE_PRESTAMO);
        Log.d("test","Tabla prestamos creada");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
