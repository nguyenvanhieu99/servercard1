/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author van hieu
 */
public class ThreeCard implements Serializable{

    public int card1;
    public int card2;
    public int card3;
    public int sum;

    public ThreeCard(int card1, int card2, int card3) {
        this.card1 = card1;
        this.card2 = card2;
        this.card3 = card3;
        this.sum = card1 + card2 + card3;
    }

    public ThreeCard() {
    }

    public String allnum() {
        String res="";
        res = res + getRealCard(card1) + "-";
        res = res + getRealCard(card2) + "-";
        res = res + getRealCard(card3) ;
        return res;

    }

    public String getRealCard(int t) {
        String re = "";
        int so = t % 10;
        t = t / 10;

        if (t == 0) {
            re += "♦" + so;
        } else if (t == 1) {
            re += "♥" + so;

        } else if (t == 2) {
            re += "♠" + so;
        } else if (t == 3) {
            re += "♣" + so;
        }
        return re;

    }

    public int getCard1() {
        return card1;
    }

    public int getCard2() {
        return card2;
    }

    public int getCard3() {
        return card3;
    }

    public int getSum() {
        return sum;
    }

    public void setCard1(int card1) {
        this.card1 = card1;
    }

    public void setCard2(int card2) {
        this.card2 = card2;
    }

    public void setCard3(int card3) {
        this.card3 = card3;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

}
