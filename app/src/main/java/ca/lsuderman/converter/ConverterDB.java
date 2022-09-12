package ca.lsuderman.converter;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ConverterDB extends Application {
    private static final String DB_NAME = "db_converter";
    private static int DB_VERSION = 1;

    private SQLiteOpenHelper helper;

    @Override
    public void onCreate() {
        helper = new SQLiteOpenHelper(this, DB_NAME, null, DB_VERSION) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL("CREATE TABLE IF NOT EXISTS tbl_conversion(" +
                        "conversion_id INTEGER PRIMARY KEY," +
                        "conversion_type TEXT NOT NULL," +
                        "convert_from TEXT NOT NULL," +
                        "convert_to TEXT NOT NULL," +
                        "number_to_convert REAL NOT NULL," +
                        "converted_number REAL NOT NULL)");
            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
                //no-op
            }
        };

        super.onCreate();
    }

    public void addConversion(String conversionType, String convertFrom, String convertTo, double numberToConvert, double convertedNumber){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("INSERT INTO tbl_conversion(conversion_type, convert_from, convert_to, number_to_convert, converted_number) " +
                "VALUES ('" + conversionType + "', '" + convertFrom + "', '" + convertTo + "', '" + numberToConvert + "', '" + convertedNumber + "')");
    }

    public Conversion getConversion(int id){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_conversion WHERE conversion_id = " + id, null );

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Conversion conversion = new Conversion();
        conversion.setConversionId(cursor.getInt(0));
        conversion.setConversionType(cursor.getString(1));
        conversion.setConvertFrom(cursor.getString(2));
        conversion.setConvertTo(cursor.getString(3));
        conversion.setNumberToConvert(cursor.getDouble(4));
        conversion.setConvertedNumber(cursor.getDouble(5));

        cursor.close();
        return conversion;
    }

    public List<Conversion> getAllConversion(){
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Conversion> conversions = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_conversion ORDER BY conversion_id", null );
        cursor.moveToFirst();

        if (cursor.getCount() > 0){
            while (cursor.getPosition() < cursor.getCount()){
                Conversion conversion = new Conversion();

                conversion.setConversionId(cursor.getInt(0));
                conversion.setConversionType(cursor.getString(1));
                conversion.setConvertFrom(cursor.getString(2));
                conversion.setConvertTo(cursor.getString(3));
                conversion.setNumberToConvert(cursor.getDouble(4));
                conversion.setConvertedNumber(cursor.getDouble(5));

                conversions.add(conversion);

                cursor.moveToNext();
            }
        }
        cursor.close();
        return conversions;
    }

    public void deleteConversion(int id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("DELETE FROM tbl_conversion WHERE conversion_id = " + id);
    }

    public void deleteAllConversions() {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("DELETE FROM tbl_conversion");
    }
}
