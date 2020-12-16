/*
 * To change this license header, choose License Headers in Pr  oject Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Controller.Command.LOGIN;
import static Controller.Command.REGISTER;
import static Controller.server.dem;
import DAO.UserDAO;
import model.Status;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Thread.sleep;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.objects.NativeDate;
import model.Stringsend;
import model.ThreeCard;
import model.User;
import model.serverSendObject;
import model.userIO;
import model.table;
import model.userSendObject;

/**
 *
 * @author van hieu
 *
 */
public class loginHandler implements Runnable {

    static Hashtable<String, table> listtable;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    Status status;
    Room r;
    static ArrayList<loginHandler> al = new ArrayList<>();
    static ArrayList<Room> listroom = new ArrayList<>();
    static int id = 0;

    public Status getStatus() {
        return status;
    }
    static int dem = 0;
    public int soban;
    Socket s;
    public boolean islogin;

    public loginHandler(int dem, Socket s) throws IOException, IOException {
        this.id = dem;
        this.s = s;
        this.islogin = true;
        //this.status = sta;
    }

    @Override
    public void run() {
        try {
            ois = new ObjectInputStream(s.getInputStream());
            oos = new ObjectOutputStream(s.getOutputStream());
            String received = null;
            while (true) {
                int so = -1;
                // Object obj = dis.readObject();
                userSendObject uso = ReceiveData();
                so = uso.getCommand();

                if (so == LOGIN) {

                    User o = (User) uso.getObject();
                    //log(o.getUserName() + " : " + o.getPassWord());
                    try {
                        
                        if (checkUser(o)) {
                            serverSendObject d = new serverSendObject(LOGIN, true, o);
                            sendData(d);
                            log("da gui");
                            UserDAO udao=new UserDAO();
                           int di= udao.getdem(o.getUserName());
                                    
                            Status sta = new Status(o.getUserName(), "wating", di);
                            sta.setId(dem);
                            this.status = sta;
                            al.add(this);
                            ++dem;

                        } else {

                            serverSendObject d = new serverSendObject(LOGIN, false, o);
                            sendData(d);
                        }
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(loginHandler.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(loginHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else if (so == Command.REGISTER) {
                    User o = (User) uso.getObject();
                    if (register(o)) {
                        serverSendObject d = new serverSendObject(REGISTER, true, o);
                        sendData(d);
                    } else {
                        serverSendObject d = new serverSendObject(REGISTER, false, o);
                        sendData(d);
                    }

                    //registerHandler re= new registerHandler(s);
                } else if (so == Command.CHALLENGE) {
                    r = new Room();
                    r.setId(getStatus().getId());
                    r.addLoginHandle(this);

                    Status t = (Status) uso.getObject();
                    serverSendObject sso;
                    listroom.add(r);

                    for (int i = 0; i < al.size(); i++) {
                        if (al.get(i).getStatus().getName().equals(t.getName())) {
                            sso = new serverSendObject(Command.invite, true, this.status);
                            al.get(i).sendData(sso);
                            log(sso.toString());

                        }
                    }

                } else if (so == Command.CREATTABLE) {
                    r = new Room();
                    r.setId(getStatus().getId());
                    r.addLoginHandle(this);

                    //Status t = (Status) uso.getObject();
                    serverSendObject sso;
                    listroom.add(r);

                } else if (so == Command.JOINTABLE) {
                    Status t = (Status) uso.getObject();
                    int b = Integer.parseInt(t.getName());
                    int term=0;
                    for(int i=0;i<listroom.size();i++){
                        if(listroom.get(i).getId()==b){
                            term=i;
                        }
                    }
                    boolean addLoginHandle = listroom.get(term).addLoginHandle(this);
                    ArrayList<Object> st1 = listroom.get(term).getAllStatus();
                    serverSendObject ss1 = new serverSendObject(Command.JOINTABLE, true,(Object)t,st1);
                    for (int i = 0; i < listroom.get(term).getAllh().size(); i++) {
                        System.out.println("Controller.loginHandler.run()");
                        listroom.get(term).getAllh().get(i).sendData(ss1);
                        

                    }

                } else if (so == Command.LOGOUT) {

                    this.islogin = false;
                    //this.s.close();
                    break;

                } else if (so == Command.LOGINDONE) {
                    User o = (User) uso.getObject();
//                    UserDAO udao=new UserDAO();
//                    this.getStatus().setDiem(udao.getdem(o.getUserName()));
                    //ArrayList<Status> all = getAllclient();
                    sendAllClient(o);
                    sendAlltable(o);
                    sendAllUser(o);
                } else if (so == Command.RANK) {
                    getRank();

                } else if (so == Command.invite) {
                    int term = -1;
                    Status k = (Status) uso.getObject();
                    serverSendObject sso = new serverSendObject(Command.CHALLENGE, true, k);
                    for (int j = 0; j < al.size(); j++) {
                        if (al.get(j).getStatus().getId() == k.getId()) {
                            term = j;
                        }
                    }
                    if (uso.isCheck()) {

                        for (int i = 0; i < listroom.size(); i++) {
                            log(listroom.get(i).getId() + "+" + k.getId());
                            if (listroom.get(i).getId() == k.getId()) {
                                //log("co thay");
                                soban = i;
                                listroom.get(i).addLoginHandle(this);
                                al.get(term).sendData(sso);
                            }
                        }
                        //log("thoat for ");
                    } else {
                        // log("khong dong y");
                        al.get(term).sendData(new serverSendObject(Command.CHALLENGE, false, k));

                    }
                } else if (so == Command.PLAY) {
//                    User o = (User) uso.getObject();
//                    User os = new User("sas", "mk", "@gami");
                    r.play();
                    ArrayList<Object> st1 = r.getAllStatus();

                    UserDAO udao = new UserDAO();
                    String[] f = new String[4];
                    ThreeCard[] tc = new ThreeCard[4];
                    for (int i = 0; i < st1.size(); i++) {
                        Status stss = (Status) st1.get(i);
                        f[i] = "" + stss.toString3();
                        tc[i] = stss.toString4();
                        udao.update(tc[i].getTt(), stss.getName());
                        r.getAllh().get(i).getStatus().setDiem(udao.getdem(stss.getName()));

                    }
                    Stringsend stsen = new Stringsend(f, so, tc);
                    serverSendObject ss1 = new serverSendObject(Command.PLAY, true, stsen, st1);
                    for (int i = 0; i < r.getAllh().size(); i++) {

                        r.getAllh().get(i).sendData(ss1);
                       

                    }
                } else if (so == Command.CONTINUE) {
                    if (uso.isCheck() || listroom.size() == 0) {
                        log("choi tiep");
                    } else {
                        boolean rt = true;
                        for (int i = 0; i < listroom.get(soban).getAllh().size(); i++) {
                            if (listroom.get(soban).getAllh().get(i).getStatus().getId() == this.getStatus().getId()) {
                                listroom.get(soban).can(so);
                                listroom.remove(soban);
                                break;
                            }
                        }

                    }
                }

            }

            try {
                for (int i = 0; i < al.size(); i++) {
                    if (al.get(i).getStatus().getId() == getStatus().getId()) {
                        al.remove(i);
                    }

                }
                //s.close();
                this.ois.close();
                this.oos.close();
            } catch (IOException ex) {
                Logger.getLogger(loginHandler.class.getName()).log(Level.SEVERE, null, ex);
                log("khong dong duoc ");
            }

        } catch (IOException ex) {
            for (int i = 0; i < al.size(); i++) {
                if (al.get(i).getStatus().getId() == getStatus().getId()) {
                    al.remove(i);
                }

            }

            Logger.getLogger(loginHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(loginHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(loginHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ArrayList<Object> getAlltable() {
        return null;
    }

    public ArrayList<Object> getRank() {
        return null;
    }

    public void joinTable() {

    }

    public void play() {

    }

    public void creatTable() {

    }

    public userSendObject ReceiveData() {
        userSendObject om = new userSendObject();
        try {

            om = (userSendObject) ois.readObject();

            return om;
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return om;
    }

    public void sendData(serverSendObject orm) {
        try {

            ArrayList<Object> ao = orm.getList();

            //System.out.println(d);
            if (orm.getCommand() == Command.PLAY) {

                ArrayList<Status> as = new ArrayList<Status>();
                for (int i = 0; i < ao.size(); i++) {
                    Status k = (Status) ao.get(i);
                    log(k.toString2());

                }
            }
            oos.writeObject(orm);
            String k = orm.getCommand() + " ";
            log(k);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void log(String mess) {
        System.out.println("log: " + mess);
    }

    public ArrayList<Object> getAllclient() {

        //ArrayList<loginHandler> sd;
        //sd = server.getArr();
        ArrayList<Object> as = new ArrayList<>();
        for (int i = 0; i < al.size(); i++) {
            as.add(al.get(i).getStatus());
        }
        return as;

    }

    public ArrayList<Object> getAllRoom() {

        //ArrayList<loginHandler> sd;
        //sd = server.getArr();
        ArrayList<Object> as = new ArrayList<>();
        Room r;
        for (int i = 0; i < listroom.size(); i++) {
            String k = "";
            r = listroom.get(i);
            k = r.getId() + ":" + r.getAllh().get(0).getStatus().getName() + ":" + r.getAllh().size();
            as.add(k);
        }
        return as;

    }

    private void sendAlltable(User o) {
        ArrayList<Object> as = getAllRoom();
        serverSendObject sso;
        sso = new serverSendObject(Command.GETALLTABLE, true, o, as);
        sendData(sso);
    }

    private void sendAllClient(User o) {
        
        ArrayList<Object> as = getAllclient();
        serverSendObject sso;
        sso = new serverSendObject(Command.GETALLCLIENT, true, o, as);
        sendData(sso);
        
    }

    private boolean checkUser(User user) throws ClassNotFoundException, SQLException {
        
        UserDAO contr = new UserDAO();
        for(int i=0;i<al.size();i++){
            if(user.getUserName().equals(al.get(id).getStatus())) return false;
        }
        if (contr.checkLogin(user)) {
            return true;
        } else {
            return false;
        }
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean register(User o) throws ClassNotFoundException, SQLException {
        UserDAO udao = new UserDAO();

        if (udao.register(o)) {
            log("dung roi");
            return true;
        }
        log("sai roi");
        return false;

    }

    private void sendAllUser(User o) {
        UserDAO udao=new UserDAO();
        
        ArrayList<Status> as = udao.getAlluser();
        ArrayList<Object> ass=new ArrayList<>();
        for(int i=0;i<as.size();i++){
            Object d=as.get(i);
            ass.add(d);
        }
        serverSendObject sso;
        sso = new serverSendObject(Command.RANK, true, o, ass);
        sendData(sso);
    }

}
