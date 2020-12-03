/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//di nguoi lai voi quyen loi 
package Controller;

import model.Status;
import DAO.UserDAO;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import model.userSendObject;
import model.serverSendObject;

/**
 *
 * @author van hieu
 *
 *
 *
 */
public class server implements Command {

    public ServerSocket control;
    public ServerSocket session;
    public Socket s;
    static ArrayList<loginHandler> arr = new ArrayList();
    static int dem = 0;

    public server() throws IOException {
        control = new ServerSocket(8080);
        new Thread(() -> {
            while (true) {
                try {
                    Socket s=control.accept();
                    Thread lh=new Thread(new loginHandler(dem, s));
                    lh.start();
                } catch (IOException ex) {
                    Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 

        }).start();
    }

   


    public static ArrayList<loginHandler> getArr() {
        return arr;
    }

    public void log(String s) {
        System.out.println(s);
    }


}
