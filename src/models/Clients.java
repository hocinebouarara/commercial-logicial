/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author hocin
 */
public class Clients {

    int id;
    String Name, Adress, City, Phone, Fax, RepresCl;

    public Clients(int id, String Name, String Adress, String City, String Phone, String Fax, String RepresCl) {
        this.id = id;
        this.Name = Name;
        this.Adress = Adress;
        this.City = City;
        this.Phone = Phone;
        this.Fax = Fax;
        this.RepresCl = RepresCl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String Adress) {
        this.Adress = Adress;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String Fax) {
        this.Fax = Fax;
    }

    public String getRepresCl() {
        return RepresCl;
    }

    public void setRepresCl(String RepresCl) {
        this.RepresCl = RepresCl;
    }

}
