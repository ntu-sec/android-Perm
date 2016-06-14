package sg.edu.ntu.testperm.provider.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

@SuppressWarnings("unused")
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Provider.db";
    private static DatabaseHandler handler;
    private final Context context;

    private DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context.getApplicationContext();
    }

    private JSONObject loadJSonFromAsset(String fileName) {
        JSONObject jsonObject = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            jsonObject = new JSONObject(json);
        } catch (IOException e) {
            throw new RuntimeException("cannot get asset folder");
        } catch (JSONException e) {
            throw new RuntimeException("cannot parse json string");
        }
        return jsonObject;
    }

    public static DatabaseHandler getInstance(final Context context) {
        if (handler == null) {
            handler = new DatabaseHandler(context);
        }
        return handler;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Person.CREATE_TABLE);
        JSONObject jsonObject = loadJSonFromAsset("info.json");
        JSONArray jsonArray = jsonObject.optJSONArray("people");
        for (int i = 0; i < jsonArray.length(); ++i) {
            try {
                JSONObject itemJSObject = jsonArray.getJSONObject(i);
                String firstName = itemJSObject.optString("firstName");
                String lastName = itemJSObject.optString("lastName");
                String birth = itemJSObject.optString("birth");
                Person person = new Person(firstName, lastName, birth);
                db.insert(Person.TABLE_NAME, "STUB", person.getContent());
            } catch (JSONException e) {
                throw new RuntimeException("cannot get js with i=" + i);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        do nothing
    }


    public synchronized Person getPerson(final long id) {
        final SQLiteDatabase db = getReadableDatabase();
        final Cursor cursor = db.query(
                Person.TABLE_NAME, // table
                Person.FIELDS, // fields
                Person.COL_ID + " IS ?", // selection
                new String[]{String.valueOf(id)}, // selectionArgs
                null, // groupBy
                null, // having
                null, // orderBy
                null); // limit
        if (cursor == null || cursor.isAfterLast()) {
            return null;
        }

        Person item = null;
        if (cursor.moveToFirst()) {
            item = new Person(cursor);
        }
        cursor.close();
        return item;
    }

    public synchronized boolean putPerson(final Person person) {
        boolean success = false;
        int result = 0;
        final SQLiteDatabase db = getWritableDatabase();

        if (person.id >= 0) {
            result += db.update(Person.TABLE_NAME, // table
                    person.getContent(), // values
                    Person.COL_ID + " IS ?", // where clause
                    new String[]{String.valueOf(person.id)}); // whereArgs
        }

        if (result > 0) {
            success = true;
        } else {
            final long id = db.insert(
                    Person.TABLE_NAME,
                    "STUB",
                    person.getContent());
            if (id >= 0) {
                person.id = id;
                success = true;
            }
        }

        if (success) {
            notifyProviderOnPersonChange();
        }

        return success;
    }

    public synchronized int removePerson(final Person person) {
        final SQLiteDatabase db = this.getWritableDatabase();
        final int result = db.delete(Person.TABLE_NAME,
                Person.COL_ID + " IS ?",
                new String[]{Long.toString(person.id)});

        if (result > 0) {
            notifyProviderOnPersonChange();
        }
        return result;
    }

    private void notifyProviderOnPersonChange() {
        context.getContentResolver().notifyChange(
                PersonProvider.URI_PERSONS, null, false);
    }
}
