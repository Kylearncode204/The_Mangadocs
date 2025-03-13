package kytallo.com.themangadocs;



import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private SessionManager sessionManager;
    private DatabaseHelper databaseHelper;
    private TextView tvUsername, tvEmail;
    private RecyclerView rvRecentActivity;
    private Button btnEditProfile, btnLogout;
    private BottomNavigationView bottomNavigationView;
    private TextView tvReadCount, tvReadingCount, tvPlanToReadCount;
    private RecyclerView rvUploadedStories;
    private ComicAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sessionManager = new SessionManager(this);
        databaseHelper = new DatabaseHelper(this);

        initViews();
        setupNavigationView();
        loadUserData();
        setupButtons();
    }

    private void initViews() {
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        rvRecentActivity = findViewById(R.id.rvRecentActivity);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnLogout = findViewById(R.id.btnLogout);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        tvReadCount = findViewById(R.id.tvReadCount);
        tvReadingCount = findViewById(R.id.tvReadingCount);
        tvPlanToReadCount = findViewById(R.id.tvPlanToReadCount);

        rvRecentActivity.setLayoutManager(new LinearLayoutManager(this));
        rvUploadedStories = findViewById(R.id.rvUploadedStories);
        rvUploadedStories.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupNavigationView() {
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_profile) {
                // Đã ở trang profile
                return true;
            }
            return false;
        });
    }

    private void loadUserData() {
        // Lấy email từ session
        String email = sessionManager.getEmail();
        tvEmail.setText(email);

        // Lấy thông tin user từ database
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(
                "users",
                new String[]{"username"},
                "email = ?",
                new String[]{email},
                null, null, null
        );
        if (cursor.moveToFirst()) {
            String username = cursor.getString(0);
            if (username != null) {
                tvUsername.setText(username);
            } else {
                tvUsername.setText("No username found");
            }
        }

        if (cursor.moveToFirst()) {
            String username = cursor.getString(0);
            tvUsername.setText(username);
        }
        // Trong loadUserData()
        List<Comic> uploadedStories = databaseHelper.getUploadedStories(sessionManager.getUserId());
        adapter = new ComicAdapter(uploadedStories, comic -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("comic", comic);
            startActivity(intent);
        });


// Cập nhật thống kê
        tvReadCount.setText(String.valueOf(databaseHelper.getReadCount(sessionManager.getUserId())));
        cursor.close();
        rvUploadedStories.setAdapter(adapter);

        // Set số liệu thống kê (có thể thay đổi theo logic của ứng dụng)
        tvReadCount.setText("0");
        tvReadingCount.setText("0");
        tvPlanToReadCount.setText("0");
    }

    private void setupButtons() {
        btnLogout.setOnClickListener(v -> {
            sessionManager.logout();
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        btnEditProfile.setOnClickListener(v -> {
            // Có thể thêm màn hình EditProfile sau này
            Toast.makeText(this, "Tính năng đang được phát triển", Toast.LENGTH_SHORT).show();
        });
    }
}

