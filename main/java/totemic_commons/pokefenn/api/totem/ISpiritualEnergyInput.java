package totemic_commons.pokefenn.api.totem;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public interface ISpiritualEnergyInput
{
    public int getCurrentSpirtualEnergy();

    public void receiveSpiritualEnergy(int spirtualEnergy);

    public boolean canReceiveSpiritualEnergy(int spiritualEnergy);

    public int getMaxEssence();
}
