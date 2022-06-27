//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.emojiapp;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;

import java.io.Serializable;

public class Emoji implements Serializable {
    private String emojiName;
    private Uri uri;
    public int emoji;
    private int type;
    private Bitmap image;

    public Emoji(int emo, String name) throws NullPointerException {

        if (name != null & name.length()<35) {
            this.emojiName = name;
            this.emoji = emo;

        } else {
            throw new IllegalArgumentException("Emoji name is invalid");
        }}


    protected void setUri(Uri uri)
    {
        this.uri = uri;
    }
    protected Uri getUri()
    {
        return uri;
    }
    public int getEmoji() {
        return this.emoji;
    }

    public String getEmojiName() {
        return this.emojiName;
    }
}
