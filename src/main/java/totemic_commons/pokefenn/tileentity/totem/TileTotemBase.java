package totemic_commons.pokefenn.tileentity.totem;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockSapling;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.IPlantable;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.api.IBlacklistedDraining;
import totemic_commons.pokefenn.api.ceremony.ICeremonyEffect;
import totemic_commons.pokefenn.api.music.IMusicAcceptor;
import totemic_commons.pokefenn.api.music.MusicEnum;
import totemic_commons.pokefenn.block.totem.BlockTotemPole;
import totemic_commons.pokefenn.api.plant.IPlantDrain;
import totemic_commons.pokefenn.item.ItemTotems;
import totemic_commons.pokefenn.recipe.registry.ceremony.CeremonyRegistry;
import totemic_commons.pokefenn.recipe.registry.TotemRegistry;
import totemic_commons.pokefenn.tileentity.TileTotemic;
import totemic_commons.pokefenn.util.EntityUtil;
import totemic_commons.pokefenn.util.TotemUtil;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 29/01/14
 * Time: 20:22
 */
public class TileTotemBase extends TileTotemic implements IMusicAcceptor
{
    public static final int SLOT_ONE = 0;
    public int plantEssence;
    public int maxEssence;
    public static int socket;
    public static int rangeUpgrades;
    public int musicalMelody;
    public int tier;
    public int efficiencyFromCeremony;
    public boolean isDoingEffect;
    public int currentCeremony;
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
    public int startupTime;
    public int musicFromCeremony;
    public boolean isCeremony;

    ItemStack[] totems;

    public TileTotemBase()
    {
        totems = new ItemStack[6];
        plantEssence = 0;
        maxEssence = 1000;
        rangeUpgrades = 0;
        music = new int[MusicEnum.values().length];
        tier = 1;
        efficiencyFromCeremony = 0;
        isDoingEffect = false;
        currentCeremony = 0;
        currentTime = 0;
        dancingEfficiency = 0;
        plantEfficiency = 0;
        musicSelector = new int[6];
        isDoingStartup = false;
        armourEfficiency = 0;
        tryingCeremonyID = 0;
        totalMelody = 0;
        isMusicSelecting = true;
        startupTime = 0;
        musicFromCeremony = 0;
        isCeremony = false;
    }

