/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Status;

/**
 *
 * @author Admin
 */
public class UserDAO extends DAO {

    public UserDAO() {
        super();
    }

    public boolean register(User u) throws ClassNotFoundException, SQLException {
        String s = u.getUserName();
        if (findbyname(s)) {
            
            return false;
            
        }
        String checklogin = "INSERT INTO user(username,password,email) VALUES (?,?,?)";
        PreparedStatement p = getConnect().prepareStatement(checklogin);
        p.setString(1, s);
        p.setString(2, u.getPassWord());
        p.setString(3, u.getEmail());
        //boolean td = p.execute();
        int t = p.executeUpdate();
        if (t == 1) {
            
            
            return true;
            
        } else {
            return false;
        }

    }

    public boolean findbyname(String s) throws ClassNotFoundException, SQLException {
        String query = "SELECT * FROM user WHERE username =?";
        PreparedStatement p = getConnect().prepareStatement(query);
        p.setString(1, s);
        ResultSet rs = p.executeQuery();
        
        if (rs.next()) {
            return true;
        }
        return false;
    }

    public boolean checkLogin(User u) throws ClassNotFoundException, SQLException {
        
        String checklogin = "SELECT * FROM user WHERE username = ? and password = ?";
        PreparedStatement p = getConnect().prepareStatement(checklogin);
        //log(u.getUserName() + ":" + u.getPassWord());
        p.setString(1, u.getUserName());
        p.setString(2, u.getPassWord());
        ResultSet rs = p.executeQuery();

        if (rs.next()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean update(int d, String name) throws ClassNotFoundException, SQLException {
        String q = "Update user SET num=num+? WHERE username = ?";
        PreparedStatement p = getConnect().prepareStatement(q);
        //log(u.getUserName() + ":" + u.getPassWord());
        p.setInt(1, d);
        p.setString(2, name);
        int rs = p.executeUpdate();
        
        if (rs>0) {
            return true;
        }else {
            return false;
        }

    }

    public void log(String s) {
        System.out.println(s);
    }

    public int getdem(String userName) throws ClassNotFoundException, SQLException {
         String checklogin = "SELECT num FROM user WHERE username = ? ";
        PreparedStatement p = getConnect().prepareStatement(checklogin);
        //log(u.getUserName() + ":" + u.getPassWord());
        p.setString(1, userName);
        ResultSet rs = p.executeQuery();

        if (rs.next()) {
            return rs.getInt("num");
          
        } else {
            return 0;
        }
    }
   
    public ArrayList<Status> getAlluser() {
        PreparedStatement pst;
        String query = "select * from user order by num DESC";
        ArrayList<Status> lncc = new ArrayList<>();
        Status ncc;
        try {
            pst = getConnect().prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ncc = new Status();
                ncc.setId(rs.getInt("id"));
                ncc.setName(rs.getString("username"));
                ncc.setStatus(rs.getNString("email"));
                ncc.setDiem(rs.getInt("num"));
                //log(ncc.toString());
                lncc.add(ncc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lncc;
    }
    
}
