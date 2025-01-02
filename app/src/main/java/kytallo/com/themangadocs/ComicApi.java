package kytallo.com.themangadocs;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ComicApi {
    @GET("manga")
    Call<MangaResponse> getComic();

    @GET("manga/{mangaId}/feed")
    Call<ChapterResponse> getChapters(@Path("mangaId") String mangaId);

    @GET("at-home/server/{chapterId}")
    Call<ChapterPagesResponse> getChapterPages(@Path("chapterId") String chapterId);
}