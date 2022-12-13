//package lk.ac.mrt.cse.dbs.simpleexpensemanager.data;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.support.annotation.Nullable;
//
//public class Database extends SQLiteOpenHelper {
//
//    private static final String DATABASE_NAME = "200681N";
//    private static final int DATABASE_VERSION = 1;
//
//    public Database(@Nullable Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    public static final String TABLE_NAME_ACCOUNT = "Account";
//    public static final String TABLE_NAME_TRANSACTION = "Transaction";
//
////    private static final String TABLE_CREATE_ACCOUNT = "CREATE TABLE " + TABLE_NAME_ACCOUNT + "(" + "accountNo" + " TEXT PRIMARY KEY," + "bankName" + "TEXT," + "accountHoldersName" + "TEXT," + "balance" + "REAL" + ")";
////    private static final String TABLE_CREATE_TRANSACTION ="CREATE TABLE "+ TABLE_NAME_TRANSACTION + "(" + "TransactionId" + "INTEGER PRIMARY KEY AUTOINCREMENT," + "accountNo" + "TEXT," + "amount" + "REAL," + "expenseType" + "TEXT," + "date" + "TEXT," + "FOREIGN KEY(" + "accountNo" + ") REFERENCES "+ TABLE_NAME_ACCOUNT + "(" + "accountNo" + ") )";
//
////    private static final String TABLE_CREATE_ACCOUNT = "CREATE TABLE " + TABLE_NAME_ACCOUNT + "(" + "accountNo" + " TEXT PRIMARY KEY," + "bankName" + "TEXT," + "accountHoldersName" + "TEXT," + "balance" + "REAL" + ")";
////    private static final String TABLE_CREATE_TRANSACTION ="CREATE TABLE " + TABLE_NAME_TRANSACTION + "(" + "TransactionId" + "INTEGER PRIMARY KEY AUTOINCREMENT," + "accountNo" + "TEXT," + "amount" + "REAL," + "expenseType" + "TEXT," + "date" + "TEXT," + "FOREIGN KEY(" + "accountNo" + ") REFERENCES "+ TABLE_NAME_ACCOUNT + "(" + "accountNo" + ") )";
//    private static final String TABLE_CREATE_ACCOUNT = "CREATE TABLE " + TABLE_NAME_ACCOUNT + "(" + "accountNo" + " TEXT PRIMARY KEY," + "bankName" + " TEXT," + "accountHolderName" + " TEXT," + "balance" + " REAL" + ")";
//
//    // Transaction table
//    private static final String TABLE_CREATE_TRANSACTION = "CREATE TABLE " + TABLE_NAME_TRANSACTION + "(" + "transactionId" + " INTEGER PRIMARY KEY AUTOINCREMENT," + "date" + " TEXT," + "accountNo" + " TEXT," + "expenseType" + " TEXT," + "amount" + " REAL," + "FOREIGN KEY(" + "accountNo" + ") REFERENCES "+ TABLE_NAME_ACCOUNT +"(" + "accountNo" + ") )";
//    @Override
//    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL(TABLE_CREATE_ACCOUNT);
//        sqLiteDatabase.execSQL(TABLE_CREATE_TRANSACTION);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int olderVersion, int newVersion) {
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME_ACCOUNT + "'");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME_TRANSACTION + "'");
//
//        onCreate(sqLiteDatabase);
//    }
//
//    public static Database instance;
//
//    public static Database getInstance(Context context) {
//        if (instance == null) {
//            instance = new Database(context);
//        }
//        return instance;
//    };
//}


package lk.ac.mrt.cse.dbs.simpleexpensemanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "200681N";
    private static final int DATABASE_VERSION = 1;

    public static Database instance;

    // Tables of the database
    public static final String TABLE_NAME_ACCOUNT = "accounts";
    public static final String TABLE_NAME_TRANSACTIONS = "transactions";

    // Account table
    private static final String ACCOUNT_TABLE_CREATE = "CREATE TABLE " + TABLE_NAME_ACCOUNT
                                                        + "(" + "accountNo" + " TEXT PRIMARY KEY,"
                                                        + "bankName" + " TEXT,"
                                                        + "accountHolderName" + " TEXT,"
                                                        + "balance" + " REAL" + ")";

    // Transaction table
    private static final String TRANSACTIONS_TABLE_CREATE = "CREATE TABLE " + TABLE_NAME_TRANSACTIONS
                                                        + "(" + "transactionId" + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                                                        + "date" + " TEXT,"
                                                        + "accountNo" + " TEXT,"
                                                        + "expenseType" + " TEXT,"
                                                        + "amount" + " REAL,"
                                                        + "FOREIGN KEY(" + "accountNo" + ") REFERENCES "+ TABLE_NAME_ACCOUNT
                                                        +"(" + "accountNo" + ") )";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create tables
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ACCOUNT_TABLE_CREATE);
        sqLiteDatabase.execSQL(TRANSACTIONS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // discard the table if exist
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME_ACCOUNT + "'");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME_TRANSACTIONS + "'");
        onCreate(sqLiteDatabase);
    }

    public static Database getInstance(Context context) {
        if (instance == null) {
            instance = new Database(context);
        }
        return instance;
    };

}