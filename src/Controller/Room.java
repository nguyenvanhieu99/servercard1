/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.ArrayList;
import model.Cards;
import model.Status;
import model.ThreeCard;
import model.serverSendObject;

/**
 *
 * @author van hieu
 */
public class Room implements Runnable{
    public ArrayList <loginHandler> allh;
    public int id;

    public Room(ArrayList<loginHandler> allh, int id) {
        this.allh = allh;
        this.id = id;
    }

    public ArrayList<loginHandler> getAllh() {
        return allh;
    }

    public void setAllh(ArrayList<loginHandler> allh) {
        this.allh = allh;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
    public boolean addLoginHandle(loginHandler lh){
        boolean add = allh.add(lh);
        return add;
        
    }

    public Room() {
        allh=new ArrayList<>();
    }
        
    
    
    public void close() {
        
    }

    @Override
    public void run() {
        
    }

    boolean checkvice() {
        if(allh.size()>1){
            return true;
        }
        return false;
            
    }
    public void can(Object o){
        
        serverSendObject sso=new serverSendObject(Command.CONTINUE, false, o);
        for(int i=0;i<allh.size();i++){
            allh.get(i).sendData(sso);
        }
    }
    void play() {
        Cards ca=new  Cards();
        ThreeCard thc;
        for(int i=0;i<allh.size();i++)    {
            thc=ca.getThreeCard();
            String term=thc.allnum();
            allh.get(i).getStatus().setThreecard(thc);
            allh.get(i).getStatus().setAllnum(term);
           // System.out.println(term);
            
        }
    }

    void sendall() {
        for(int i=0;i<allh.size();i++)    {
            allh.get(i).getStatus();
          //  allh.get(i).getStatus().setAllnum(term);
            
        }
    }
    public ArrayList<Object> getAllStatus(){
        ArrayList<Object> res=new ArrayList<>();
        for(int i=0;i<allh.size();i++){
          //log(allh.get(i).getStatus().getAllnum());
               res.add(allh.get(i).getStatus());
        }
        return res;
    }
    private void  log(String s){
        System.out.println(s);
    }
    

 
    
    
}
