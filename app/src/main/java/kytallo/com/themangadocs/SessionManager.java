package kytallo.com.themangadocs;
import android.content.Context;
import android.content.SharedPreferences;
public class SessionManager {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_USER_ID = "userId"; // Thêm key mới cho user ID
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    public SessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }
    public void setLogin(boolean isLoggedIn, String email, int userId) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.putString(KEY_EMAIL, email);
        editor.putInt("userId", userId);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }
    public String getEmail() {
        return pref.getString(KEY_EMAIL, "");
    }
    public void logout() {
        editor.clear();
        editor.apply();
    }
    public int getUserId() {
        return pref.getInt("userId", -1);
    }
}
