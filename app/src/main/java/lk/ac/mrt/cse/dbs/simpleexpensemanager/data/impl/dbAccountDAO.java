package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.Database;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class dbAccountDAO implements AccountDAO {
    private Database database;
    public dbAccountDAO(Database database) {
        this.database = database;
    }

    @Override
    public List<String> getAccountNumbersList() {
        SQLiteDatabase db = database.getReadableDatabase();
        List<String> AccNoList = new ArrayList<>();
        Cursor cursor = db.query(database.TABLE_NAME_ACCOUNT,
                                    new String[] {"accountNo"},
                                null,
                                null,
                                null,
                                null,
                                null
                                );

        if (cursor.moveToFirst()) {
            while(true){
                String AccNo = cursor.getString(0);
                AccNoList.add(AccNo);
                if(!cursor.moveToNext()){
                    break;
                }
            }
        }
        cursor.close();
        return AccNoList;
    }

    @Override
    public List<Account> getAccountsList() {
        SQLiteDatabase db = database.getReadableDatabase();
        List<Account> AccList = new ArrayList<>();

        Cursor cursor = db.query(database.TABLE_NAME_ACCOUNT,
                                null, null,
                                null, null,
                                null, null
                                );

        if (cursor.moveToFirst()) {
            while (true){
                String AccNo = cursor.getString(0);
                String bankName = cursor.getString(1);
                String AccHolderName = cursor.getString(2);
                Double balance = Double.parseDouble(cursor.getString(3));
                Account TempAcc = new Account(AccNo,bankName,AccHolderName,balance);
                AccList.add(TempAcc);
                if (!cursor.moveToNext()){
                    break;
                }
            }
        }
        cursor.close();
        return AccList;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase db = database.getReadableDatabase();

        Cursor cursor = db.query(database.TABLE_NAME_ACCOUNT,
                                null, null,
                                null, null,
                                null, null
                                );

        if (cursor.moveToFirst()) {
            while (true){
                if (cursor.getString(0).equals(accountNo)){
                    String AccNo = cursor.getString(0);
                    String bankName = cursor.getString(1);
                    String AccHolderName = cursor.getString(2);
                    Double balance = Double.parseDouble(cursor.getString(3));
                    Account TempAcc = new Account(AccNo,bankName,AccHolderName,balance);
                    return TempAcc;
                }
                if (!cursor.moveToNext()){
                    break;
                }
            }
        }
        String NotFoundMsg = "Account " + accountNo + " isn't available.";
        throw new InvalidAccountException(NotFoundMsg);
    }

    @Override
    public void addAccount(Account account) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("accountNo", account.getAccountNo());
        value.put("bankName", account.getBankName());
        value.put("accountHolderName", account.getAccountHolderName());
        value.put("balance", account.getBalance());
        db.insert(database.TABLE_NAME_ACCOUNT,null,value);
        db.close();
    }



    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        Account account = this.getAccount(accountNo);
        SQLiteDatabase db = database.getWritableDatabase();
        db.delete(Database.TABLE_NAME_ACCOUNT,"accountNo" + " = ?", new String[] { accountNo });
        db.close();
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        Account account = this.getAccount(accountNo);
        SQLiteDatabase db = database.getWritableDatabase();

        ContentValues values = new ContentValues();
        switch (expenseType) {
            case EXPENSE:
                values.put("balance", account.getBalance() - amount);
                break;
            case INCOME:
                values.put("balance", account.getBalance() + amount);
                break;
        }
        db.update(Database.TABLE_NAME_ACCOUNT, values, "accountNo" + " = ?", new String[] { accountNo });
        db.close();
    }
}
