package Controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author van hieu
 */
public interface Command {

    public static int LOGIN = 1;
    public static int REGISTER = 2;
    public static int LOGOUT = 3;
    public static int GETALLTABLE = 4;
    public static int GETALLCLIENT = 5;
    public static int RANK = 6;
    public static int CHALLENGE = 7;
    public static int JOINTABLE = 8;
    public static int CONTINUE = 9;
    public static int LOGINDONE = 10;
    public static int CREATTABLE = 11;
    public static int invite = 12;
    public static int PLAY = 13;

}