    public void updateEntity()
    {
        super.updateEntity();

        int currentInput = worldObj.getBlockPowerInput(xCoord, yCoord, zCoord);

        if(!this.worldObj.isRemote)
        {
            if(this.worldObj.getWorldTime() % 100L == 0)
            {
                setSocketAmounts();
                scanArea();
            }

            if(!(currentInput >= 1))
            {
                if(worldObj.getWorldTime() % 80L == 0)
                    drainEffect();

                if(isCeremony)
                {
                    doCeremonyCode();
                }

                if(socket > 0)
                {
                    for(int i = 1; i <= socket; i++)
                    {
                        if(totems[i] != null)
                        {
                            for(TotemRegistry totemRegistry : TotemRegistry.getRecipes())
                            {
                                //TODO remember tier
                                if(/*tier >= totemRegistry.getTier() && */totems[i] != null && totems[i].getItem() == totemRegistry.getTotem().getItem() && totems[i].getItemDamage() == totemRegistry.getTotem().getItemDamage() && canDoEffect(totemRegistry.getChlorophyllDecrement(), totems[i].getItemDamage()))
                                {
                                    totemRegistry.getEffect().effect(this, socket, true, totemRegistry, totemRegistry.getHorizontal(), totemRegistry.getVerticalHight());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void doCeremonyCode()
    {
        if(worldObj.getWorldTime() % 30L == 0)
        {
            resetMelody();
        }

        if(!isDoingStartup)
        {
            System.out.println("evenGetHere?");
            for(CeremonyRegistry ceremonyRegistry : CeremonyRegistry.ceremonyRegistry)
            {
                System.out.println(ceremonyRegistry.getInstruments(0).ordinal() + 1 == musicSelector[0] && ceremonyRegistry.getInstruments(1).ordinal() + 1 == musicSelector[1] && ceremonyRegistry.getInstruments(2).ordinal() + 1 == musicSelector[2] && ceremonyRegistry.getInstruments(3).ordinal() + 1 == musicSelector[3]);
                if(musicSelector[0] != 0 && musicSelector[1] != 0 && musicSelector[2] != 0 && musicSelector[3] != 0)
                {
                    if(ceremonyRegistry.getInstruments(0).ordinal() + 1 == musicSelector[0] && ceremonyRegistry.getInstruments(1).ordinal() + 1 == musicSelector[1] && ceremonyRegistry.getInstruments(2).ordinal() + 1 == musicSelector[2] && ceremonyRegistry.getInstruments(3).ordinal() + 1 == musicSelector[3])
                    {
                        MinecraftServer.getServer().worldServerForDimension(worldObj.provider.dimensionId).func_147487_a("enchantmenttable", (double) xCoord + 0.5D, (double) yCoord + 1.2D, (double) zCoord + 0.5D, 16, 0.0D, 0.0D, 0.0D, 0.0D);
                        tryingCeremonyID = ceremonyRegistry.getCeremonyID();
                        isDoingStartup = true;
                        isMusicSelecting = false;
                        resetSelector();
                    }
                }
            }
        }

        if(isDoingStartup && tryingCeremonyID != 0)
        {
            startupTime++;
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

            if(currentTime > CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getMaximumTicks())
                resetAfterCeremony(true);


            ICeremonyEffect effect = CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getCeremonyEffect();

            if(effect != null && !isDoingEffect)
            {
                if(CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getIsInstant())
                {
                    musicFromCeremony += getMusicFromCeremony();
                    effect.effect(this);
                    resetAfterCeremony(true);
                } else
                {
                    if(canContinueCeremony())
                    {
                        effect.effect(this);
                    } else
                    {
                        resetAfterCeremony(true);
                    }
                }
            }
        }

        if(worldObj.getWorldTime() % (20 * 60) == 0)
        {
            isCeremony = false;
            resetAfterCeremony(true);
        }
    }

    public int getMusicFromCeremony()
    {
        int j = 0;

        for(int i = 0; i < music.length; i++)
        {
            j += music[i];
        }
        return j;
    }

    public void resetMelody()
    {
        totalMelody = 0;
        int m = 0;

        for(int musicu : music)
        {
            m += musicu;
        }
        totalMelody = m;
    }

    public void resetAfterCeremony(boolean doResetMusicSelector)
    {
        currentCeremony = 0;
        tryingCeremonyID = 0;
        isMusicSelecting = true;
        isDoingEffect = false;
        startupTime = 0;
        musicFromCeremony = 0;

        dancingEfficiency = 0;
        armourEfficiency = 0;
        plantEfficiency = 0;

        for(int i = 0; i < music.length; i++)
        {
            music[i] = 0;
        }
        if(doResetMusicSelector)
            for(int i = 0; i < 4; i++)
            {
                musicSelector[i] = 0;
            }
    }

    public void resetSelector()
    {
        for(int i = 0; i < 4; i++)
        {
            musicSelector[i] = 0;
        }
    }

    public boolean canContinueCeremony()
    {
        int totalEfficiency = armourEfficiency + dancingEfficiency + plantEfficiency;

        if(worldObj.getWorldTime() % 20 * 5 == 0)
        {
            if(totalMelody - CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getMelodyPer5After() < 0)
                totalMelody = 0;
            else
                totalMelody -= CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getMelodyPer5After();

            if(totalMelody < CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getMelodyPer5After())
            {
                resetAfterCeremony(true);
            }
        }

        return totalMelody - CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getMelodyPer5After() >= 0;
    }


    public boolean canStartCeremony()
    {
        if(CeremonyRegistry.ceremonyRegistry.get(tryingCeremonyID - 1).getDoesNeedItems())
        {
            if(EntityUtil.getEntitiesInRange(worldObj, xCoord, yCoord, zCoord, 6, 6) != null)
            {
                for(Entity entity : EntityUtil.getEntitiesInRange(worldObj, xCoord, yCoord, zCoord, 6, 6))
                {
                    if(entity instanceof EntityItem)
                    {
                        if(((EntityItem) entity).getEntityItem().getItem() == CeremonyRegistry.ceremonyRegistry.get(tryingCeremonyID - 1).getItem().getItem() && ((EntityItem) entity).getEntityItem().getItemDamage() == CeremonyRegistry.ceremonyRegistry.get(tryingCeremonyID - 1).getItem().getItemDamage() && ((EntityItem) entity).getEntityItem().stackSize >= CeremonyRegistry.ceremonyRegistry.get(tryingCeremonyID - 1).getItem().stackSize)
                        {
                            ((EntityItem) entity).setEntityItemStack(new ItemStack(((EntityItem) entity).getEntityItem().getItem(), ((EntityItem) entity).getEntityItem().stackSize - CeremonyRegistry.ceremonyRegistry.get(tryingCeremonyID - 1).getItem().stackSize, ((EntityItem) entity).getEntityItem().getItemDamage()));
                            break;
                        }
                    }
                }
            }
        }
        resetMelody();
        workOutEfficiency();

        //TODO
        return totalMelody > CeremonyRegistry.ceremonyRegistry.get(tryingCeremonyID - 1).getMusicNeeded() - (plantEfficiency % 8) - (armourEfficiency) - (dancingEfficiency % 50);
    }

    public void workOutEfficiency()
    {
    }

    public void startupMain()
    {
        if(startupTime > CeremonyRegistry.ceremonyRegistry.get(tryingCeremonyID - 1).getMaximumStartupTime())
        {
            resetAfterCeremony(true);
        }

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

        if(worldObj.getWorldTime() % 5L == 0)
        {
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

        if(worldObj.getClosestPlayer(xCoord, yCoord, zCoord, 8) != null)
        {

            EntityPlayer player = worldObj.getClosestPlayer(xCoord, yCoord, zCoord, 8);

            if((int) player.posX != (int) player.prevPosX && (int) player.posY != (int) player.prevPosY)
            {
                dancingEfficiency++;
            }
        }
    }

    public void drainEffect()
    {
        int totemRadius = 12;

        for(int i = -totemRadius; i <= totemRadius; i++)
            for(int k = -totemRadius; k <= totemRadius; k++)
                for(int j = -totemRadius; j <= totemRadius; j++)
                {
                    reducePlantMetadata(xCoord + i, yCoord + k, zCoord + j);
                }
    }

    public void reducePlantMetadata(int x, int y, int z)
    {
        Block blockQuery = worldObj.getBlock(x, y, z);
        boolean isNotFlower = !blockQuery.getUnlocalizedName().contains("flower");
        boolean isNotBush = !blockQuery.getUnlocalizedName().contains("bush");
        boolean isNotBerry = !blockQuery.getUnlocalizedName().contains("berry");
        boolean isNotKelp = !blockQuery.getUnlocalizedName().contains("kelp");
        boolean isNotLeaves = !blockQuery.getUnlocalizedName().contains("leaves");

        if(blockQuery != null)
        {
            if(blockQuery instanceof BlockSapling || blockQuery instanceof BlockLeaves || blockQuery instanceof BlockLog)
            {
                Random rand = new Random();
                if(rand.nextInt(10) == 1)
                    increasePlantEssence(blockQuery);
            }

            if(blockQuery instanceof IPlantable)
            {
                if(worldObj.getBlockMetadata(x, y, z) >= 4 && !(blockQuery instanceof IBlacklistedDraining) && isNotFlower && isNotBush && isNotBerry && isNotKelp && isNotLeaves)
                {
                    Random rand = new Random();

                    if(rand.nextInt(4) == 1)
                        worldObj.setBlockMetadataWithNotify(x, y, z, worldObj.getBlockMetadata(x, y, z) - 1, 2);
                    if(rand.nextBoolean())
                        increasePlantEssence(blockQuery);
                    MinecraftServer.getServer().worldServerForDimension(getWorldObj().provider.dimensionId).func_147487_a("happyVillager", (double) x + 0.5D, (double) y + 0.9D, (double) z + 0.5D, 4, 0.0D, 0.0D, 0.0D, 0.0D);
                }
            }
        }
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

    public int getEffiencyFromBlock(Block block)
    {
        //if(efficiency < 50)
        {
            if(block == ModBlocks.totemTorch)
                return 4;
            if(block == ModBlocks.totemPole)
                return 3;
            if(block == Blocks.fire)
                return 2;
            if(block == ModBlocks.flameParticle)
                return 4;
        }

        return 0;
    }

    protected void scanArea()
    {
        for(int i = 1; i <= getSocketAmounts(); i++)
        {
            if(getSocketAmounts() <= 5)
            {
                if(getSocketItemStack(i) != null)
                {
                    totems[i] = getSocketItemStack(i);
                } else
                    totems[i] = null;
            }
        }
    }

    public boolean canUpdate()
    {
        return true;
    }

    public void increasePlantEssence(Block block)
    {
        if(plantEssence < maxEssence)
        {
            if((plantEssence + getPlantDrained(block) > maxEssence))
            {
                plantEssence = maxEssence;
                return;
            }

            plantEssence += getPlantDrained(block);
        }
    }

    public int getPlantDrained(Block plant)
    {
        if(plant instanceof BlockLog || plant instanceof BlockLeaves)
        {
            return 1;
        }

        if(plant instanceof BlockSapling)
        {
            return 2;
        }

        return 3;
    }

    public void decreaseChlorophyll(int subtraction)
    {
        if(plantEssence - subtraction > 0)
            plantEssence -= subtraction;
    }

    protected boolean canDoEffect(int subtraction, int meta)
    {
        return plantEssence - subtraction > 0;
    }

    protected ItemStack getSocketItemStack(int par1)
    {
        TileEntity tileEntity = this.worldObj.getTileEntity(this.xCoord, this.yCoord + par1, this.zCoord);

        return ((IInventory) tileEntity).getStackInSlot(TileTotemPole.SLOT_ONE);
    }


    protected int getSocketAmounts()
    {
        Block block1 = worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord);
        Block block2 = worldObj.getBlock(this.xCoord, this.yCoord + 2, this.zCoord);
        Block block3 = worldObj.getBlock(this.xCoord, this.yCoord + 3, this.zCoord);
        Block block4 = worldObj.getBlock(this.xCoord, this.yCoord + 4, this.zCoord);
        Block block5 = worldObj.getBlock(this.xCoord, this.yCoord + 5, this.zCoord);

        if(block1 instanceof BlockTotemPole && block2 != ModBlocks.totemPole)
        {
            return 1;
        } else if(block1 instanceof BlockTotemPole && block2 instanceof BlockTotemPole && block3 != ModBlocks.totemPole)
        {
            return 2;
        } else if(block1 instanceof BlockTotemPole && block2 instanceof BlockTotemPole && block3 instanceof BlockTotemPole && block4 != ModBlocks.totemPole)
        {
            return 3;
        } else if(block1 instanceof BlockTotemPole && block2 instanceof BlockTotemPole && block3 instanceof BlockTotemPole && block4 instanceof BlockTotemPole && block5 != ModBlocks.totemPole)
        {
            return 4;
        } else if(block1 instanceof BlockTotemPole && block2 instanceof BlockTotemPole && block3 instanceof BlockTotemPole && block4 instanceof BlockTotemPole && block5 instanceof BlockTotemPole)
        {
            return 5;
        } else
            return 0;

    }

    protected void setSocketAmounts()
    {
        Block block1 = worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord);
        Block block2 = worldObj.getBlock(this.xCoord, this.yCoord + 2, this.zCoord);
        Block block3 = worldObj.getBlock(this.xCoord, this.yCoord + 3, this.zCoord);
        Block block4 = worldObj.getBlock(this.xCoord, this.yCoord + 4, this.zCoord);
        Block block5 = worldObj.getBlock(this.xCoord, this.yCoord + 5, this.zCoord);

        if(block1 instanceof BlockTotemPole && block2 != ModBlocks.totemPole)
        {
            socket = 1;
        } else if(block1 instanceof BlockTotemPole && block2 instanceof BlockTotemPole && block3 != ModBlocks.totemPole)
        {
            socket = 2;
        } else if(block1 instanceof BlockTotemPole && block2 instanceof BlockTotemPole && block3 instanceof BlockTotemPole && block4 != ModBlocks.totemPole)
        {
            socket = 3;
        } else if(block1 instanceof BlockTotemPole && block2 instanceof BlockTotemPole && block3 instanceof BlockTotemPole && block4 instanceof BlockTotemPole && block5 != ModBlocks.totemPole)
        {
            socket = 4;
        } else if(block1 instanceof BlockTotemPole && block2 instanceof BlockTotemPole && block3 instanceof BlockTotemPole && block4 instanceof BlockTotemPole && block5 instanceof BlockTotemPole)
        {
            socket = 5;
        } else
            socket = 0;

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
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {

        super.readFromNBT(nbtTagCompound);

        plantEssence = nbtTagCompound.getInteger("plantEssence");
        musicalMelody = nbtTagCompound.getInteger("musicalMelody");
        tier = nbtTagCompound.getInteger("tier");
        efficiencyFromCeremony = nbtTagCompound.getInteger("efficiencyFromCeremony");
        isDoingEffect = nbtTagCompound.getBoolean("isDoingEffect");
        currentCeremony = nbtTagCompound.getInteger("currentCeremony");
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
        startupTime = nbtTagCompound.getInteger("startupTime");
        musicFromCeremony = nbtTagCompound.getInteger("musicFromCeremony");
        isCeremony = nbtTagCompound.getBoolean("isCeremony");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {

        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setInteger("plantEssence", plantEssence);
        nbtTagCompound.setIntArray("music", music);
        nbtTagCompound.setInteger("musicalMelody", musicalMelody);
        nbtTagCompound.setInteger("tier", tier);
        nbtTagCompound.setInteger("efficiencyFromCeremony", efficiencyFromCeremony);
        nbtTagCompound.setBoolean("isDoingEffect", isDoingEffect);
        nbtTagCompound.setInteger("currentCeremony", currentCeremony);
        nbtTagCompound.setInteger("currentTime", currentTime);
        nbtTagCompound.setInteger("dancingEfficiency", dancingEfficiency);
        nbtTagCompound.setInteger("plantEfficiency", plantEfficiency);
        nbtTagCompound.setIntArray("musicSelector", musicSelector);
        nbtTagCompound.setBoolean("isDoingStartup", isDoingStartup);
        nbtTagCompound.setInteger("armourEfficiency", armourEfficiency);
        nbtTagCompound.setInteger("tryingCeremonyID", tryingCeremonyID);
        nbtTagCompound.setInteger("totalMelody", totalMelody);
        nbtTagCompound.setBoolean("isMusicSelecting", isMusicSelecting);
        nbtTagCompound.setInteger("startupTime", startupTime);
        nbtTagCompound.setInteger("musicFromCeremony", musicFromCeremony);
        nbtTagCompound.setBoolean("isCeremony", isCeremony);
    }

    @Override
    public int[] getMusicArray()
    {
        return music;
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

    @Override
    public boolean isMusicSelecting()
    {
        return isMusicSelecting;
    }
}
