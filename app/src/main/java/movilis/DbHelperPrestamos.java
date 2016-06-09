package movilis;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DbHelperPrestamos extends SQLiteOpenHelper {

    public DbHelperPrestamos(Context context) {
        super(context, db_name, null, db_scheme_version);
    }

    private static final String db_name= "prestamos";
    private static int db_scheme_version=1;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(dataBaseManager.CREATE_TABLE_PRESTAMO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
