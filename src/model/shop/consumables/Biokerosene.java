package model.shop.consumables;

import shop.attachements.DragonHead;

public class Biokerosene extends Consumable{
    public Biokerosene(final int id, int amount){
        super(id, amount);
    }

    public boolean use(DragonHead d){
        amount--;
        return true;
    }

    @Override
    public void addAmount(Biokerosene b){
        this.amount = this.getAmount() + b.getAmount();
    }
}
