package com.mobil.my_a.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.mobil.my_a.model.Product;
import com.mobil.my_a.model.User;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2; //Veritabani-sürümü
    private static final String DATABASE_NAME = "Camper.db"; //Veritabanı-ismi
    //Tablo-isimlendirmeleri
    public static final String DATABASE_TABLE_USER = "user";
    public static final String DATABASE_TABLE_PRODUCT = "product";
    //Tablo-USER-kolonlari
    private static final String USER_COLUMN_ID = "user_id";
    private static final String USER_COLUMN_NAME = "user_name";
    private static final String USER_COLUMN_SURNAME = "user_surname";
    public static final String USER_COLUMN_EMAIL = "user_mail";
    public static final String USER_COLUMN_PASSWORD = "user_password";
    //Tablo-PRODUCT-kolonlari
    private static final String PRODUCT_COLUMN_ID = "product_id";
    private static final String PRODUCT_COLUMN_NAME = "product_name";
    private static final String PRODUCT_COLUMN_DESC = "product_desc";
    private static final String PRODUCT_COLUMN_OLD = "product_old";
    private static final String PRODUCT_COLUMN_NEW = "product_new";
    //Tablolarin-olusturulmasi
    private String CREATE_USER_TABLE = "CREATE TABLE " + DATABASE_TABLE_USER + "("
            + USER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_COLUMN_NAME + " TEXT,"
            + USER_COLUMN_SURNAME + " TEXT," + USER_COLUMN_EMAIL + " TEXT," + USER_COLUMN_PASSWORD + " TEXT)";

    private String CREATE_PRODUCT_TABLE = "CREATE TABLE " + DATABASE_TABLE_PRODUCT + "("
            + PRODUCT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + PRODUCT_COLUMN_NAME + " TEXT,"
            + PRODUCT_COLUMN_DESC + " TEXT," + PRODUCT_COLUMN_OLD + " TEXT," + PRODUCT_COLUMN_NEW + " TEXT)";
    //Tablolarin-droplanmasi
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + DATABASE_TABLE_USER;
    private String DROP_PRODUCT_TABLE = "DROP TABLE IF EXISTS " + DATABASE_TABLE_PRODUCT;

    //Buradan-asagısı-cokomelli
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public DatabaseHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_PRODUCT_TABLE);
        onCreate(db);
    }
    //Kullanici-ekleme-fonksiyonu
    public void onAddUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_COLUMN_NAME, user.getName());
        values.put(USER_COLUMN_SURNAME, user.getSurname());
        values.put(USER_COLUMN_EMAIL, user.getEmail());
        values.put(USER_COLUMN_PASSWORD, user.getPassword());
        //Insert-sorgusu-burada-yapılıyor
        db.insert(DATABASE_TABLE_USER, null, values);
        db.close();
    }
    //Urunleri-ekleme-fonksiyonu
    public void onAddProduct(Product product){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRODUCT_COLUMN_NAME, product.getPname());
        values.put(PRODUCT_COLUMN_DESC, product.getDescription());
        values.put(PRODUCT_COLUMN_OLD, product.getOldprice());
        values.put(PRODUCT_COLUMN_NEW, product.getNewprice());

        db.insert(DATABASE_TABLE_PRODUCT, null , values);
        db.close();
    }
    //Urunler-kontrolü-getirme
    public void onGetList(){
        String[] columns = { PRODUCT_COLUMN_ID };
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(DATABASE_TABLE_PRODUCT,columns,null,null,null,null,null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
    }

    //Kullanici-kontrolu-getirme
    public boolean onCheckUser(String email, String password){
        String[] columns = { USER_COLUMN_ID };
        SQLiteDatabase db = this.getWritableDatabase();
        String select = USER_COLUMN_EMAIL + " = ?" + " AND " + USER_COLUMN_PASSWORD + " =?";
        String[] selectArgs = { email, password };

        Cursor cursor = db.query(DATABASE_TABLE_USER, columns, select, selectArgs, null,null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0){
            return true;
        } else {
            return false;
        }
    }

    public User Auth(User user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DATABASE_TABLE_USER,
                new String[]{USER_COLUMN_ID, USER_COLUMN_NAME, USER_COLUMN_SURNAME, USER_COLUMN_EMAIL, USER_COLUMN_PASSWORD},
                USER_COLUMN_EMAIL + "=?",
                new String[]{user.email},
                null, null, null);
        if(cursor != null && cursor.moveToFirst() && cursor.getCount()>0){
            User user1 = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4));
                    if(user.password.equalsIgnoreCase(user1.password)){
                        return user1;
                    }
        }
        return null;
    }
}
