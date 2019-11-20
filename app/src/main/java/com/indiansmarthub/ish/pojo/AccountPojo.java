package com.indiansmarthub.ish.pojo;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class AccountPojo {
    String name;
    Drawable image;

    public AccountPojo(Drawable image, String name) {

        this.name = name;
        this.image = image;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }


}
