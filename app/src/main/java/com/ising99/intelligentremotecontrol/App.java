package com.ising99.intelligentremotecontrol;

import android.app.Application;

import com.ising99.intelligentremotecontrol.db.DaoMaster;
import com.ising99.intelligentremotecontrol.db.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by shun on 2018/3/28.
 *
 */

public class App extends Application {
    public static final boolean ENCRYPTED = true;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"users-db"); //The users-db here is the name of our database.
//        Database db = helper.getWritableDb();
//        daoSession = new DaoMaster(db).newSession();

        ///// Using the below lines of code we can toggle ENCRYPTED to true or false in other to use either an encrypted database or not.
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "users-db-encrypted" : "users-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

}
