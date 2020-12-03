/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author van hieu
 */
public class Stringsend implements Serializable{
    private String k[];
    private int so;
    private ThreeCard tt[];

    public Stringsend(String[] k) {
        this.k = k;
    }

    public Stringsend(String[] k, int so, ThreeCard[] tt) {
        this.k = k;
        this.so = so;
        this.tt = tt;
    }
    
    public Stringsend(String[] k, int so) {
        this.k = k;
        this.so = so;
    }

    public Stringsend() {
    }

    public String[] getK() {
        return k;
    }

    public int getSo() {
        return so;
    }

    public void setK(String[] k) {
        this.k = k;
    }

    public void setSo(int so) {
        this.so = so;
    }

    public ThreeCard[] getTt() {
        return tt;
    }

    public void setTt(ThreeCard[] tt) {
        this.tt = tt;
    }
    
}
