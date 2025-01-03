package kytallo.com.themangadocs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import kytallo.com.themangadocs.R;

public class UploadStoryActivity extends AppCompatActivity {

    private EditText etTitle, etAuthor, etDescription;
    private RadioGroup rgStoryType;
    private Button btnSelectCover, btnUploadContent, btnSubmit;
    private ImageView ivCoverPreview;
    private Uri coverUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_story);

        // Khởi tạo các thành phần giao diện
        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        etDescription = findViewById(R.id.etDescription);
        rgStoryType = findViewById(R.id.rgStoryType);
        btnSelectCover = findViewById(R.id.btnSelectCover);
        btnUploadContent = findViewById(R.id.btnUploadContent);
        btnSubmit = findViewById(R.id.btnSubmit);
        ivCoverPreview = findViewById(R.id.ivCoverPreview);

        // Xử lý thay đổi loại truyện
        rgStoryType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbManga) {
                etDescription.setVisibility(View.GONE);
                btnUploadContent.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.rbNovel) {
                etDescription.setVisibility(View.VISIBLE);
                btnUploadContent.setVisibility(View.GONE);
            }
        });

        // Chọn ảnh bìa
        btnSelectCover.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 101);
        });

        // Nút đăng truyện
        btnSubmit.setOnClickListener(v -> submitStory());
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

    private void submitStory() {
        String title = etTitle.getText().toString().trim();
        String author = etAuthor.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        boolean isManga = rgStoryType.getCheckedRadioButtonId() == R.id.rbManga;

        if (title.isEmpty() || author.isEmpty() || coverUri == null) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isManga && !btnUploadContent.isShown()) {
            Toast.makeText(this, "Vui lòng tải lên nội dung truyện tranh", Toast.LENGTH_SHORT).show();
            return;
        }

        // Xử lý gửi dữ liệu lên server hoặc lưu vào database
        Toast.makeText(this, "Truyện đã được đăng thành công!", Toast.LENGTH_SHORT).show();

        // Reset giao diện sau khi đăng truyện
        resetForm();
    }

    private void resetForm() {
        etTitle.setText("");
        etAuthor.setText("");
        etDescription.setText("");
        ivCoverPreview.setVisibility(View.GONE);
        rgStoryType.check(R.id.rbManga);
        btnUploadContent.setVisibility(View.VISIBLE);
    }
}
