package model.shop.attachements;
import model.shop.base.Purchasable;
import model.shop.consumables.Biokerosene;
import model.shop.consumables.Salt;
import model.vehicles.SnowShovel;
import model.map.Tile;

/**
 * A latogato tervezesi minta „latogato” szerepkoret valositja meg.
 * Egyseges feluletet biztosit a kulonbozo fejek nyersanyag fogyasztasanak kezelesere.
 * Asszociaciok: Tile, Consumable, SnowShovel.
 */
abstract public class Attachment implements Purchasable {
    /**
     * A kotrofej neve.
     */
    private final String name;

    /**
     * A kotrofej egyedi azonositoja.
     */
    int id;

    /**
     * A hokotro gep, amire a fej fel van szerelve.
     */
    private SnowShovel snowShovel;

    /**
     * Konstruktor az absztrakt kotrofejhez.
     * @param name a fej neve
     * @param id a fej egyedi azonositoja
     */
    public Attachment(final String name, final int id) {
        this.id = id;
        this.name = name;
    }

    /**
     * Absztrakt latogatasi metodus, amely megvalositja a tisztitasi folyamat polimorfizmusat.
     * @param tile az utfelulet, amellyel interakcioba lep
     * @return sikeres volt-e az allapotvaltozas
     */
    public abstract boolean cleanWith(Tile tile);

    /**
     * So felhasznalasat ellenorzi es hajtja vegre.
     * @param s a felhasznalando so
     * @return sikeres volt-e a felhasznalas (alapertelmezetten true)
     */
    public boolean consume(Salt s){
        return true;
    }

    /**
     * Biokerozin felhasznalasat ellenorzi es hajtja vegre.
     * @param b a felhasznalando biokerozin
     * @return sikeres volt-e a felhasznalas (alapertelmezetten true)
     */
    public boolean consume(Biokerosene b){
        return true;
    }

    /**
     * Visszaadja a fej nevet.
     * @return a fej neve
     */
    public String getName(){
        return name;
    }

    /**
     * Beallitja, hogy melyik hokotron van felszerelve a fej.
     * @param snowShovel az aktiv hokotro
     */
    public void setSnowShovel(SnowShovel snowShovel){
        this.snowShovel = snowShovel;
    }
}
