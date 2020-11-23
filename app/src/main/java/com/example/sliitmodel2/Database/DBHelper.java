package com.example.sliitmodel2.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.sliitmodel2.Model.User;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "UserInfoDB.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserProfile.Users.TABLE_NAME + " (" +
                    UserProfile.Users._ID + " INTEGER PRIMARY KEY," +
                    UserProfile.Users.COLUMN_NAME_UserName + " TEXT," +
                    UserProfile.Users.COLUMN_NAME_DateOfBirth + " TEXT," +
                    UserProfile.Users.COLUMN_NAME_Gender + " TEXT," +
                    UserProfile.Users.COLUMN_NAME_Password + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserProfile.Users.TABLE_NAME;

    //CRUD METHODS

    //INSERT

    public long addInfo(String mUserName, String mDOB, String mGender, String mPassword) {


        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(UserProfile.Users.COLUMN_NAME_UserName, mUserName);
        values.put(UserProfile.Users.COLUMN_NAME_DateOfBirth, mDOB);
        values.put(UserProfile.Users.COLUMN_NAME_Gender, mGender);
        values.put(UserProfile.Users.COLUMN_NAME_Password, mPassword);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(UserProfile.Users.TABLE_NAME, null, values);

        return newRowId;

    }

    //UPDATE

    public boolean updateInfo(String mUserName, String mDOB, String mGender, String mPassword) {
        SQLiteDatabase db = getWritableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(UserProfile.Users.COLUMN_NAME_DateOfBirth, mDOB);
        values.put(UserProfile.Users.COLUMN_NAME_Gender, mGender);
        values.put(UserProfile.Users.COLUMN_NAME_Password, mPassword);

        // Which row to update, based on the title
        String selection = UserProfile.Users.COLUMN_NAME_UserName + " LIKE ?";
        String[] selectionArgs = {mUserName};

        int count = db.update(
                UserProfile.Users.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    //DELETE

    public boolean deleteInfo(String mUserName) {

        SQLiteDatabase db = getWritableDatabase();
        // Define 'where' part of query.
        String selection = UserProfile.Users.COLUMN_NAME_UserName + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {mUserName};
        // Issue SQL statement.
        int deletedRows = db.delete(UserProfile.Users.TABLE_NAME, selection, selectionArgs);

        if (deletedRows > 0) {
            return true;
        } else {
            return false;
        }

    }

    /*
    *
    *
    Retrieve All Info
    *
    *
     */

    public ArrayList readAllInfo() {
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                UserProfile.Users.COLUMN_NAME_UserName,
                UserProfile.Users.COLUMN_NAME_DateOfBirth,
                UserProfile.Users.COLUMN_NAME_Gender,
                UserProfile.Users.COLUMN_NAME_Password
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = UserProfile.Users.COLUMN_NAME_UserName + " = ?";
        String[] selectionArgs = {};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                UserProfile.Users.COLUMN_NAME_UserName + " ASC";

        Cursor cursor = db.query(
                UserProfile.Users.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        ArrayList userInfo = new ArrayList<>();
        while (cursor.moveToNext()) {
            String UserName = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_NAME_UserName));
            String DOB = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_NAME_DateOfBirth));
            String Gender = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_NAME_Gender));
            String Password = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_NAME_Password));

            userInfo.add(UserName);
            userInfo.add(DOB);
            userInfo.add(Password);
            userInfo.add(Gender);
        }
        cursor.close();
        return userInfo;

    }

    /*
    *
    Retrieve info for specific UserName
    *
     */

    public List readAllInfo(String mUserName) {
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                UserProfile.Users.COLUMN_NAME_UserName,
                UserProfile.Users.COLUMN_NAME_DateOfBirth,
                UserProfile.Users.COLUMN_NAME_Gender,
                UserProfile.Users.COLUMN_NAME_Password
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = UserProfile.Users.COLUMN_NAME_UserName + " LIKE ?";
        String[] selectionArgs = {mUserName};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                UserProfile.Users.COLUMN_NAME_UserName + " ASC";

        Cursor cursor = db.query(
                UserProfile.Users.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List userInfo = new ArrayList<>();
        while (cursor.moveToNext()) {
            String UserName = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_NAME_UserName));
            String DOB = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_NAME_DateOfBirth));
            String Gender = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_NAME_Gender));
            String Password = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_NAME_Password));

            userInfo.add(UserName); //0
            userInfo.add(DOB); //1
            userInfo.add(Gender); //2
            userInfo.add(Password); //3

        }
        cursor.close();
        return userInfo;

    }

    /*
    *
    Login Method
    *
     */
    public boolean loginCheck(String mUserName, String mPassword) {
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                UserProfile.Users.COLUMN_NAME_UserName,
                UserProfile.Users.COLUMN_NAME_Password
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = UserProfile.Users.COLUMN_NAME_UserName + " = ? AND " + UserProfile.Users.COLUMN_NAME_Password + " = ? ";
        String[] selectionArgs = {mUserName, mPassword};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                UserProfile.Users.COLUMN_NAME_UserName + " DESC";

        Cursor cursor = db.query(
                UserProfile.Users.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        List validUsers = new ArrayList<>();
        while (cursor.moveToNext()) {
            String userName = cursor.getString(
                    cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_NAME_UserName));
            validUsers.add(userName);
        }
        cursor.close();

        if (validUsers.isEmpty()) {
            return false;
        } else {
            return true;
        }


    }

    /*
     *
     * Read All Method with Objects array
     *
     */

    public List readAllInfoObjects() {
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                UserProfile.Users.COLUMN_NAME_UserName,
                UserProfile.Users.COLUMN_NAME_DateOfBirth,
                UserProfile.Users.COLUMN_NAME_Gender,
                UserProfile.Users.COLUMN_NAME_Password
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = UserProfile.Users.COLUMN_NAME_UserName + " = ?";
        String[] selectionArgs = {};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                UserProfile.Users.COLUMN_NAME_UserName + " ASC";

        Cursor cursor = db.query(
                UserProfile.Users.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List<User> userObjects = new ArrayList<>();
        User user;
        while (cursor.moveToNext()) {
            String UserName = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_NAME_UserName));
            String DOB = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_NAME_DateOfBirth));
            String Gender = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_NAME_Gender));
            String Password = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_NAME_Password));

            user = new User(UserName, DOB, Gender, Password);

            userObjects.add(user);

        }
        cursor.close();
        return userObjects;

    }

    /*
    *
    * For List View change the return Type to ArrayList
    *
     */

    public List readAllInfoObjectsList() {
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                UserProfile.Users.COLUMN_NAME_UserName,
                UserProfile.Users.COLUMN_NAME_DateOfBirth,
                UserProfile.Users.COLUMN_NAME_Gender,
                UserProfile.Users.COLUMN_NAME_Password
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = UserProfile.Users.COLUMN_NAME_UserName + " = ?";
        String[] selectionArgs = {};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                UserProfile.Users.COLUMN_NAME_UserName + " ASC";

        Cursor cursor = db.query(
                UserProfile.Users.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List userObjects = new ArrayList<>();
        while (cursor.moveToNext()) {
            String UserName = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_NAME_UserName));
            String DOB = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_NAME_DateOfBirth));
            String Gender = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_NAME_Gender));
            String Password = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_NAME_Password));

            userObjects.add(UserName);

        }
        cursor.close();
        return userObjects;

    }

}

