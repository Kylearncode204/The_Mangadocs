package kytallo.com.themangadocs;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class ReaderActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ReaderAdapter adapter;
    private ProgressBar progressBar;
    private ComicApi comicApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        initViews();
        setupApi();
        loadChapters();
    }

    private void initViews() {
        viewPager = findViewById(R.id.viewPager);
        progressBar = findViewById(R.id.progressBar);
        adapter = ReaderAdapter.withRemoteUrls(this, new ArrayList<>());
        viewPager.setAdapter(adapter);
    }

    private void setupApi() {
        comicApi = RetrofitClient.getInstance().create(ComicApi.class);
    }

    private void loadChapters() {
        Comic comic = getIntent().getParcelableExtra("comic");
        if (comic == null) {
            showError("Không thể tải dữ liệu truyện");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        comicApi.getChapters(comic.getId()).enqueue(new Callback<ChapterResponse>() {
            @Override
            public void onResponse(Call<ChapterResponse> call, Response<ChapterResponse> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().getData().isEmpty()) {
                    // Lấy chapter đầu tiên
                    Chapter firstChapter = response.body().getData().get(0);
                    loadChapterPages(firstChapter.getId());
                } else {
                    showError("Không tìm thấy chapter nào");
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ChapterResponse> call, Throwable t) {
                showError("Lỗi khi tải chapters: " + t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void loadChapterPages(String chapterId) {
        comicApi.getChapterPages(chapterId).enqueue(new Callback<ChapterPagesResponse>() {
            @Override
            public void onResponse(Call<ChapterPagesResponse> call, Response<ChapterPagesResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    ChapterPagesResponse pagesResponse = response.body();
                    List<String> imageUrls = buildImageUrls(pagesResponse);
                    adapter.updateWithRemoteUrls(imageUrls);
                } else {
                    showError("Không thể tải nội dung chapter");
                }
            }

            @Override
            public void onFailure(Call<ChapterPagesResponse> call, Throwable t) {
                showError("Lỗi khi tải trang: " + t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private List<String> buildImageUrls(ChapterPagesResponse response) {
        List<String> urls = new ArrayList<>();
        String baseUrl = response.getBaseUrl();
        String hash = response.getChapter().getHash();
        List<String> pageFilenames = response.getChapter().getData();

        for (String filename : pageFilenames) {
            String url = String.format("%s/data/%s/%s", baseUrl, hash, filename);
            urls.add(url);
        }
        return urls;
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }
}
