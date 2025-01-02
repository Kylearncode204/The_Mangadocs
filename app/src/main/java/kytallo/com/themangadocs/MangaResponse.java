package kytallo.com.themangadocs;

import java.util.List;

public class MangaResponse {
    private List<Comic> data;
    private String status;
    private String message;

    public List<Comic> getData() {
        return data;
    }

    public void setData(List<Comic> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}