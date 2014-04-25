package totemic_commons.pokefenn.tileentity;

import net.minecraftforge.common.util.ForgeDirection;
import totemic_commons.pokefenn.api.totem.ISpiritualEnergyInput;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class TileSpiritualEnergyMover extends TileTotemic implements ISpiritualEnergyInput
{
    public int currentEnergy;
    public int maximumEnergy;

    public TileSpiritualEnergyMover()
    {
        currentEnergy = 0;
        maximumEnergy = 100;
    }

    public boolean canUpdate()
    {
        return true;
    }

    public void updateEntity()
    {

        if(!worldObj.isRemote)
        {
            //if(currentEnergy != 0)
            {
                if(worldObj.getWorldTime() % 80L == 0)
                {
                    spawnSpiritualEnergy();
                    currentEnergy = 0;
                }
            }

        }

    }

    public void spawnSpiritualEnergy()
    {
        int direction = orientation.ordinal();

    }

    @Override
    public int getCurrentSpirtualEnergy()
    {
        return currentEnergy;
    }

    @Override
    public void receiveSpiritualEnergy(int spirtualEnergy)
    {
        if(spirtualEnergy + currentEnergy > maximumEnergy)
        {
            this.currentEnergy = maximumEnergy;
        } else
        {
            currentEnergy += maximumEnergy;
        }
    }

    @Override
    public boolean canReceiveSpiritualEnergy(int spiritualEnergy)
    {
        return currentEnergy < maximumEnergy;
    }

    @Override
    public int getMaxEssence()
    {
        return maximumEnergy;
    }
}
