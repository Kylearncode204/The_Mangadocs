package kytallo.com.themangadocs;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AuthorDetailActivity extends AppCompatActivity {
    private Author author; // Giả sử có lớp Author với thuộc tính id, tên, tiểu sử,…
    private Button btnFollowAuthor, btnFavoriteAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_author_detail);

        btnFollowAuthor = findViewById(R.id.btnFollowAuthor);
        btnFavoriteAuthor = findViewById(R.id.btnFavoriteAuthor);

        // Lấy dữ liệu tác giả từ Intent (cần implement lớp Author)
        author = getIntent().getParcelableExtra("author");

        btnFollowAuthor.setOnClickListener(v -> toggleFollowAuthor());
        btnFavoriteAuthor.setOnClickListener(v -> toggleFavoriteAuthor());
        // Hiển thị thông tin tác giả lên layout...
    }

    private void toggleFollowAuthor() {
        if (!new SessionManager(this).isLoggedIn()) {
            Toast.makeText(this, "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
            return;
        }
        int userId = new SessionManager(this).getUserId();
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        if (dbHelper.isAuthorFavorite(userId, author.getId())) {
            dbHelper.removeFavorite(userId, author.getId(), true);
            btnFollowAuthor.setText("Theo dõi");
        } else {
            dbHelper.addAuthorFavorite(userId, author.getId());
            btnFollowAuthor.setText("Đang theo dõi");
        }
    }

    private void toggleFavoriteAuthor() {
        // Tương tự, nếu bạn muốn tách riêng chức năng "yêu thích tác giả"
        // Bạn có thể dùng cùng các phương thức hoặc tạo riêng bảng nếu cần.
    }
}
