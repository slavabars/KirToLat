package dcns.ru.kirtolat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB {
    private final Context mCtx;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB(Context ctx) {
        mCtx = ctx;
    }

    public void open() {
        mDBHelper = new DBHelper(mCtx);
        mDB = mDBHelper.getWritableDatabase();
    }

    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }

    public Cursor getAllData() {
        return mDB.query("copylist", null, null, null, null, null, "id desc");
    }

    public boolean getFromId(int id) {
        String selection = "id = ?";
        String[] selectionArgs = new String[] {String.valueOf(id)};
        Cursor c =  mDB.query("copylist", null, selection, selectionArgs, null, null, null);
        if(c.moveToFirst()){
           return true;
        }
        return false;
    }

    public boolean getFromText(String text) {
        String selection = "text = ?";
        String[] selectionArgs = new String[] {String.valueOf(text)};
        Cursor c =  mDB.query("copylist", null, selection, selectionArgs, null, null, null);
        if(c.moveToFirst()){
            return true;
        }
        return false;
    }

    public void addRec(String text, String data) {
        ContentValues cv = new ContentValues();
        cv.put("text", text);
        cv.put("data", data);
        mDB.insert("copylist", null, cv);
    }

    public void delRec(long id) {
        mDB.delete("copylist", "id" + " = " + id, null);
    }

    static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, "kirtolat", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table copylist ("
                    + "id integer primary key autoincrement,"
                    + "data text,"
                    + "text text);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVer, int newVer) {

        }
    }
}
