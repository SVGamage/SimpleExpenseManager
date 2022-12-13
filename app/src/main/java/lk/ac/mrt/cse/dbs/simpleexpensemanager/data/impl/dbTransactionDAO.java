package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.Database;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class dbTransactionDAO implements TransactionDAO {
    private Database database;
    public dbTransactionDAO(Database database) {
        this.database = database;
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("date", new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date));
        value.put("accountNo", accountNo );
        value.put("expenseType", String.valueOf(expenseType));
        value.put("amount",amount);
        db.insert(database.TABLE_NAME_TRANSACTIONS,null,value);
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        SQLiteDatabase db = database.getWritableDatabase();
        List<Transaction> transactions = new ArrayList<>();


        Cursor cursor = db.query(database.TABLE_NAME_TRANSACTIONS,
                null, null,
                null, null,
                null, null
        );

        if (cursor.moveToFirst()) {
            while (true){
                Date date = null;
                try{

                    date = new SimpleDateFormat("dd/MM/yyyy").parse(cursor.getString(1));
                    String accountNo = cursor.getString(2);
                    ExpenseType expenseType = ExpenseType.valueOf(cursor.getString(3));
                    Double amount = Double.parseDouble(cursor.getString(4));
                    Transaction tempTrans = new Transaction(date,accountNo,expenseType,amount);
                    transactions.add(tempTrans);
                }catch (Exception e){
                }

                if (!cursor.moveToNext()){
                    break;
                }
            }
        }
        cursor.close();
        return transactions;

    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {

        List<Transaction> transactions = getAllTransactionLogs();

        int size = transactions.size();
        if (size <= limit) {
            return transactions;
        }
        // return the last <code>limit</code> number of transaction logs
        return transactions.subList(size - limit, size);
    }

}
