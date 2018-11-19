package my.headbana.fyp.drong3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {

    //In here we will define the setting of our DB
    //table name, column, etc
    //Preferred to make it this way to avoid any typo error, if we keep defining table_name,column_name,etc in each JAVA class that require us to connect to DB

    private static final String DATABASE_NAME = "MobileAppDevelopment";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "profile";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_PASSWORD = "password";

    DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Query to create table (need basic understanding of SQL Query)
        String sql = "CREATE TABLE " + TABLE_NAME + " (\n" +
                "    " + COLUMN_ID + " INTEGER NOT NULL CONSTRAINT row_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    " + COLUMN_NAME + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_ADDRESS + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_PASSWORD + " varchar NOT NULL" +
                ");";

        // This query will generate (String)sql as below (according to our concatenation and variable above)
        // sql = "CREATE TABLE profile ( "id"  INTEGER NOT NULL CONSTRAINT row_pk PRIMARY KEY AUTOINCREMENT,"name" varchar(200) NOT NULL,"address" varchar(200) NOT NULL,"password" varchar NOT NULL)
        // You may try copy the sql query and run inside XAMPP/phpMyAdmin to see if the database created is actually created as what you want

        //By understanding the actual program logic, it would help you debug your program :D

        // Executing the sql query
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        // Incase if there are any changes need to be made to our table structure
        // Easier to drop and re-create the db instead of adding column, define primary key, etc LOL

        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    // This will be our function/method to run a specific action

    // Write function
    boolean insertProfileData(String name, String address, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_ADDRESS, address);
        contentValues.put(COLUMN_PASSWORD, password);
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(TABLE_NAME, null, contentValues) != -1;
    }

    // Keep in mind, data retrieved from the database might be in Array Object.
    // The main purpose of this JAVA class is just to retrieve the data.
    // We will parse the data later in our DisplayData.java

    // Read
    Cursor getAllProfiles() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    Cursor getProfile(int id){
        SQLiteDatabase db = getReadableDatabase();
//        Select ALL from profile where id=id
        return db.rawQuery("SELECT * FROM " + TABLE_NAME +" WHERE " + COLUMN_ID + " = "+id, null);
    }

    // Update
    boolean updateProfile(int id, String name, String address, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_ADDRESS, address);
        contentValues.put(COLUMN_PASSWORD, password);
        return db.update(TABLE_NAME, contentValues, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) == 1;
    }

    // Delete
    boolean deleteProfile(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) == 1;
    }
}
