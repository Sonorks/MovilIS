package movilis;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class dataBaseManager {
    public static final String tableName = "articulos";
    public static final String cn_id = "_id";
    public static final String cn_code = "codigo";
    public static final String cn_cant = "cantidad";
    public static final String cn_name = "nombre";
    public static final String tableName1 = "prestamos";
    public static final String cn_item = "objeto";
    public static final String cn_userID = "userID";
    public static final String cn_tipo = "tipoPrestamo";

    public static final String CREATE_TABLE_PRESTAMO = "create table " +tableName1+ " ("

            + cn_id + " integer primary key autoincrement,"

            + cn_item + " text not null," + cn_userID + " text not null,"

            + cn_tipo + " text not null);";


    public static final String CREATE_TABLE = "create table " +tableName+ " ("

            + cn_id + " integer primary key autoincrement,"

            + cn_name + " text not null," + cn_cant + " text not null,"

            + cn_code + " text not null);";

    private DbHelper helper;

    private DbHelperPrestamos helperPrestamos;

    public static SQLiteDatabase dbInventario;

    public static SQLiteDatabase dbPrestamos;

    public dataBaseManager(Context context) { //el constructor
        helper = new DbHelper(context);
        helperPrestamos = new DbHelperPrestamos(context);
        dbPrestamos = helperPrestamos.getWritableDatabase();
        dbInventario = helper.getWritableDatabase();
    }

    private ContentValues generarContentValues(String nombre, String codigo, String cantidad) {

        ContentValues valores = new ContentValues();

        valores.put(cn_name, nombre);

        valores.put(cn_code, codigo);

        valores.put(cn_cant, cantidad);

        return valores;

    }

    private ContentValues generarContentValuesPrestamo (String objeto, String userID, String tipoPrestamo){
        ContentValues valores = new ContentValues();
        valores.put(cn_item,objeto);
        valores.put(cn_userID, userID);
        valores.put(cn_tipo, tipoPrestamo);
        return valores;
    }

    public void realizarPrestamo(String articulo, String user, String tipoPrestamo){
        dbPrestamos.insert(tableName1,null,generarContentValuesPrestamo(articulo,user,tipoPrestamo));
    }

    public void insertarArticulo(String nombre, String codigo, String cantidad) {

        dbInventario.insert(tableName, null, generarContentValues(nombre, codigo, cantidad));

    }
    public String PrestamoAEliminar(String ID){
        String[] columnasPrestamos = new String[]{cn_id,cn_item,cn_userID, cn_tipo};
        Cursor c = dbPrestamos.query(tableName1, columnasPrestamos, null, null, null, null, null);
        String name;
        if(c.moveToFirst()){
            do{
                if (c.getString(2).equals(ID)) {
                    name = c.getString(1);
                    eliminarPrestamo(ID);
                    return name; // esta devolviendo el nombre del objeto
                }
            }while(c.moveToNext());
            return "";
        }
        else{
            return "";
        }

    }
    public void eliminarPrestamo(String ID) {
        dbPrestamos.delete(tableName1, cn_userID + "=?", new String[]{ID});
    }
    public Cursor cargarCursorPrestamos() {
        String[] columnasPrestamos = new String[]{cn_id,cn_item,cn_userID, cn_tipo};
        return dbPrestamos.query(tableName1, columnasPrestamos, null, null, null, null, null);
    }
    public void actualizarArticulo(String articulo, String cant){
        String codigo = obtenerCodigoPorArticulo(articulo);
        String where[] = {articulo};
        dbInventario.update(tableName,generarContentValues(articulo,codigo,cant),cn_name + "=?",where);
    }
    public String obtenerCodigoPorArticulo(String articulo){
        Cursor c = cargarCursorArticulos();
        if(c.moveToFirst()){
            do{
                if(c.getString(1).equals(articulo)){
                    return c.getString(2);
                }
            }
            while (c.moveToNext());
            return "null";
        }
        else {
            return "null";
        }
    }
    public Cursor cargarCursorArticulos() {
        String[] columnas = new String[]{cn_id,cn_name, cn_code,cn_cant};
        return dbInventario.query(tableName, columnas, null, null, null, null, null);
    }

    public boolean primeraVez(){
        String[] columnasInventario = new String[]{cn_id,cn_name, cn_code,cn_cant};
        String[] columnasPrestamos = new String[]{cn_id,cn_item,cn_userID,cn_tipo};
        Cursor inventario = dbInventario.query(tableName,columnasInventario,null,null,null,null,null);
        Cursor prestamos = dbPrestamos.query(tableName1,columnasPrestamos,null,null,null,null,null);
        prestamos.moveToFirst();
        inventario.moveToFirst();
        if(inventario.getCount()==0){
            if(prestamos.getCount()==0) {
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    public void insertarInventario() {
        insertarArticulo("UnifiAP_1", "UAP01", "1");
        insertarArticulo("UnifiAP_2", "UAP02", "1");
        insertarArticulo("UnifiAP_3", "UAP03", "1");
        insertarArticulo("TabletAsus_1", "ASUS01", "1");
        insertarArticulo("TabletAsus_2", "ASUS02", "1");
        insertarArticulo("TabletAsus_3", "ASUS03", "1");
        insertarArticulo("TabletAsus_4", "ASUS04", "1");
        insertarArticulo("TabletAsus_5", "ASUS05", "1");
        insertarArticulo("iPAD_1", "IPAD01", "1");
        insertarArticulo("iPhone5_1", "iPhone01", "1");
        insertarArticulo("SamsungS4_1", "S401", "1");
        insertarArticulo("SamsungS4_2", "S402", "1");
        insertarArticulo("BatExtIphone_1", "BEI01", "1");
        insertarArticulo("BatExtIphone_2", "BEI02", "1");
        insertarArticulo("MIKRO-TIK CloudCore_1", "CC01", "1");
        insertarArticulo("MIKRO-TIK CloudCore_2", "CC02", "1");
        insertarArticulo("Kinect Windows_1", "KIN01", "1");
        insertarArticulo("Adaptador RB_1", "RB01", "1");
        insertarArticulo("Adaptador RB_2", "RB02", "1");
        insertarArticulo("Adaptador RB_3", "RB03", "1");
        insertarArticulo("Adaptador RB_4", "RB04", "1");
        insertarArticulo("Mindwave Mobile_1", "MWMBLE01", "1");
        insertarArticulo("RobotEV3_1", "RBTEV01", "1");
        insertarArticulo("RobotEV3_2", "RBTEV02", "1");
        insertarArticulo("RobotEV3_3", "RBTEV03", "1");
        insertarArticulo("RobotNXT_1", "RBTNX01", "1");
        insertarArticulo("RobotNXT_2", "RBTNX02", "1");
        insertarArticulo("Barometric Pressure Sensor_1", "BPS01", "1");
        insertarArticulo("Barometric Pressure Sensor_2", "BPS02", "1");
        insertarArticulo("Barometric Pressure Sensor_3", "BPS03", "1");
        insertarArticulo("Barometric Pressure Sensor_4", "BPS04", "1");
        insertarArticulo("UltraSound Sensor_1", "USS01", "1");
        insertarArticulo("UltraSound Sensor_2", "USS02", "1");
        insertarArticulo("UltraSound Sensor_3", "USS03", "1");
        insertarArticulo("Lithium Backpack_1", "LBPCK01", "1");
        insertarArticulo("Lithium Backpack_2", "LBPCK02", "1");
        insertarArticulo("Lithium Backpack_3", "LBPCK03", "1");
        insertarArticulo("Lithium Backpack_4", "LBPCK04", "1");
        insertarArticulo("Lithium Backpack_5", "LBPCK05", "1");
        insertarArticulo("Lithium Backpack_6", "LBPCK06", "1");
        insertarArticulo("Aeroquad Shield_1", "AQSHD01", "1");
        insertarArticulo("Aeroquad Shield_2", "AQSHD02", "1");
        insertarArticulo("Helices Fibra Carbono_1", "HFC01", "1");
        insertarArticulo("Helices Fibra Carbono_2", "HFC02", "1");
        insertarArticulo("Helices Fibra Carbono_3", "HFC03", "1");
        insertarArticulo("Helices Fibra Carbono_4", "HFC04", "1");
        insertarArticulo("Helices Fibra Carbono_5", "HFC05", "1");
        insertarArticulo("Helices Fibra Carbono_6", "HFC06", "1");
        insertarArticulo("Helices Fibra Carbono_7", "HFC07", "1");
        insertarArticulo("Helices Fibra Carbono_8", "HFC08", "1");
        insertarArticulo("Helices Fibra Carbono_9", "HFC09", "1");
        insertarArticulo("Helices Fibra Carbono_10", "HFC10", "1");
        insertarArticulo("Helices Fibra Carbono_11", "HFC11", "1");
        insertarArticulo("Helices Fibra Carbono_12", "HFC12", "1");
        insertarArticulo("Turning Multistar Pole_1", "TMP01", "1");
        insertarArticulo("Turning Multistar Pole_2", "TMP02", "1");
        insertarArticulo("Turning Multistar Pole_3", "TMP03", "1");
        insertarArticulo("Turning Multistar Pole_4", "TMP04", "1");
        insertarArticulo("Turnigy Multistar 30AMP_1", "TMAMP01", "1");
        insertarArticulo("Turnigy Multistar 30AMP_2", "TMAMP02", "1");
        insertarArticulo("Turnigy Multistar 30AMP_3", "TMAMP03", "1");
        insertarArticulo("Turnigy Multistar 30AMP_4", "TMAMP04", "1");
        insertarArticulo("Turnigy Multistar 30AMP_5", "TMAMP05", "1");
        insertarArticulo("Turnigy Multistar 30AMP_6", "TMAMP06", "1");
        insertarArticulo("Turnigy Multistar 30AMP_7", "TMAMP07", "1");
        insertarArticulo("Turnigy Multistar 30AMP_8", "TMAMP08", "1");
        insertarArticulo("Logic Level Converter_1", "LGLC01", "1");
        insertarArticulo("Logic Level Converter_2", "LGLC02", "1");
        insertarArticulo("Logic Level Converter_3", "LGLC03", "1");
        insertarArticulo("Logic Level Converter_4", "LGLC04", "1");
        insertarArticulo("Logic Level Converter_5", "LGLC05", "1");
        insertarArticulo("Logic Level Converter_6", "LGLC06", "1");
        insertarArticulo("Logic Level Converter_7", "LGLC07", "1");
        insertarArticulo("IMAX C-403_1", "IMAX01", "1");
        insertarArticulo("IMAX C-403_2", "IMAX02", "1");
        insertarArticulo("66 Channel GPS_1", "CHGPS01", "1");
        insertarArticulo("66 Channel GPS_2", "CHGPS02", "1");
        insertarArticulo("66 Channel GPS_3", "CHGPS03", "1");
        insertarArticulo("66 Channel GPS_4", "CHGPS04", "1");
        insertarArticulo("Turnigy NanoTech_1", "TNT01", "1");
        insertarArticulo("GoPro Frame_1", "GOFR01", "1");
        insertarArticulo("GoPro 3Silver_1", "GOSILV01", "1");
        insertarArticulo("Arduino Protoshield_1", "ARPS01", "1");
        insertarArticulo("Arduino Protoshield_2", "ARPS02", "1");
        insertarArticulo("Arduino Protoshield_3", "ARPS03", "1");
        insertarArticulo("Arduino Protoshield_4", "ARPS04", "1");
        insertarArticulo("Arduino Protoshield_5", "ARPS05", "1");
        insertarArticulo("Impresora3D_1", "IMP3D01", "1");
        insertarArticulo("Filamento Impresion 3D_1", "FILIMP01", "1");
        insertarArticulo("Air Vision NVR_1", "AVNVR01", "1");
        insertarArticulo("Air Vision Cam Mini_1", "AVCMINI01", "1");
        insertarArticulo("Air Vision Cam Mini_2", "AVCMINI02", "1");
        insertarArticulo("TP-Link WirelessUSB_1", "TPWLS01", "1");
        insertarArticulo("TP-Link WirelessUSB_2", "TPWLS02", "1");
        insertarArticulo("TP-Link WirelessUSB_3", "TPWLS03", "1");
        insertarArticulo("TP-Link WirelessUSB_4", "TPWLS04", "1");
        insertarArticulo("TP-Link WirelessUSB_5", "TPWLS05", "1");
        insertarArticulo("TP-Link WirelessUSB_6", "TPWLS06", "1");
        insertarArticulo("TP-Link WirelessUSB_7", "TPWLS07", "1");
        insertarArticulo("TP-Link WirelessUSB_8", "TPWLS08", "1");
        insertarArticulo("TP-Link WirelessUSB_9", "TPWLS09", "1");
        insertarArticulo("TP-Link WirelessUSB_10", "TPWLS10", "1");
        insertarArticulo("TP-Link WirelessUSB_11", "TPWLS11", "1");
        insertarArticulo("AXIS Camera_1", "AXISC01", "1");
        insertarArticulo("AXIS Camera_2", "AXISC02", "1");
        insertarArticulo("Cable de Red_1", "CBLRD01", "1");
        insertarArticulo("Cable de Red_2", "CBLRD02", "1");
        insertarArticulo("Cable de Red_3", "CBLRD03", "1");
        insertarArticulo("Cable de Red_4", "CBLRD04", "1");
        insertarArticulo("Cable de Red_5", "CBLRD05", "1");
        insertarArticulo("Cable de Red_6", "CBLRD06", "1");
        insertarArticulo("Cable de Red_7", "CBLRD07", "1");
        insertarArticulo("Cable de Red_8", "CBLRD08", "1");
        insertarArticulo("Cable de Red_9", "CBLRD09", "1");
        insertarArticulo("Cable de Red_10", "CBLRD10", "1");
        insertarArticulo("Cable de Red_11", "CBLRD11", "1");
        insertarArticulo("Cable de Impresora_1", "CBLIMP01", "1");
        insertarArticulo("Cable de Impresora_2", "CBLIMP02", "1");
        insertarArticulo("Cable VGA_1", "CBLVGA01", "1");
        insertarArticulo("Cable MiniUSB_1", "CBLMUSB01", "1");
        insertarArticulo("Cargador_1", "CRG01", "1");
        insertarArticulo("Cable USB Samsung_1", "CBLSMG01", "1");
        insertarArticulo("Mouse Genius_1", "MSGN01", "1");
        insertarArticulo("Mouse HP_1", "MSHP01", "1");
        insertarArticulo("Pilas AA_1", "PLAA01", "1");
        insertarArticulo("Pilas AA_2", "PLAA02", "1");
        insertarArticulo("Pilas AA_3", "PLAA03", "1");
        insertarArticulo("Pilas AA_4", "PLAA04", "1");
        insertarArticulo("Pilas AA_5", "PLAA05", "1");
        insertarArticulo("Pilas AA_6", "PLAA06", "1");
        insertarArticulo("Pilas AAA_1", "PLAAA01", "1");
        insertarArticulo("Pilas AAA_2", "PLAAA02", "1");
        insertarArticulo("Pilas AAA_3", "PLAAA03", "1");
        insertarArticulo("Pilas AAA_4", "PLAAA04", "1");
        insertarArticulo("Cargador ASUS_1", "CRGASUS01", "1");
        insertarArticulo("Lector TIP_1", "LCTIP01", "1");
        insertarArticulo("RouterBoard STX_1", "RBSTX01", "1");
        insertarArticulo("RouterBoard STX_2", "RBSTX02", "1");
        insertarArticulo("MYO_1", "MYO01", "1");
        insertarArticulo("Arduino UNO_1", "ARUNO01", "1");
        insertarArticulo("Arduino UNO_2", "ARUNO02", "1");
        insertarArticulo("Arduino UNO_3", "ARUNO03", "1");
        insertarArticulo("Arduino UNO_4", "ARUNO04", "1");
        insertarArticulo("Arduino UNO_5", "ARUNO05", "1");
        insertarArticulo("Arduino UNO_6", "ARUNO06", "1");
        insertarArticulo("Arduino DUE_1", "ARDUE01", "1");
        insertarArticulo("Arduino DUE_2", "ARDUE02", "1");
        insertarArticulo("Arduino DUE_3", "ARDUE03", "1");
        insertarArticulo("MicroSD_1", "MRCSD01", "1");
        insertarArticulo("MicroSD_2", "MRCSD02", "1");
        insertarArticulo("MicroSD_3", "MRCSD03", "1");
        insertarArticulo("MicroSD_4", "MRCSD04", "1");
        insertarArticulo("RaspberryPI_1", "RBRRY01", "1");
        insertarArticulo("RaspberryPI_2", "RBRRY02", "1");
        insertarArticulo("RaspberryPI_3", "RBRRY03", "1");
        insertarArticulo("RaspberryPI_4", "RBRRY04", "1");
        insertarArticulo("AdaptadorAC_1", "ADPAC01", "1");
        insertarArticulo("AdaptadorAC_2", "ADPAC02", "1");
        insertarArticulo("AdaptadorAC_3", "ADPAC03", "1");
        insertarArticulo("AdaptadorAC_4", "ADPAC04", "1");
        insertarArticulo("Mini Wifi Raspberry_1", "MiniRBRRY01", "1");
        insertarArticulo("Mini Wifi Raspberry_2", "MiniRBRRY02", "1");
        insertarArticulo("Mini Wifi Raspberry_3", "MiniRBRRY03", "1");
        insertarArticulo("Mini Wifi Raspberry_4", "MiniRBRRY04", "1");
        insertarArticulo("USB Console Cable_1", "USBCC01", "1");
        insertarArticulo("USB Console Cable_2", "USBCC02", "1");
        insertarArticulo("USB Console Cable_3", "USBCC03", "1");
        insertarArticulo("USB Console Cable_4", "USBCC04", "1");
        insertarArticulo("USB MicroUSB Cable_1", "USBMICRO01", "1");
        insertarArticulo("USB MicroUSB Cable_2", "USBMICRO02", "1");
        insertarArticulo("USB MicroUSB Cable_3", "USBMICRO03", "1");
        insertarArticulo("USB MicroUSB Cable_4", "USBMICRO04", "1");
        insertarArticulo("Cable HDMI High Speed_1", "HDMIHS01", "1");
        insertarArticulo("Cable HDMI High Speed_2", "HDMIHS02", "1");
        insertarArticulo("Cable HDMI High Speed_3", "HDMIHS03", "1");
        insertarArticulo("Cable HDMI High Speed_4", "HDMIHS04", "1");
        insertarArticulo("Rubber Bumper 4Pack_1", "RBPCK01", "1");
        insertarArticulo("Rubber Bumper 4Pack_2", "RBPCK02", "1");
        insertarArticulo("Rubber Bumper 4Pack_3", "RBPCK03", "1");
        insertarArticulo("Rubber Bumper 4Pack_4", "RBPCK04", "1");
        insertarArticulo("Rubber Bumper 4Pack_5", "RBPCK05", "1");
        insertarArticulo("Rubber Bumper 4Pack_6", "RBPCK06", "1");
        insertarArticulo("Rubber Bumper 4Pack_7", "RBPCK07", "1");
        insertarArticulo("XBee S2_1", "XBS01", "1");
        insertarArticulo("XBee S2_2", "XBS02", "1");
        insertarArticulo("XBee S2_3", "XBS03", "1");
        insertarArticulo("XBee S2_4", "XBS04", "1");
        insertarArticulo("XBee S2_5", "XBS05", "1");
        insertarArticulo("XBee S2_6", "XBS06", "1");
        insertarArticulo("XBee Explorer USB_1", "XBEX01", "1");
        insertarArticulo("XBee Explorer USB_2", "XBEX02", "1");
        insertarArticulo("XBee Explorer USB_3", "XBEX03", "1");
        insertarArticulo("XBee Explorer USB_4", "XBEX04", "1");
        insertarArticulo("XBee Explorer USB_5", "XBEX05", "1");
        insertarArticulo("XBee Explorer USB_6", "XBEX06", "1");
        insertarArticulo("XBee Shield_1", "XBSHLD01", "1");
        insertarArticulo("XBee Shield_2", "XBSHLD02", "1");
        insertarArticulo("XBee Shield_3", "XBSHLD03", "1");
        insertarArticulo("XBee Shield_4", "XBSHLD04", "1");
        insertarArticulo("XBee Shield_5", "XBSHLD05", "1");
        insertarArticulo("XBee Shield_6", "XBSHLD06", "1");
        insertarArticulo("Grove Starter Kit_1", "GRVKIT01", "1");
        insertarArticulo("Grove Starter Kit_2", "GRVKIT02", "1");
        insertarArticulo("Grove Starter Kit_3", "GRVKIT03", "1");
        insertarArticulo("Grove Starter Kit_4", "GRVKIT04", "1");
        insertarArticulo("Grove Starter Kit_5", "GRVKIT05", "1");
        }
    }


