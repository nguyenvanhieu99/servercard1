/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Random;

/**
 *
 * @author van hieu
 */
public class Cards {
    public  boolean s[];
    
    public Cards(boolean[] s) {
        this.s = s;
    }

    public Cards() {
        s=new boolean[40];
        s[0]=s[10]=s[20]=s[30]=true;
    }
    public int random(){
        int res = -1;
        boolean term=true;
        while(term){
            Random rand = new Random(); 
             res=rand.nextInt(40);
             if(!s[res]){
                 term=false;
                 s[res]=true;
             }
        }
        return res;
    }
    public ThreeCard getThreeCard(){        
        return new ThreeCard(random(),random(),random());
        
    }

}

