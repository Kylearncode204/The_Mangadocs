package kytallo.com.themangadocs;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ComicAdapter adapter;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // Initialize adapter with an empty list
        recyclerView = findViewById(R.id.recyclerView);
        if (recyclerView != null) {
            GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
            recyclerView.setLayoutManager(layoutManager);
            int spacing = getResources().getDimensionPixelSize(R.dimen.grid_spacing);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, spacing, true));

            adapter = new ComicAdapter(new ArrayList<>(), comic -> {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("comic", comic);
                startActivity(intent);
            });
            recyclerView.setAdapter(adapter);
        } else {
            Log.e("MainActivity", "RecyclerView not found!");
        }
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home); // Mặc định chọn Trang chủ
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                return true;
            }
            else if (item.getItemId()== R.id.nav_favorites) {
                startActivity(new Intent(MainActivity.this, FavoritesActivity.class));
                return true;
            }
            else if (item.getItemId() == R.id.nav_profile) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                return true;
            }

            else if(item.getItemId()==R.id.nav_upload){
                Intent intent = new Intent(MainActivity.this, UploadStoryActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String lastCheckin = prefs.getString("lastCheckinDate", "");
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        if (!today.equals(lastCheckin)) {
            new CheckinDialog(this).show();
        }


        fetchComicsFromApi();
    }

    private void fetchComicsFromApi() {
        try {
            ComicApi comicApi = RetrofitClient.getInstance().create(ComicApi.class);
            Call<MangaResponse> call = comicApi.getComic();

            call.enqueue(new Callback<MangaResponse>() {
                @Override
                public void onResponse(Call<MangaResponse> call, Response<MangaResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Comic> comics = response.body().getData();
                        if (comics != null && !comics.isEmpty()) {
                            adapter.updateData(comics);
                            Log.d("API", "Loaded " + comics.size() + " comics");
                        } else {
                            Toast.makeText(MainActivity.this, "Không có dữ liệu truyện",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Lỗi khi tải dữ liệu: " +
                                response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MangaResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Lỗi kết nối: " +
                            t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("API", "Error: ", t);
                }
            });
        } catch (Exception e) {
            Log.e("MainActivity", "Error in fetchComicsFromApi: ", e);
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}
