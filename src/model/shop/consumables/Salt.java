package model.shop.consumables;

import model.shop.attachements.SalterHead;

public class Salt extends Consumable {
    public Salt(final int id, int amount){
        super(id, amount);
    }

    public boolean use(SalterHead s){
        amount--;
        return true;
    }

    @Override
    public boolean addAmount(Salt s){
        this.amount = this.getAmount() + s.getAmount();
        return true;
    }
}
