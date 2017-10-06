package com.wytings.route.module;

import java.io.Serializable;

/**
 * Created by rex on 06/10/2017.
 *
 * @author wytings@gmail.com
 */


public class ModuleSerializable implements Serializable {
    public String name = "defaultSerializable";
    public float value = 111;


    public ModuleSerializable() {

    }

    public ModuleSerializable(String name, float value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "ModuleSerializable{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
