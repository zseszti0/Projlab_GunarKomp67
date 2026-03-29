package model.shop.consumables;

import model.shop.attachements.Attachment;
import model.shop.base.Purchasable;

abstract public class Consumable implements Purchasable {
    protected int amount;
    private final String name;

    public Consumable(final int id, int amount, String name) {
        this.amount = amount;
        this.name = name;
    }

    public boolean use(Attachment a){
        return true;
    }

    public void addAmount(Salt s){

    }
    public void addAmount(Biokerosene b){

    }
    public int getAmount(){return amount;}

    public String getName(){
        return name;
    }
}
