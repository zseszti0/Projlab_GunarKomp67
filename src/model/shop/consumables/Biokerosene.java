package model.shop.consumables;

import model.shop.attachements.DragonHead;

public class Biokerosene extends Consumable{
    public Biokerosene(final int id, int amount, String name){
        super(id, amount,  name);
    }

    public boolean use(DragonHead d){
        amount--;
        return true;
    }

    @Override
    public boolean addAmount(Biokerosene b){
        this.amount = this.getAmount() + b.getAmount();
        return true;
    }
}
