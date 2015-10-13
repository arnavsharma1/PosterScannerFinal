// Homework Assignment Number 6 Final Project                                               //
// Class: CS6301 User Interface Design                                        //

// Name: ARNAV SHARMA               Net ID: axs144130                         //
//----------------------------------------------------------------------------//
// Date created: 04.19.2015                                                   //
////////////////////////////////////////////////////////////////////////////////
//----------------------------------------------------------------------------//


// Basic class for event object.//     	              //
package com.smartmobilesofware.ocrapiservice;

/**
 * Created by Arnav on 4/19/2015.
 */
public class Eve {
    private String name;
    private String sdate;
   private String ldate;
    //private String email;

    public Eve(String name) {
        this.name = name;
       // Lname = lname;
        this.sdate = sdate;
        this.ldate = ldate;
    }

    public Eve() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getsdate() {
        return sdate;
    }

    public void setsdate(String sdate) {
        this.sdate = sdate;
    }

    public String getldate() {
        return ldate;
    }

    public void setldate(String ldate) {
        this.ldate = ldate;
    }

    /*public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }*/
}

