package fr.thebigtouffe.unescoworldheritage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import fr.thebigtouffe.unescoworldheritage.UNESCO.Site;

public class userManager extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "user.db";

    String SQL_CREATE_ENTRIES = "CREATE TABLE `user_seen` (`site` INTEGER, PRIMARY KEY(`site`));";
    String SQL_DELETE_ENTRIES = "DROP TABLE user_seen";

    public userManager(Context context) {
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

    public ArrayList<Integer> getSeenSitesIDs() {
        ArrayList<Integer> seenSitesId = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String queryId = "SELECT site FROM user_seen";
        Cursor c = db.rawQuery(queryId, null);

        while(c.moveToNext()) {
            seenSitesId.add(c.getInt(0));
        }

        return seenSitesId;
    }

    public void addSiteToSeen(Site site) {
        Integer id = site.getNumber();

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("site", id);
        db.insert("user_seen", null, values);

    }

    public Boolean getSeenStatus(Integer id) {
        Boolean status = false;

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT site FROM user_seen WHERE site=?";
        Cursor c = db.rawQuery(query, new String[] {id.toString()});

        if (c.moveToFirst()) {
            status = true;
        }
        c.close();

        return status;
    }
}
