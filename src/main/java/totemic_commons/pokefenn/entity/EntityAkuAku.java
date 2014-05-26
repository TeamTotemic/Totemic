package totemic_commons.pokefenn.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class EntityAkuAku extends Entity
{
    public String playerName;
    public int x;
    public int y;
    public int z;

    public EntityAkuAku(World par1World)
    {
        super(par1World);
    }

    public EntityAkuAku(World par1World, String playerName)
    {
        super(par1World);
        this.playerName = playerName;
    }

    public EntityAkuAku(World par1World, int x, int y, int z, String playerName)
    {
        super(par1World);
        this.x = x;
        this.y = y;
        this.z = z;
        this.playerName = playerName;
    }

    @Override
    protected void entityInit()
    {

    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbtTagCompound)
    {
        x = nbtTagCompound.getInteger("xCoord");
        y = nbtTagCompound.getInteger("yCoord");
        z = nbtTagCompound.getInteger("zCoord");
        playerName = nbtTagCompound.getString("playerName");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbtTagCompound)
    {
        nbtTagCompound.setInteger("xCoord", x);
        nbtTagCompound.setInteger("yCoord", y);
        nbtTagCompound.setInteger("zCoord", z);
        nbtTagCompound.setString("playerName", playerName);
    }
}
