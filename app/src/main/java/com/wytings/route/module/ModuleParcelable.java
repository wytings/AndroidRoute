package com.wytings.route.module;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rex on 06/10/2017.
 *
 * @author wytings@gmail.com
 */


public class ModuleParcelable implements Parcelable {
    public String name = "defaultParcelable";
    public float value = 222;

    public ModuleParcelable() {

    }

    public ModuleParcelable(String name, float value) {
        this.name = name;
        this.value = value;
    }

    protected ModuleParcelable(Parcel in) {
        name = in.readString();
        value = in.readFloat();
    }

    @Override
    public String toString() {
        return "ModuleParcelable{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    public static final Creator<ModuleParcelable> CREATOR = new Creator<ModuleParcelable>() {
        @Override
        public ModuleParcelable createFromParcel(Parcel in) {
            return new ModuleParcelable(in);
        }

        @Override
        public ModuleParcelable[] newArray(int size) {
            return new ModuleParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(value);
    }
}
