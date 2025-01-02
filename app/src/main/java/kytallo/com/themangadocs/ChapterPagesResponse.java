package kytallo.com.themangadocs;

import java.util.List;

public class ChapterPagesResponse {
    private String baseUrl;
    private ChapterData chapter;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public ChapterData getChapter() {
        return chapter;
    }

    public void setChapter(ChapterData chapter) {
        this.chapter = chapter;
    }

    public static class ChapterData {
        private String hash;
        private List<String> data; // image filenames
        private List<String> dataSaver; // compressed image filenames

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public List<String> getData() {
            return data;
        }

        public void setData(List<String> data) {
            this.data = data;
        }

        public List<String> getDataSaver() {
            return dataSaver;
        }

        public void setDataSaver(List<String> dataSaver) {
            this.dataSaver = dataSaver;
        }
    }
}
