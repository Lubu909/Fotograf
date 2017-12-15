package pl.edu.ug.java;

import java.util.Random;

public class PasswordGenerator {
    private Random r = new Random();
    private final int low = 33;
    private final int hight = 125;
    private char c;
    private StringBuilder password;
    private int res;

    public String generatePasswd(){
        password = new StringBuilder();
        for(int i = 0; i<32; i++){
            res =  r.nextInt(hight - low) + low;
            c = (char)res;
            password.append(c);
        }
        return password.toString();
    }
}
