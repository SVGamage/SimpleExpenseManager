package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.Database;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.dbAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.dbTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

public class PersistanceExpenseManager extends ExpenseManager{
    private Database database;
    public PersistanceExpenseManager(Database database) {
        this.database = database;
        setup();
    }

    @Override
    public void setup() {

        TransactionDAO dbTransactionDAO = new dbTransactionDAO(database);
        setTransactionsDAO(dbTransactionDAO);

        AccountDAO dbAccountDAO = new dbAccountDAO(database);
        setAccountsDAO(dbAccountDAO);


    }
}
