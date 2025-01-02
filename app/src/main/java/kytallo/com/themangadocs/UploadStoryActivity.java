package kytallo.com.themangadocs;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UploadStoryActivity extends AppCompatActivity {
    private EditText etTitle, etAuthor, etGenre, etDescription;
    private ImageView ivCoverPreview;
    private Uri coverUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_story);

        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        etGenre = findViewById(R.id.etGenre);
        etDescription = findViewById(R.id.etDescription);
        ivCoverPreview = findViewById(R.id.ivCoverPreview);

        findViewById(R.id.btnSelectCover).setOnClickListener(v -> selectImage());
        findViewById(R.id.btnSubmit).setOnClickListener(v -> uploadStory());
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            coverUri = data.getData();
            ivCoverPreview.setVisibility(View.VISIBLE);
            ivCoverPreview.setImageURI(coverUri);
        }
    }

    private void uploadStory() {
        String title = etTitle.getText().toString();
        String author = etAuthor.getText().toString();
        String genre = etGenre.getText().toString();
        String description = etDescription.getText().toString();

        if (title.isEmpty() || author.isEmpty() || genre.isEmpty() || coverUri == null) {
            Toast.makeText(this, "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Upload thông tin truyện lên server hoặc lưu vào database
        // Gửi coverUri dưới dạng hình ảnh hoặc đường dẫn
        Toast.makeText(this, "Đăng truyện thành công!", Toast.LENGTH_SHORT).show();
    }
}
