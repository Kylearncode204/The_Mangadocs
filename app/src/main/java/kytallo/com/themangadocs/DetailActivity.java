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
    private ImageView imgDetailCover;
    private TextView tvDetailTitle, tvDetailDescription;
    private Button btnRead;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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
        Comic comic = getIntent().getParcelableExtra("comic");
        if (comic != null) {
            String coverUrl = comic.getCoverImageUrl();
            if (coverUrl != null) {
                Log.d("DetailActivity", "Loading cover image: " + coverUrl);
                loadImage(coverUrl);
            } else {
                imgDetailCover.setImageResource(R.drawable.img);
            }

            // Hiển thị thông tin khác của truyện
            if (comic.getAttributes() != null) {
                if (comic.getAttributes().getTitle() != null) {
                    String title = comic.getAttributes().getTitle().getEn();
                    tvDetailTitle.setText(title != null ? title : "Không có tiêu đề");
                }

                if (comic.getAttributes().getDescription() != null) {
                    String description = comic.getAttributes().getDescription().getEn();
                    tvDetailDescription.setText(description != null ? description : "Không có mô tả");
                }
            }

            // Set up nút đọc truyện
            btnRead.setOnClickListener(view -> {
                Intent intent = new Intent(DetailActivity.this, ReaderActivity.class);
                intent.putExtra("comic", comic);
                startActivity(intent);
            });
        } else {
            Toast.makeText(this, "Không thể tải thông tin truyện", Toast.LENGTH_SHORT).show();
            finish();
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
                        Log.d("DetailActivity", "Image loaded successfully");
                    }

                    @Override
                    public void onError(Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("DetailActivity", "Error loading image: " + e.getMessage());
                        Toast.makeText(DetailActivity.this, "Không thể tải ảnh", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    }
