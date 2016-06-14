package sg.edu.ntu.testperm.provider.database;

import android.content.ContentValues;
import android.database.Cursor;

public class Person {

    public static final String TABLE_NAME = "PersonTable";
    public static final String COL_ID = "_id";
    public static final String FIRSTNAME = "firstName";
    public static final String LASTNAME = "lastName";
    public static final String BIRTH = "birth";

    public Person(String firstName, String lastName, String birth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birth = birth;
    }

    public Person() {
    }

    public static final String[] FIELDS = {COL_ID, FIRSTNAME, LASTNAME,
            BIRTH};

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COL_ID + " INTEGER PRIMARY KEY,"
                    + FIRSTNAME + " TEXT NOT NULL DEFAULT '',"
                    + LASTNAME + " TEXT NOT NULL DEFAULT '',"
                    + BIRTH + " TEXT NOT NULL DEFAULT ''"
                    + ")";
    public static String TAG = "Person";
    public long id = -1;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirth() {
        return birth;
    }

    private String firstName;
    private String lastName;
    private String birth;

    public Person(final Cursor cursor) {
        this.id = cursor.getLong(0);
        this.firstName = cursor.getString(1);
        this.lastName = cursor.getString(2);
        this.birth = cursor.getString(3);
    }

    public ContentValues getContent() {
        final ContentValues values = new ContentValues();
        values.put(FIRSTNAME, firstName);
        values.put(LASTNAME, lastName);
        values.put(BIRTH, birth);
        return values;
    }
}
