package model.shop.consumables;

import shop.attachements.Attachment;
import shop.base.Purchasable;

abstract public class Consumable extends Purchasable {
    protected int amount;

    public Consumable(final int id, int amount) {
        super(id);
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
