package mukhtar.exapple.com.solutions_book;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(_id integer primary key autoincrement," +
                "login text,pass text);");
        db.execSQL("create table information(_id integer primary key autoincrement,username text,name text, surname text );");
        db.execSQL("create table categories(_id integer primary key autoincrement," +
                "category_name text,"+
                "parent_category_id int,"+
                "parent_category_name text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}