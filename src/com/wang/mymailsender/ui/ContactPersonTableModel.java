/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.mymailsender.ui;

import com.wang.mymailsender.data.Contact;
import com.wang.mymailsender.data.ContactList;
import java.util.HashMap;
import javax.swing.JCheckBox;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Allan
 */
public class ContactPersonTableModel  extends AbstractTableModel {

    public ContactPersonTableModel(ContactList cl){
        contactList = cl;
    }

    public void setContactList(ContactList cl){
        contactList = cl;
    }

    @Override
    public int getRowCount() {
        return contactList.getContactList().size() + 3;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }
    public String getColumnName(int col) {
        switch(col){
            case 0: return "First Name";
            case 1: return "Last Name";
            case 2: return "Title";
            case 3: return "E-mail";
        }
        return "";
    }
    public Class getColumnClass(int c){
        if(c < 4){
            return new String().getClass();
        }else{
            return new JCheckBox().getClass();
        }
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        HashMap<String,Contact> map = contactList.getContactList();
        if(rowIndex < map.size()){
            Contact c = map.get(map.keySet().toArray()[rowIndex]);
            if(null == c){
                return "";
            }

            switch(columnIndex){
                case 0: return c.getFirstName();
                case 1: return c.getLastName();
                case 2: return c.getTitle();
                case 3: return c.getEmail();
                case 4: return new JCheckBox();
            }
        }
        return "";
    }

    public boolean isCellEditable(int row, int col) {
        if(col < 4) {
            return false;
        }
        else{
            return true;
        }
    }
    private ContactList contactList;
}
