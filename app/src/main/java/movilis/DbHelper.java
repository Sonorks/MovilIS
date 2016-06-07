package movilis;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sonorks on 10/05/16.
 */
public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, db_name, null, db_scheme_version);
    }

    private static final String db_name= "articulos";
    private static int db_scheme_version=1;

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("Voy a crear la tabla");
        db.execSQL(dataBaseManager.CREATE_TABLE);
        System.out.println("Tabla creada");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
