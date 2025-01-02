package kytallo.com.themangadocs;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class ChapterAttributes implements Parcelable {
    private String title;
    private String chapter;
    private List<String> pages;

    protected ChapterAttributes(Parcel in) {
        title = in.readString();
        chapter = in.readString();
        pages = in.createStringArrayList();
    }

    public static final Creator<ChapterAttributes> CREATOR = new Creator<ChapterAttributes>() {
        @Override
        public ChapterAttributes createFromParcel(Parcel in) {
            return new ChapterAttributes(in);
        }

        @Override
        public ChapterAttributes[] newArray(int size) {
            return new ChapterAttributes[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public List<String> getPages() {
        return pages;
    }

    public void setPages(List<String> pages) {
        this.pages = pages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(chapter);
        dest.writeStringList(pages);
    }
}
