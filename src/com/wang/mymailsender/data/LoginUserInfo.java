/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.mymailsender.data;

/**
 *
 * @author ewawenl
 */
public class LoginUserInfo {
    private String user;
    private String password;
    private String smtpServer;
    
    public LoginUserInfo(String strUser, String strPwd, String strSmtpServer){
        user = strUser;
        password = strPwd;
        smtpServer = strSmtpServer;
    }
    public String getUser(){
        return user;
    }
    public String getPassword(){
        return password;
    }
    public String getSmtpServer(){
        return smtpServer;
    }
    public boolean validate(){
        return !(0 == smtpServer.length() ||
                password.length() == 0 ||
                0 == user.length());
    }
}
