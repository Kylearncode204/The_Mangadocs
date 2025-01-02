package kytallo.com.themangadocs;

import android.os.Parcel;
import android.os.Parcelable;

public class Chapter implements Parcelable {
    private String id;
    private ChapterAttributes attributes;

    protected Chapter(Parcel in) {
        id = in.readString();
        attributes = in.readParcelable(ChapterAttributes.class.getClassLoader());
    }

    public static final Creator<Chapter> CREATOR = new Creator<Chapter>() {
        @Override
        public Chapter createFromParcel(Parcel in) {
            return new Chapter(in);
        }

        @Override
        public Chapter[] newArray(int size) {
            return new Chapter[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ChapterAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(ChapterAttributes attributes) {
        this.attributes = attributes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(attributes, flags);
    }
}
