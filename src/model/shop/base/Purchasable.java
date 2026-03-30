package model.shop.base;

import model.inventory.Inventory;

/**
 * A megvasarolhato arucikkek kozos interfesze.
 * A kontroller es a modell kozotti erintkezesi felulet resze, biztositja az egyseges nevlekerdezest.
 */
public interface Purchasable {
    /**
     * Visszaadja a megvasarolhato arucikk nevet.
     * @return az arucikk neve
     */
    String getName();

    public void addToInventory(Inventory inventory);
}