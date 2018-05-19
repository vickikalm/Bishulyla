package com.example.victoriya.bishulayla;



/**
 * Created by Victoriya on 3/29/2018.
 */

public class Prod  {
    private String prod;
    private String amount;

    public Prod(String prod, String amount) {
        this.prod = prod;
        this.amount = amount;
    }

    public Prod() {

    }

    public String getProd() {
        return prod;
    }

    public void setProd(String prod) {
        this.prod = prod;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return
                 prod + ":====>" +  amount + '\n'
               ;
    }
}
