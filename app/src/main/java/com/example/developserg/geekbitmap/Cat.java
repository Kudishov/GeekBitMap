package com.example.developserg.geekbitmap;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by DevelopSerg on 13.02.2017.
 */
public class Cat implements Parcelable {
    private String name;
    private String latLng;
    private int fotoId;
    private String type;
    private String description;

    public Cat() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatLng() {
        return latLng;
    }

    public void setLatLng(String latLng) {
        this.latLng = latLng;
    }

    public int getFoto() {
        return fotoId;
    }

    public void setFoto(int foto) {
        this.fotoId = foto;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeString(type);
        parcel.writeString(description);
        parcel.writeInt(fotoId);
        parcel.writeString(latLng);
    }

    public static final Parcelable.Creator<Cat> CREATOR = new Parcelable.Creator<Cat>() {
        public Cat createFromParcel(Parcel in) {
            return new Cat(in);
        }

        public Cat[] newArray(int size) {
            return new Cat[size];
        }
    };

    private Cat(Parcel parcel) {
        name = parcel.readString();
        type = parcel.readString();
        description = parcel.readString();
        fotoId = parcel.readInt();
        latLng = parcel.readString();
    }
}
