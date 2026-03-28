package model.vehicles;

import model.inventory.Inventory;
import model.map.Tile;
import model.shop.attachements.Attachment;
import model.shop.base.Purchasable;

public class SnowShovel extends Vehicle implements Purchasable {
    private Attachment currentAttachment;

    public SnowShovel(String name, Tile position){
        super(name,position);
    }

    public boolean clean(Inventory inventory){
        if(currentAttachment!=null && inventory!=null){
            boolean hasEnoughResource = inventory.useHead(currentAttachment);
            if(hasEnoughResource){
                return currentAttachment.cleansOnTile(position);
            }
        }
        return false;
    }

    public Attachment getEquippedAttachment(){
        return currentAttachment;
    }

    public void setEquippedAttachment(Attachment attachment){
        this.currentAttachment=attachment;
    }

    @Override
    public void getHitByCar(){}
}