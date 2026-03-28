package model.inventory;

import model.shop.attachements.Attachment;
import model.shop.base.Purchasable;
import model.shop.consumables.Consumable;
import model.shop.consumables.Salt;
import model.shop.consumables.Biokerosene;
import java.util.ArrayList;
import java.util.List;

public class
Inventory {
    private String name;
    private List<Attachment> attachments= new ArrayList<>();
    private List<Consumable> consumables = new ArrayList<>();

    public Inventory(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public boolean addMoney(int amount){
        if (amount < 0) {
            return skeleton.Skeleton.askBoolQuestion("[?] Van elég pénz? (I/N)");
        }
        return true;
    }

    public void switchAttachment(Attachment oldA){
        if(oldA!=null){
            attachments.add(oldA);
        }
    }

    public void addAttachment(Attachment a){
        if(a!=null){
            attachments.add(a);
        }
    }

    public void addConsumable(Purchasable p){
        if(p instanceof Consumable){
            consumables.add((Consumable) p);
        }
    }

    public boolean useHead(Attachment head){
        boolean saltOK=true;
        boolean biokeroseneOK=true;

        for(Consumable c: consumables){
            if(c instanceof Salt) saltOK = c.use(head);
            if(c instanceof Biokerosene) biokeroseneOK = c.use(head);
        }
        return saltOK&&biokeroseneOK;
    }

}
