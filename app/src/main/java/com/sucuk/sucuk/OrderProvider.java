package com.sucuk.sucuk;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;


public class OrderProvider extends ContentProvider {

    private static final String AUTHORITY = "com.sucuk.suck.orderprovider";
    private static final String BASE_PATH = "orders";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );

    // Constant to identify the requested operation

    private static final int ORDERS = 1;
    private static final int ORDERS_ID = 2;

    private static final UriMatcher uriMatcher =
            new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY,BASE_PATH,ORDERS);
        uriMatcher.addURI(AUTHORITY,BASE_PATH +"/#",ORDERS_ID);
    }
    private SQLiteDatabase database;
    @Override
    public boolean onCreate() {
        DBOpenHelper helper = new DBOpenHelper(getContext());
        database = helper.getWritableDatabase();
        return true;
    }
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return database.query(DBOpenHelper.TABLE_ORDER,DBOpenHelper.ALL_COLUMNS,selection,null,null,null,DBOpenHelper.MENU_CREATED+" DESC");
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String menuFilter = DBOpenHelper.MENU_NAME+"='"+values.getAsString(DBOpenHelper.MENU_NAME)+"'";
        System.out.println(menuFilter);
        Cursor cursor= database.query(DBOpenHelper.TABLE_ORDER,DBOpenHelper.ALL_COLUMNS,menuFilter,null,null,null,DBOpenHelper.MENU_CREATED+" DESC");
        if(cursor.moveToFirst())
            values.put(DBOpenHelper.MENU_COUNT,cursor.getInt(cursor.getColumnIndex(DBOpenHelper.MENU_COUNT))+1);
        else
            values.put(DBOpenHelper.MENU_COUNT,1);
        long id= database.replace(DBOpenHelper.TABLE_ORDER,null,values);
        return Uri.parse(BASE_PATH+"/"+id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return database.delete(DBOpenHelper.TABLE_ORDER,selection,selectionArgs);
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return database.update(DBOpenHelper.TABLE_ORDER,values,selection,selectionArgs);
    }
}
