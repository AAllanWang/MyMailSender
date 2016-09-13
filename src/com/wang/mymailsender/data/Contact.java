/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.mymailsender.data;

/**
 *
 * @author allan
 */
public class Contact {
    public Contact(String firstName, String lastName, String title,
            String email){
        firstName_ = firstName;
        lastName_ = lastName;
        title_ = title;
        email_ = email;
    }
    public String getFirstName(){
        return firstName_;
    }
    public String getLastName(){
        return lastName_;
    }
    public String getEmail(){
        return email_;
    }
    public String getTitle(){
        return title_;
    }

    public boolean equals(Contact c1){
        return this.getKey().equals(c1.getKey());
    }

    public String getKey(){
        return this.firstName_+this.lastName_;
    }

    private String firstName_;
    private String lastName_;
    private String title_;
    private String email_;
}
