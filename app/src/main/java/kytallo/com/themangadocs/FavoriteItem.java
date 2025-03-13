package kytallo.com.themangadocs;

import java.util.ArrayList;
import java.util.List;

public class FavoriteItem {
    private String comicId;
    private String title;
    private String subtitle;

    public FavoriteItem(String comicId, String title, String subtitle) {
        this.comicId = comicId;
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getComicId() {
        return comicId;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    // Phương thức tạm để chuyển danh sách comicIds thành danh sách FavoriteItem
    // Trong thực tế, bạn có thể truy vấn dữ liệu chi tiết từ API hoặc database.
    public static List<FavoriteItem> fromComicIds(List<String> comicIds) {
        List<FavoriteItem> list = new ArrayList<>();
        for (String id : comicIds) {
            // Giả sử nếu không có dữ liệu chi tiết, sử dụng comicId làm title và hiển thị một thông báo mặc định cho subtitle
            list.add(new FavoriteItem(id, "Comic " + id, "Thông tin truyện chưa được cập nhật"));
        }
        return list;
    }
}
