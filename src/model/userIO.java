/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hoa
 */
public class userIO {
    String url = "E:\\PTTK\\login_register\\user.txt";
    public void WriteFile( User us) throws FileNotFoundException, IOException{
        FileOutputStream f = new FileOutputStream(url);
        ObjectOutputStream oos = new ObjectOutputStream(f);
        oos.writeObject(us);
        oos.close();
    }
    public List  <User> ReadFile() throws FileNotFoundException, IOException, ClassNotFoundException{
        User us ;
        ArrayList  userList  = new ArrayList <User> ();
        FileInputStream f = new FileInputStream(url);
        ObjectInputStream ois = new ObjectInputStream(f);
        while( ois.available() > 0 ){
            us = (User) ois.readObject();
            userList.add(us);
        }
        return userList;
    }
}
