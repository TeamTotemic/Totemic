package totemic_commons.pokefenn.tileentity.music;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import totemic_commons.pokefenn.tileentity.TileTotemic;

public class TileDrum extends TileTotemic implements ITickable
{
    public int currentTime = 0;
    public boolean canPlay = true;

    @Override
    public void update()
    {
        /* TODO
         * Have the drums play music when you jump ontop of them, the armour will increase the amount of music played.
         * Also a drum beater item so when you hit the drum it pounces extra music.
         */

        if(!world.isRemote)
        {
            if(!canPlay)
            {
                currentTime++;
            }

            if(currentTime > 20)
            {
                currentTime = 0;
                canPlay = true;
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag)
    {
        tag = super.writeToNBT(tag);
        tag.setInteger("currentTime", currentTime);
        tag.setBoolean("canPlay", canPlay);
        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        currentTime = nbtTagCompound.getInteger("currentTime");
        canPlay = nbtTagCompound.getBoolean("canPlay");
    }
}
