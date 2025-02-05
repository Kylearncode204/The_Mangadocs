package kytallo.com.themangadocs;

import android.os.Parcel;
import android.os.Parcelable;

public class Author implements Parcelable {
    private String id;
    private String name;
    private String bio;

    // Constructor
    public Author(String id, String name, String bio) {
        this.id = id;
        this.name = name;
        this.bio = bio;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    // Parcelable implementation
    protected Author(Parcel in) {
        id = in.readString();
        name = in.readString();
        bio = in.readString();
    }

    public static final Creator<Author> CREATOR = new Creator<Author>() {
        @Override
        public Author createFromParcel(Parcel in) {
            return new Author(in);
        }

        @Override
        public Author[] newArray(int size) {
            return new Author[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(bio);
    }
}
