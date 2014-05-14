package totemic_commons.pokefenn.tileentity.totem;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.api.ceremony.ICeremonyEffect;
import totemic_commons.pokefenn.api.music.IMusicAcceptor;
import totemic_commons.pokefenn.api.music.MusicEnum;
import totemic_commons.pokefenn.api.plant.IPlantDrain;
import totemic_commons.pokefenn.recipe.registry.CeremonyRegistry;
import totemic_commons.pokefenn.lib.PlantIds;
import totemic_commons.pokefenn.tileentity.TileTotemic;
import totemic_commons.pokefenn.util.TotemUtil;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class TileCeremonyIntelligence extends TileTotemic implements IMusicAcceptor
{
    public boolean isDoingEffect;
    public int currentCeremony;
    public String player;
    public int currentTime;
    public int dancingEfficiency;
    public int[] music;
    public int plantEfficiency;
    public int[] musicSelector;
    public boolean isDoingStartup;
    public int armourEfficiency;
    public int tryingCeremonyID;
    public int totalMelody;
    public boolean isMusicSelecting;

    public TileCeremonyIntelligence()
    {
        isDoingEffect = false;
        currentCeremony = 0;
        player = "";
        currentTime = 0;
        dancingEfficiency = 0;
        music = new int[MusicEnum.values().length];
        plantEfficiency = 0;
        musicSelector = new int[6];
        isDoingStartup = false;
        armourEfficiency = 0;
        tryingCeremonyID = 0;
        totalMelody = 0;
        isMusicSelecting = false;
    }

    @Override
    public boolean canUpdate()
    {
        return true;
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if(!this.worldObj.isRemote)
        {
            if(worldObj.getWorldTime() % 60L == 0)
            {
                for(int aMusicSelector : musicSelector)
                {
                    System.out.println(aMusicSelector);
                }
                //System.out.println(isDoingStartup + "IS DOING STARTUP");
            }

            if(!isDoingStartup)
                for(CeremonyRegistry ceremonyRegistry : CeremonyRegistry.ceremonyRegistry)
                {
                    isMusicSelecting = true;
                    if(ceremonyRegistry.getInstruments(1).ordinal() == musicSelector[0] && ceremonyRegistry.getInstruments(2).ordinal() == musicSelector[1] && ceremonyRegistry.getInstruments(3).ordinal() == musicSelector[2] && ceremonyRegistry.getInstruments(4).ordinal() == musicSelector[3])
                    {
                        MinecraftServer.getServer().worldServerForDimension(worldObj.provider.dimensionId).func_147487_a("enchantmenttable", (double) xCoord + 0.5D, (double) yCoord + 1.2D, (double) zCoord + 0.5D, 16, 0.0D, 0.0D, 0.0D, 0.0D);
                        tryingCeremonyID = ceremonyRegistry.getCeremonyID();
                        isDoingStartup = true;
                        isMusicSelecting = false;
                    }
                }

            if(isDoingStartup)
            {
                startupMain();
            }

            if(tryingCeremonyID != 0 && isDoingStartup)
            {
                if(canStartCeremony())
                {
                    currentCeremony = tryingCeremonyID;
                    tryingCeremonyID = 0;
                    isDoingStartup = false;
                }
            }

            if(currentCeremony <= CeremonyRegistry.ceremonyRegistry.size() && currentCeremony != 0)
            {
                if(isDoingEffect && !CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getIsInstant())
                    currentTime++;

                ICeremonyEffect effect = CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getCeremonyEffect();

                if(effect != null && !isDoingEffect)
                {
                    if(CeremonyRegistry.ceremonyRegistry.get(currentCeremony).getIsInstant())
                    {
                        effect.effect(this);
                        currentCeremony = 0;
                        tryingCeremonyID = 0;
                    } else if(canContinueCeremony())
                    {
                        effect.effect(this);
                    }
                }
            }
        }
    }

    public boolean canContinueCeremony()
    {
        //TODO
        return false;
    }


    public boolean canStartCeremony()
    {
        //TODO
        return false;
    }

    public void startupMain()
    {
        if(worldObj.getWorldTime() % 80L == 0)
        {
            plantEfficiency = 0;
            findBlocksForEfficiency();
        }

        if(worldObj.getWorldTime() % 60L == 0)
        {
            armourEfficiency = 0;
            getPlayersArmour();
        }

        if(worldObj.getWorldTime() % 10L == 0)
        {
            dancingEfficiency = 0;
            danceLikeAMonkey();
        }
    }

    public void getPlayersArmour()
    {
        int p = 0;

        if(EntityUtil.getEntitiesInRange(this.worldObj, xCoord, yCoord, zCoord, 8, 8) != null)
        {
            for(Entity entity : EntityUtil.getEntitiesInRange(getWorldObj(), xCoord, yCoord, zCoord, 8, 8))
            {
                if(entity instanceof EntityPlayer)
                {
                    p++;
                    if(p <= 3)
                    {
                        int armourAndBaubles = TotemUtil.getArmourAmounts((EntityPlayer) entity) + TotemUtil.getTotemBaublesAmount((EntityPlayer) entity);
                        armourEfficiency += armourAndBaubles;
                    }
                }
            }
        }


    }

    public void danceLikeAMonkey()
    {
        //TODO
    }

    public void findBlocksForEfficiency()
    {
        int radius = 6;
        int m = 0;

        for(int i = -radius; i <= radius; i++)
            for(int j = -3; j <= 3; j++)
                for(int k = radius; k <= radius; k++)
                {
                    if(worldObj.getBlock(xCoord + i, yCoord + j, zCoord + k) != null)
                    {
                        Block block = worldObj.getBlock(xCoord + i, yCoord + j, zCoord + k);

                        if(block instanceof IPlantable)
                        {
                            m++;
                        }

                        if(getEffiencyFromBlock(block) != 0)
                        {
                            m += getEffiencyFromBlock(block);
                        }
                    }
                }
        plantEfficiency += m;
    }

    public void resetEverything()
    {
        currentCeremony = 0;
        currentTime = 0;
        dancingEfficiency = 0;
        isDoingEffect = false;
    }

    public int getEffiencyFromBlock(Block block)
    {
        //if(efficiency < 50)
        {
            if(block == ModBlocks.totemTorch)
                return 4;
            if(block == ModBlocks.totemSocket)
                return 3;
            if(block == Blocks.fire)
                return 2;
            if(block == ModBlocks.flameParticle)
                return 4;
        }

        return 0;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setBoolean("isDoingEffect", isDoingEffect);
        nbtTagCompound.setInteger("currentCeremony", currentCeremony);
        nbtTagCompound.setString("player", player);
        nbtTagCompound.setInteger("currentTime", currentTime);
        nbtTagCompound.setInteger("dancingEfficiency", dancingEfficiency);
        nbtTagCompound.setIntArray("music", music);
        nbtTagCompound.setInteger("plantEfficiency", plantEfficiency);
        nbtTagCompound.setIntArray("musicSelector", musicSelector);
        nbtTagCompound.setBoolean("isDoingStartup", isDoingStartup);
        nbtTagCompound.setInteger("armourEfficiency", armourEfficiency);
        nbtTagCompound.setInteger("tryingCeremonyID", tryingCeremonyID);
        nbtTagCompound.setInteger("totalMelody", totalMelody);
        nbtTagCompound.setBoolean("isMusicSelecting", isMusicSelecting);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);

        isDoingEffect = nbtTagCompound.getBoolean("isDoingEffect");
        currentCeremony = nbtTagCompound.getInteger("currentCeremony");
        player = nbtTagCompound.getString("player");
        dancingEfficiency = nbtTagCompound.getInteger("dancingEfficiency");
        currentTime = nbtTagCompound.getInteger("currentTime");
        music = nbtTagCompound.getIntArray("music");
        plantEfficiency = nbtTagCompound.getInteger("plantEfficiency");
        musicSelector = nbtTagCompound.getIntArray("musicSelector");
        isDoingStartup = nbtTagCompound.getBoolean("isDoingStartup");
        armourEfficiency = nbtTagCompound.getInteger("armourEfficiency");
        tryingCeremonyID = nbtTagCompound.getInteger("tryingCeremonyID");
        totalMelody = nbtTagCompound.getInteger("totalMelody");
        isMusicSelecting = nbtTagCompound.getBoolean("isMusicSelecting");
    }


    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        readFromNBT(pkt.func_148857_g());
    }

    @Override
    public int[] getMusicArray()
    {
        return this.music;
    }

    @Override
    public int[] getMusicSelector()
    {
        return musicSelector;
    }

    @Override
    public boolean doesMusicSelect()
    {
        return true;
    }
}
