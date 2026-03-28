package model.shop.attachements;

import shop.consumables.Biokerosene;

public class DragonHead extends Attachment{
    public DragonHead(final int id) {
        super(id);
    }

    @Override
    public boolean consume(Biokerosene b){
        b.use(this);
        return true;
    }
}
