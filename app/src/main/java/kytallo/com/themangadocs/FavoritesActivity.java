package kytallo.com.themangadocs;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFavorites;
    private FavoritesAdapter adapter;
    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;
    private List<FavoriteItem> favoriteItems;  // Mỗi mục sẽ chứa thông tin chi tiết

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        recyclerViewFavorites = findViewById(R.id.recyclerViewFavorites);
        recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);
        int userId = sessionManager.getUserId();

        // Lấy danh sách comic IDs mà user đã yêu thích
        // Ví dụ: Nếu bạn chỉ lưu comic_id, bạn có thể tạo đối tượng FavoriteItem để chứa thông tin
        List<String> comicIds = dbHelper.getFavoriteComicIds(userId);
        // Ở đây, mình giả sử bạn có thể lấy thêm thông tin chi tiết từ API hay cache;
        // ví dụ tạo danh sách FavoriteItem với title và subtitle.
        favoriteItems = FavoriteItem.fromComicIds(comicIds);

        adapter = new FavoritesAdapter(favoriteItems);
        recyclerViewFavorites.setAdapter(adapter);
    }
}
