package kytallo.com.themangadocs;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

public class Comic implements Parcelable {
    private String id;
    private Attributes attributes;

    public Comic() {
    }

    protected Comic(Parcel in) {
        id = in.readString();
        attributes = in.readParcelable(Attributes.class.getClassLoader());
    }

    public static final Creator<Comic> CREATOR = new Creator<Comic>() {
        @Override
        public Comic createFromParcel(Parcel in) {
            return new Comic(in);
        }

        @Override
        public Comic[] newArray(int size) {
            return new Comic[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(attributes, flags);
    }
    public String getCoverImageUrl() {
        if (attributes != null && attributes.getCoverImage() != null && id != null) {
            return "https://uploads.mangadex.org/covers/" + id + "/" + attributes.getCoverImage();
        }
        return null;
    }




    public static class Attributes implements Parcelable {
        private Title title;
        private Description description;
        private String coverImage;

        protected Attributes(Parcel in) {
            title = in.readParcelable(Title.class.getClassLoader());
            description = in.readParcelable(Description.class.getClassLoader());
            coverImage = in.readString();
        }

        public static final Creator<Attributes> CREATOR = new Creator<Attributes>() {
            @Override
            public Attributes createFromParcel(Parcel in) {
                return new Attributes(in);
            }

            @Override
            public Attributes[] newArray(int size) {
                return new Attributes[size];
            }
        };

        public Title getTitle() {
            return title;
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Description getDescription() {
            return description;
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public String getCoverImage() {
            return coverImage;
        }

        public void setCoverImage(String coverImage) {
            this.coverImage = coverImage;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(title, flags);
            dest.writeParcelable(description, flags);
            dest.writeString(coverImage);
        }

        public static class Title implements Parcelable {
            private String en;

            protected Title(Parcel in) {
                en = in.readString();
            }

            public static final Creator<Title> CREATOR = new Creator<Title>() {
                @Override
                public Title createFromParcel(Parcel in) {
                    return new Title(in);
                }

                @Override
                public Title[] newArray(int size) {
                    return new Title[size];
                }
            };

            public String getEn() {
                return en;
            }

            public void setEn(String en) {
                this.en = en;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(en);
            }
        }

        public static class Description implements Parcelable {
            private String en;

            protected Description(Parcel in) {
                en = in.readString();
            }

            public static final Creator<Description> CREATOR = new Creator<Description>() {
                @Override
                public Description createFromParcel(Parcel in) {
                    return new Description(in);
                }

                @Override
                public Description[] newArray(int size) {
                    return new Description[size];
                }
            };

            public String getEn() {
                return en;
            }

            public void setEn(String en) {
                this.en = en;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(en);
            }
        }
    }
}
