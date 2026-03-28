package model.shop.base;

import java.util.function.Supplier;

public class StoreListing {
    private int price;
    private Supplier<Purchasable> factory;

    public StoreListing(int price, Purchasable purchasable) {
        this.price = price;
        this.factory = () -> purchasable;
    }

    public Purchasable manufacture(){
        return factory.get();
    }

    public int getPrice() {
        return price;
    }
}
