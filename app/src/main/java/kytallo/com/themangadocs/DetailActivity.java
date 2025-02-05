package kytallo.com.themangadocs;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import kytallo.com.themangadocs.utils.ImageUrlUtils;
public class DetailActivity extends AppCompatActivity {
    private Comic comic;
    private Button btnFavorite, btnFollowStory;
    private ImageView imgDetailCover;
    private TextView tvDetailTitle, tvDetailDescription;
    private Button btnRead;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Khai báo và gán các nút cần thiết
        btnFavorite = findViewById(R.id.btnFavoriteStory);
        btnFollowStory = findViewById(R.id.btnFollowStory);

        btnFavorite.setOnClickListener(v -> toggleFavorite());
        btnFollowStory.setOnClickListener(v -> toggleFollowStory());

        initViews();
        loadComicDetails();
    }

    private void initViews() {
        imgDetailCover = findViewById(R.id.imgDetailCover);
        tvDetailTitle = findViewById(R.id.tvDetailTitle);
        tvDetailDescription = findViewById(R.id.tvDetailDescription);
        btnRead = findViewById(R.id.btnRead);
        progressBar = findViewById(R.id.progressBar);
    }

    private void loadComicDetails() {
        // Lưu comic thành biến toàn cục để các phương thức khác sử dụng
        comic = getIntent().getParcelableExtra("comic");
        if (comic != null) {
            // Xử lý load ảnh bìa và các thông tin khác của truyện...
            String coverUrl = comic.getCoverImageUrl();
            if (coverUrl != null) {
                loadImage(coverUrl);
            } else {
                imgDetailCover.setImageResource(R.drawable.img);
            }

            if (comic.getAttributes() != null) {
                String title = comic.getAttributes().getTitle() != null ?
                        comic.getAttributes().getTitle().getEn() : "Không có tiêu đề";
                tvDetailTitle.setText(title);

                String description = comic.getAttributes().getDescription() != null ?
                        comic.getAttributes().getDescription().getEn() : "Không có mô tả";
                tvDetailDescription.setText(description);
            }

            btnRead.setOnClickListener(view -> {
                Intent intent = new Intent(DetailActivity.this, ReaderActivity.class);
                intent.putExtra("comic", comic);
                startActivity(intent);
            });

            // Cập nhật trạng thái của nút yêu thích (nếu người dùng đã đăng nhập)
            updateFavoriteButtonState();
        } else {
            Toast.makeText(this, "Không thể tải thông tin truyện", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void toggleFavorite() {
        if (!new SessionManager(this).isLoggedIn()) {
            Toast.makeText(this, "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
            return;
        }
        // Lấy userId – Lưu ý: cần thêm phương thức getUserId() trong SessionManager
        int userId = new SessionManager(this).getUserId();
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        if (dbHelper.isFavorite(userId, comic.getId())) {
            dbHelper.removeFavorite(userId, comic.getId(), false);
            btnFavorite.setText("Yêu thích");
        } else {
            dbHelper.addFavorite(userId, comic.getId());
            btnFavorite.setText("Đã thích");
        }
    }

    private void updateFavoriteButtonState() {
        if (new SessionManager(this).isLoggedIn()) {
            int userId = new SessionManager(this).getUserId();
            boolean isFavorite = new DatabaseHelper(this).isFavorite(userId, comic.getId());
            btnFavorite.setText(isFavorite ? "Đã thích" : "Yêu thích");
        }
    }

    private void toggleFollowStory() {
        // Giả sử bạn đã bổ sung chức năng theo dõi truyện trong DatabaseHelper
        if (!new SessionManager(this).isLoggedIn()) {
            Toast.makeText(this, "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
            return;
        }
        int userId = new SessionManager(this).getUserId();
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        if (dbHelper.isFollowedStory(userId, comic.getId())) { // Phương thức cần được cài đặt
            dbHelper.removeFollowStory(userId, comic.getId());
            btnFollowStory.setText("Theo dõi");
        } else {
            dbHelper.addFollowStory(userId, comic.getId());
            btnFollowStory.setText("Đang theo dõi");
        }
    }

    private void loadImage(String coverUrl) {
        progressBar.setVisibility(View.VISIBLE);
        Picasso.get()
                .load(coverUrl)
                .placeholder(R.drawable.img)
                .error(R.drawable.img)
                .into(imgDetailCover, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }
                    @Override
                    public void onError(Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(DetailActivity.this, "Không thể tải ảnh", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}