package kytallo.com.themangadocs.utils;

import android.util.Log;

public class ImageUrlUtils {
    private static final String BASE_URL = "https://uploads.mangadex.org/covers/";
    private static final String API_BASE_URL = "https://api.mangadex.org/";

    public static String getFullCoverUrl(String mangaId, String coverFileName) {
        if (mangaId == null || coverFileName == null) {
            Log.w("ImageUrlUtils", "mangaId or coverFileName is null");
            return null;
        }
        String url = BASE_URL + mangaId + "/" + coverFileName;
        Log.d("ImageUrlUtils", "Generated URL: " + url);
        return url;
    }


    public static String getApiUrl(String endpoint) {
        return API_BASE_URL + endpoint;
    }

    public static boolean isValidUrl(String url) {
        return url != null && !url.isEmpty() &&
                (url.startsWith("http://") || url.startsWith("https://"));
    }

    public static String getDefaultImageUrl() {
        // URL ảnh mặc định khi không có ảnh
        return "https://via.placeholder.com/300x400";
    }
}
