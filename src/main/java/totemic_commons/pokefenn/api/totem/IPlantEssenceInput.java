package totemic_commons.pokefenn.api.totem;

import totemic_commons.pokefenn.api.totem.ITotem;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public interface IPlantEssenceInput extends ITotem
{
    public int getCurrentPlantEssence();

    public void receivePlantEssence(int plantEssence);

    public boolean canReceivePlantEssence(int plantEssence);

    public int getMaxEssence();

}
