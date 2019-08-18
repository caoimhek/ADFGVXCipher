package ie.gmit.dip;

import java.util.*;


public class Menu {

    public Scanner s = new Scanner(System.in);

    public String MenuInput() {

        System.out.println("### Welcome to the ADFGVX Cipher ###");
        System.out.println("### 1. Please Enter The Message to Encrypted ###");
        System.out.println("###  ###");
        
        String message = s.nextLine();
        
        return message;
    }
}
