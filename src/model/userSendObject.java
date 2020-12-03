/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author van hieu
 */
public class userSendObject implements Serializable{

    private int command;
    private boolean check;
    private Object object;
    ArrayList<Object> list;

    public userSendObject(int command, boolean check, Object object) {
        this.command = command;
        this.check = check;
        this.object = object;
    }
    
    public userSendObject() {
    }

    public int getCommand() {
        return command;
    }

    public boolean isCheck() {
        return check;
    }

    public Object getObject() {
        return object;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public ArrayList<Object> getList() {
        return list;
    }

    public void setList(ArrayList<Object> list) {
        this.list = list;
    }

}
