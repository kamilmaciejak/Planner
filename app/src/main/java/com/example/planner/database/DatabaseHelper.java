package com.example.planner.database;

import android.content.Context;
import org.greenrobot.greendao.database.Database;

public class DatabaseHelper extends DaoMaster.OpenHelper {

    public DatabaseHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        for (int ver = oldVersion; ver < DaoMaster.SCHEMA_VERSION; ver++) {
            switch (ver) {
                case 1:
                    migrateToV2(db);
            }
        }
    }

    private void migrateToV2(Database db) {
        db.execSQL("ALTER TABLE " + PlanEntityDao.TABLENAME
                + " ADD COLUMN " + PlanEntityDao.Properties.Position.columnName + " LONG DEFAULT '0'");
    }
}
