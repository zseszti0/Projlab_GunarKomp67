package model.shop.consumables;

import model.shop.attachements.Attachment;
import model.shop.base.Purchasable;

abstract public class Consumable implements Purchasable {
    protected int amount;

    public Consumable(final int id, int amount) {
        super();
        this.amount = amount;
    }

    public boolean use(Attachment a){
        return true;
    }

    public void addAmount(Salt s){

    }
    public void addAmount(Biokerosene b){

    }
    public int getAmount(){return amount;}
}
