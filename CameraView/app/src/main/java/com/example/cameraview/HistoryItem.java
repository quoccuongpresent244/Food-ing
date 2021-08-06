package com.example.cameraview;

public class HistoryItem {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    private int id;
    private byte[] image;

    public HistoryItem(int id, byte[] image) {
        this.id = id;
        this.image = image;
    }
}
