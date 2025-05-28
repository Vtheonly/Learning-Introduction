import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBExemple extends SQLiteOpenHelper {

    // Database Version
    private static final int DB_VERSION = 1;

    // Database Name
    private static final String DB_NAME = "dbcours.db"; // As per slide, usually just "dbcours"

    // Table Name
    private static final String TABLE_ETUDIANT = "etudiant";

    // Etudiant Table Columns
    private static final String KEY_MATRICULE = "matricule";
    private static final String KEY_NOM = "nom";
    private static final String KEY_PRENOM = "prenom";

    public DBExemple(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ETUDIANT_TABLE = "CREATE TABLE " + TABLE_ETUDIANT + "("
                + KEY_MATRICULE + " TEXT PRIMARY KEY,"
                + KEY_NOM + " TEXT,"
                + KEY_PRENOM + " TEXT" + ")";
        db.execSQL(CREATE_ETUDIANT_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ETUDIANT);
        // Create tables again
        onCreate(db);
    }

    // --- CRUD Operations ---

    // Adding new Etudiant
    public void addEtudiant(Etudiant etudiant) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MATRICULE, etudiant.matricule);
        values.put(KEY_NOM, etudiant.nom);
        values.put(KEY_PRENOM, etudiant.prenom); // Slide 11 had "nom" for prenom key, corrected here

        db.insert(TABLE_ETUDIANT, null, values);
        db.close(); // Important to close the database connection
    }

    // Getting single Etudiant
    public Etudiant getEtudiant(String matricule) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
            "SELECT * FROM " + TABLE_ETUDIANT + " WHERE " + KEY_MATRICULE + "=?",
            new String[]{matricule}
        );

        Etudiant etudiant = null;
        if (cursor != null && cursor.moveToFirst()) {
            // Column indices must be correct. If SELECT * is used and matricule is 0th column.
            etudiant = new Etudiant(
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_MATRICULE)), // Safer
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_NOM)),
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_PRENOM))
            );
            cursor.close();
        }
        db.close();
        return etudiant;
    }
    
    // Getting all Etudiants (for CursorAdapter example later)
    public Cursor getAllEtudiantsCursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        // For CursorAdapter, it's good practice to have an _id column.
        // SQLite rowid can be aliased as _id.
        return db.rawQuery("SELECT rowid as _id, " + KEY_MATRICULE + ", " + KEY_NOM + ", " + KEY_PRENOM + " FROM " + TABLE_ETUDIANT, null);
        // Note: The db is NOT closed here, the CursorAdapter will manage the Cursor.
        // The Activity that gets the Cursor is responsible for closing it if not used by an adapter.
    }
}