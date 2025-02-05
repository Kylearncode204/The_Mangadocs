package kytallo.com.themangadocs;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UserDB";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    public static final String TABLE_FAVORITES = "favorites";
    public static final String COLUMN_COMIC_ID = "comic_id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String TABLE_AUTHOR_FAVORITES = "author_favorites";
    public static final String COLUMN_AUTHOR_ID = "author_id";
    public static final String TABLE_FOLLOWS = "follows";
    public static final String COLUMN_STORY_ID = "story_id";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT UNIQUE,"
                + COLUMN_PASSWORD + " TEXT)";
        db.execSQL(CREATE_USERS_TABLE);
        String CREATE_FAVORITES_TABLE = "CREATE TABLE " + TABLE_FAVORITES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_ID + " INTEGER, "
                + COLUMN_COMIC_ID + " TEXT)";
        db.execSQL(CREATE_FAVORITES_TABLE);
        String CREATE_AUTHOR_FAVORITES_TABLE = "CREATE TABLE " + TABLE_AUTHOR_FAVORITES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_ID + " INTEGER, "
                + COLUMN_AUTHOR_ID + " TEXT)";
        db.execSQL(CREATE_AUTHOR_FAVORITES_TABLE);
        String CREATE_FOLLOWS_TABLE = "CREATE TABLE " + TABLE_FOLLOWS + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_ID + " INTEGER, "
                + COLUMN_STORY_ID + " TEXT)";
        db.execSQL(CREATE_FOLLOWS_TABLE);
    }
    public long addFollowStory(int userId, String storyId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_STORY_ID, storyId);
        return db.insert(TABLE_FOLLOWS, null, values);
    }

    public boolean isFollowedStory(int userId, String storyId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FOLLOWS,
                new String[]{"id"},
                COLUMN_USER_ID + "=? AND " + COLUMN_STORY_ID + "=?",
                new String[]{String.valueOf(userId), storyId},
                null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public int removeFollowStory(int userId, String storyId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_FOLLOWS,
                COLUMN_USER_ID + "=? AND " + COLUMN_STORY_ID + "=?",
                new String[]{String.valueOf(userId), storyId});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTHOR_FAVORITES);
        onCreate(db);
    }

    public long registerUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassworld());
        return db.insert(TABLE_USERS, null, values);
    }
    public long addFavorite(int userId, String comicId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_COMIC_ID, comicId);
        return db.insert(TABLE_FAVORITES, null, values);
    }
    public boolean isFavorite(int userId, String comicId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FAVORITES,
                new String[]{COLUMN_ID},
                COLUMN_USER_ID + "=? AND " + COLUMN_COMIC_ID + "=?",
                new String[]{String.valueOf(userId), comicId},
                null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public long addAuthorFavorite(int userId, String authorId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_AUTHOR_ID, authorId);
        return db.insert(TABLE_AUTHOR_FAVORITES, null, values);
    }

    public boolean isAuthorFavorite(int userId, String authorId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_AUTHOR_FAVORITES,
                new String[]{COLUMN_ID},
                COLUMN_USER_ID + "=? AND " + COLUMN_AUTHOR_ID + "=?",
                new String[]{String.valueOf(userId), authorId},
                null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public int removeFavorite(int userId, String targetId, boolean isAuthor) {
        SQLiteDatabase db = this.getWritableDatabase();
        String table = isAuthor ? TABLE_AUTHOR_FAVORITES : TABLE_FAVORITES;
        String column = isAuthor ? COLUMN_AUTHOR_ID : COLUMN_COMIC_ID;
        return db.delete(table,
                COLUMN_USER_ID + "=? AND " + column + "=?",
                new String[]{String.valueOf(userId), targetId});
    }
    public int getUserId(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_ID},
                COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{email, password},
                null, null, null);
        int userId = -1;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                userId = cursor.getInt(columnIndex);
            }
            cursor.close();
        }
        return userId;
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_ID},
                COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{email, password},
                null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}
