/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Controller.loginHandler;
import java.util.Hashtable;

/**
 *
 * @author van hieu
 */
public class table {
        int num;
    String name;
    static Hashtable<String, loginHandler> hash = new Hashtable<>();

    public table() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void add(String key, loginHandler d) {
        hash.put(key, d);

    }
}
