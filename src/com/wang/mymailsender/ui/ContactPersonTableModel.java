/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wang.mymailsender.ui;

import com.wang.mymailsender.data.Contact;
import com.wang.mymailsender.data.ContactList;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Allan
 */
public class ContactPersonTableModel  extends AbstractTableModel {

    public ContactPersonTableModel(ContactList cl){
        contactList = cl;
        enableList = new ArrayList<Integer>();
    }

    public void setContactList(ContactList cl){
        contactList = cl;
    }
    
    public ArrayList<Integer> getEnableList(){
        return enableList;
    }

    @Override
    public int getRowCount() {
        return contactList.getContactList().size() + 3;
    }

    @Override
    public int getColumnCount() {
        return 5;
    }
    public String getColumnName(int col) {
        switch(col){
            case 0: return "First Name";
            case 1: return "Last Name";
            case 2: return "Title";
            case 3: return "E-mail";
            case 4: return "Send or Not";
        }
        return "";
    }
    public Class getColumnClass(int c){
        if(c < 4){
            return String.class;
        }else{
            return Boolean.class;
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
            }
        }
        if(4 == columnIndex){
            if(enableList.contains(new Integer(rowIndex))){
                return new Boolean(true);
            }
            return new Boolean(false);
        }
        return "";
    }
    public void setValueAt(Object value, int row, int col) {
        if(4 == col && row < contactList.getContactList().size()){
            if((Boolean)(value)){
                enableList.add(new Integer(row));
            }else {
                enableList.remove(new Integer(row));
            }
            fireTableCellUpdated(row, col);
        }
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
    private ArrayList<Integer> enableList;
}
