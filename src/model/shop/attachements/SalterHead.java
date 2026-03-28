package model.shop.attachements;

import shop.consumables.Salt;

public class SalterHead extends Attachment{
    public SalterHead(final int id) {
        super(id);
    }

    @Override
    public boolean consume(Salt s){
        s.use(this);
        return true;
    }
}
