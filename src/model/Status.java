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
public class Status implements Serializable {

    private String name;
    public String status;
    public int diem;
    public int id;
    private String allnum;
    private ThreeCard threecard;

    public Status(String name, String status, int diem, int id, String allnum, ThreeCard threecard) {
        this.name = name;
        this.status = status;
        this.diem = diem;
        this.id = id;
        this.allnum = allnum;
        this.threecard = threecard;
    }

    public Status(String name, String status, int diem) {
        this.name = name;
        this.status = status;
        this.diem = diem;
        this.id = 0;
        this.allnum = "1";
        this.threecard = new ThreeCard();

    }

    public void setAllnum(String allnum) {
        this.allnum = allnum;
    }

    public void setThreecard(ThreeCard threecard) {
        this.threecard = threecard;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status() {
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public int getDiem() {
        return diem;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDiem(int diem) {
        this.diem = diem;
    }

    public String getAllnum() {
        return this.allnum;
    }

    public ThreeCard getThreecard() {
        return this.threecard;
    }

    @Override
    public String toString() {
        return name + ":" + status + ":" + diem;
    }

    public String toString2() {
        return "Status{" + "name=" + name + ", status=" + status + ", diem=" + diem + ", id=" + id + ", allnum=" + allnum + ", threecard=" + threecard + '}';
    }
    public String toString3() {
        return  allnum +"" ;
    }
    public ThreeCard toString4() {
        return  this.threecard ;
    } 
}
