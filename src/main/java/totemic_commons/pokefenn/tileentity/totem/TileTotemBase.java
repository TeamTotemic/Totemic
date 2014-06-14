package totemic_commons.pokefenn.tileentity.totem;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockSapling;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.api.ceremony.ICeremonyEffect;
import totemic_commons.pokefenn.api.music.IMusicAcceptor;
import totemic_commons.pokefenn.api.music.MusicEnum;
import totemic_commons.pokefenn.api.recipe.CeremonyRegistry;
import totemic_commons.pokefenn.api.recipe.TotemRegistry;
import totemic_commons.pokefenn.block.totem.BlockTotemPole;
import totemic_commons.pokefenn.tileentity.TileTotemic;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 29/01/14
 * Time: 20:22
 */
public class TileTotemBase extends TileTotemic implements IMusicAcceptor
{
    public int maxEssence;
    public static int socket;
    public static int rangeUpgrades;
    public int musicalMelody;
    public int tier;
    public int efficiencyFromCeremony;
    public boolean isDoingEffect;
    public int currentCeremony;
    public int ceremonyEffectTimer;
    public int dancingEfficiency;
    public int[] musicCeremony;
    public int[] musicSelector;
    public boolean isDoingStartup;
    public int armourEfficiency;
    public int tryingCeremonyID;
    public int totalMelody;
    public boolean isMusicSelecting;
    public int ceremonyStartupTimer;
    public int musicFromCeremony;
    public boolean isCeremony;
    public int continueTimer;
    public boolean isCeremonyAwakening;
    public int musicForEffect;
    public static int maximumMusic = 128;

    ItemStack[] totems;

    public TileTotemBase()
    {
        totems = new ItemStack[6];
        maxEssence = 500;
        rangeUpgrades = 0;
        musicCeremony = new int[MusicEnum.values().length];
        tier = 1;
        efficiencyFromCeremony = 0;
        isDoingEffect = false;
        currentCeremony = 0;
        ceremonyEffectTimer = 0;
        dancingEfficiency = 0;
        musicSelector = new int[6];
        isDoingStartup = false;
        armourEfficiency = 0;
        tryingCeremonyID = 0;
        totalMelody = 0;
        isMusicSelecting = true;
        ceremonyStartupTimer = 0;
        musicFromCeremony = 0;
        isCeremony = false;
        continueTimer = 0;
        isCeremonyAwakening = false;
        musicForEffect = 0;
    }

    public void updateEntity()
    {
        super.updateEntity();

        int currentInput = worldObj.getBlockPowerInput(xCoord, yCoord, zCoord);

        if(!worldObj.isRemote)
        {
            if(this.worldObj.getWorldTime() % 100L == 0)
            {
                setSocketAmounts();
                scanArea();
            }

            if(isCeremony)
            {
                doCeremonyCode();
            }

            if(!(currentInput >= 1))
            {
                if(socket > 0)
                {
                    for(int i = 1; i <= socket; i++)
                    {
                        if(totems[i] != null)
                        {
                            for(TotemRegistry totemRegistry : TotemRegistry.getRecipes())
                            {
                                if(/*tier >= totemRegistry.getTier() && */totems[i] != null && totems[i].getItem() == totemRegistry.getTotem().getItem() && totems[i].getItemDamage() == totemRegistry.getTotem().getItemDamage())
                                {
                                    //TODO
                                    totemRegistry.getEffect().effect(this, socket, totemRegistry, getRanges(totemRegistry)[0], getRanges(totemRegistry)[1], musicForEffect);
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
        if(!isDoingStartup)
        {
            for(CeremonyRegistry ceremonyRegistry : CeremonyRegistry.ceremonyRegistry)
            {
                if(musicSelector[0] != 0 && musicSelector[1] != 0 && musicSelector[2] != 0 && musicSelector[3] != 0)
                {
                    if(ceremonyRegistry.getInstruments(0).ordinal() + 1 == musicSelector[0] && ceremonyRegistry.getInstruments(1).ordinal() + 1 == musicSelector[1] && ceremonyRegistry.getInstruments(2).ordinal() + 1 == musicSelector[2] && ceremonyRegistry.getInstruments(3).ordinal() + 1 == musicSelector[3])
                    {
                        System.out.println("foobar");
                        particleAfterStartup();
                        tryingCeremonyID = ceremonyRegistry.getCeremonyID();
                        isDoingStartup = true;
                        isMusicSelecting = false;
                        resetSelector();
                    }
                }
            }
        }

        if(worldObj.getWorldTime() % 30L == 0)
        {
            resetMelody();
        }

        if(isDoingStartup && tryingCeremonyID != 0)
        {
            ceremonyStartupTimer++;
            startupMain();
        }
        if(tryingCeremonyID != 0 && isDoingStartup)
        {
            if(canStartCeremony())
            {
                currentCeremony = tryingCeremonyID;
                tryingCeremonyID = 0;
                isDoingStartup = false;
                //if(CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getMusicNeeded() == 0)
                //{
                //    isCeremonyAwakening = true;
                //}
            }
        }

        if(currentCeremony <= CeremonyRegistry.ceremonyRegistry.size() && currentCeremony != 0)
        {
            if(isDoingEffect && !CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getIsInstant())
                ceremonyEffectTimer++;

            ICeremonyEffect effect = CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getCeremonyEffect();

            if(ceremonyEffectTimer > CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getMaximumTicksOnEffect())
            {
                //if(isCeremonyAwakening)
                //{
                musicFromCeremony += getMusicFromCeremony();
                //    effect.effect(this);
                //}
                resetAfterCeremony(true);
            }

            if(effect != null && !isDoingEffect && !isCeremonyAwakening)
            {
                System.out.println("getting close to effect");
                if(CeremonyRegistry.ceremonyRegistry.get(currentCeremony - 1).getIsInstant())
                {
                    System.out.println("got to instant effect");
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

        if(isMusicSelecting && worldObj.getWorldTime() % (20 * 60) == 0)
        {
            isCeremony = false;
            resetAfterCeremony(true);
        }
    }

    public void depricateMelody()
    {
        if(musicForEffect != 0)
        {

        }
    }

    public static String getMusicName(int i)
    {
        //TODO stat collector
        if(i < 10)
        {
        } else if(i > 10 && i < 32)
        {
            return StatCollector.translateToLocal("totemic:weak");
        } else if(i > 32 && i < 64)
        {
            return StatCollector.translateToLocal("totemic:sufficient");
        } else if(i > 64 && i < 96)
        {
        } else if(i > 96 && i < 115)
        {
        } else if(i > 115)
        {
        }
        return "";
    }

    public int[] getRanges(TotemRegistry totemRegistry)
    {
        int[] array = new int[2];

        array[0] = totemRegistry.getHorizontal();
        array[1] = totemRegistry.getVerticalHight();

        if(musicForEffect > 10 && musicForEffect < 32)
        {
            array[0] += 1;
            array[1] += 1;
        } else if(musicForEffect > 32 && musicForEffect < 64)
        {
            array[0] += 2;
            array[1] += 2;
        } else if(musicForEffect > 64 && musicForEffect < 96)
        {
            array[0] += 3;
            array[1] += 3;
        } else if(musicForEffect > 96 && musicForEffect < 115)
        {
            array[0] += 4;
            array[1] += 4;
        } else if(musicForEffect > 115)
        {
            array[0] += 6;
            array[1] += 6;
        }
        array[0] += socket % 2;
        array[1] += socket % 2;

        //TODO this method will say the vertical/horizontal range of Totem Poles

        return array;
    }

    public void particleAfterStartup()
    {
        MinecraftServer.getServer().worldServerForDimension(worldObj.provider.dimensionId).func_147487_a("fireworksSpark", (double) xCoord + 1, (double) yCoord, (double) zCoord + 0.5D, 16, 0.0D, 0.5D, 0.0D, 0.0D);
        MinecraftServer.getServer().worldServerForDimension(worldObj.provider.dimensionId).func_147487_a("fireworksSpark", (double) xCoord - 1, (double) yCoord, (double) zCoord + 0.5D, 16, 0.0D, 0.5D, 0.0D, 0.0D);
        MinecraftServer.getServer().worldServerForDimension(worldObj.provider.dimensionId).func_147487_a("fireworksSpark", (double) xCoord, (double) yCoord + 1, (double) zCoord + 0.5D, 16, 0.0D, 0.5D, 0.0D, 0.0D);
        MinecraftServer.getServer().worldServerForDimension(worldObj.provider.dimensionId).func_147487_a("fireworksSpark", (double) xCoord, (double) yCoord - 1, (double) zCoord + 0.5D, 16, 0.0D, 0.5D, 0.0D, 0.0D);

    }

    public int getMusicFromCeremony()
    {
        int j = 0;

        for(int i = 0; i < musicCeremony.length; i++)
        {
            j += musicCeremony[i];
        }
        return j;
    }

    public void resetMelody()
    {
        totalMelody = 0;
        int m = 0;

        for(int musicu : musicCeremony)
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
        ceremonyStartupTimer = 0;
        musicFromCeremony = 0;
        isDoingStartup = false;
        isCeremonyAwakening = false;

        dancingEfficiency = 0;
        armourEfficiency = 0;

        for(int i = 0; i < musicCeremony.length; i++)
        {
            musicCeremony[i] = 0;
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
        continueTimer++;
        if(continueTimer > 20 * 5)
        {
            continueTimer = 0;
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
        System.out.println("melody is " + totalMelody);
        System.out.println(totalMelody - CeremonyRegistry.ceremonyRegistry.get(tryingCeremonyID - 1).getMusicNeeded() - (armourEfficiency) - (dancingEfficiency % 50));

        return totalMelody > CeremonyRegistry.ceremonyRegistry.get(tryingCeremonyID - 1).getMusicNeeded() - (armourEfficiency) - (dancingEfficiency % 50);
    }

    public void workOutEfficiency()
    {
    }

    public void startupMain()
    {
        System.out.println("main?");
        if(ceremonyStartupTimer > CeremonyRegistry.ceremonyRegistry.get(tryingCeremonyID - 1).getMaximumStartupTime()) ;
        {
            resetAfterCeremony(true);
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
                        //int armourAndBaubles = TotemUtil.getArmourAmounts((EntityPlayer) entity) + TotemUtil.getTotemBaublesAmount((EntityPlayer) entity);
                        //armourEfficiency += armourAndBaubles;
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
                if(player.getCurrentArmor(1) != null && player.getCurrentArmor(1).getItem() == ModItems.totemArmourLeg)
                    dancingEfficiency += 2;

                dancingEfficiency++;
            }
        }
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

    public int getPlantDrained(Block plant)
    {
        if(plant instanceof BlockLog || plant instanceof BlockLeaves)
        {
            return 1;
        }

        if(plant instanceof BlockSapling)
        {
            return 1;
        }

        return 2;
    }

    protected ItemStack getSocketItemStack(int par1)
    {
        TileEntity tileEntity = this.worldObj.getTileEntity(this.xCoord, this.yCoord + par1, this.zCoord);

        return ((TileTotemPole) tileEntity).getStackInSlot(TileTotemPole.SLOT_ONE);
    }


    protected int getSocketAmounts()
    {
        Block block1 = worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord);
        Block block2 = worldObj.getBlock(this.xCoord, this.yCoord + 2, this.zCoord);
        Block block3 = worldObj.getBlock(this.xCoord, this.yCoord + 3, this.zCoord);
        Block block4 = worldObj.getBlock(this.xCoord, this.yCoord + 4, this.zCoord);
        Block block5 = worldObj.getBlock(this.xCoord, this.yCoord + 5, this.zCoord);
        Block block6 = worldObj.getBlock(this.xCoord, this.yCoord + 6, this.zCoord);
        Block block7 = worldObj.getBlock(this.xCoord, this.yCoord + 7, this.zCoord);
        Block block8 = worldObj.getBlock(this.xCoord, this.yCoord + 8, this.zCoord);
        Block block9 = worldObj.getBlock(this.xCoord, this.yCoord + 9, this.zCoord);
        Block block10 = worldObj.getBlock(this.xCoord, this.yCoord + 10, this.zCoord);


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
        } else if(block1 instanceof BlockTotemPole && block2 instanceof BlockTotemPole && block3 instanceof BlockTotemPole && block4 instanceof BlockTotemPole && block5 instanceof BlockTotemPole && block6 != ModBlocks.totemPole)
        {
            return 6;
        } else if(block1 instanceof BlockTotemPole && block2 instanceof BlockTotemPole && block3 instanceof BlockTotemPole && block4 instanceof BlockTotemPole && block5 instanceof BlockTotemPole && block6 instanceof BlockTotemPole && block7 != ModBlocks.totemPole)
        {
            return 7;
        } else if(block1 instanceof BlockTotemPole && block2 instanceof BlockTotemPole && block3 instanceof BlockTotemPole && block4 instanceof BlockTotemPole && block5 instanceof BlockTotemPole && block6 instanceof BlockTotemPole && block7 instanceof BlockTotemPole && block8 != ModBlocks.totemPole)
        {
            return 8;
        } else if(block1 instanceof BlockTotemPole && block2 instanceof BlockTotemPole && block3 instanceof BlockTotemPole && block4 instanceof BlockTotemPole && block5 instanceof BlockTotemPole && block6 instanceof BlockTotemPole && block7 instanceof BlockTotemPole && block8 instanceof BlockTotemPole && block9 != ModBlocks.totemPole)
        {
            return 9;
        } else if(block1 instanceof BlockTotemPole && block2 instanceof BlockTotemPole && block3 instanceof BlockTotemPole && block4 instanceof BlockTotemPole && block5 instanceof BlockTotemPole && block6 instanceof BlockTotemPole && block7 instanceof BlockTotemPole && block8 != ModBlocks.totemPole && block9 == ModBlocks.totemPole && block10 != ModBlocks.totemPole)
        {
            return 10;
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

        musicalMelody = nbtTagCompound.getInteger("musicalMelody");
        tier = nbtTagCompound.getInteger("tier");
        efficiencyFromCeremony = nbtTagCompound.getInteger("efficiencyFromCeremony");
        isDoingEffect = nbtTagCompound.getBoolean("isDoingEffect");
        currentCeremony = nbtTagCompound.getInteger("currentCeremony");
        dancingEfficiency = nbtTagCompound.getInteger("dancingEfficiency");
        ceremonyEffectTimer = nbtTagCompound.getInteger("ceremonyEffectTimer");
        musicCeremony = nbtTagCompound.getIntArray("musicCeremony");
        musicSelector = nbtTagCompound.getIntArray("musicSelector");
        isDoingStartup = nbtTagCompound.getBoolean("isDoingStartup");
        armourEfficiency = nbtTagCompound.getInteger("armourEfficiency");
        tryingCeremonyID = nbtTagCompound.getInteger("tryingCeremonyID");
        totalMelody = nbtTagCompound.getInteger("totalMelody");
        isMusicSelecting = nbtTagCompound.getBoolean("isMusicSelecting");
        ceremonyStartupTimer = nbtTagCompound.getInteger("ceremonyStartupTimer");
        musicFromCeremony = nbtTagCompound.getInteger("musicFromCeremony");
        isCeremony = nbtTagCompound.getBoolean("isCeremony");
        continueTimer = nbtTagCompound.getInteger("continueTimer");
        isCeremonyAwakening = nbtTagCompound.getBoolean("isCeremonyAwakening");
        musicForEffect = nbtTagCompound.getInteger("musicForEffect");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {

        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setIntArray("musicCeremony", musicCeremony);
        nbtTagCompound.setInteger("musicalMelody", musicalMelody);
        nbtTagCompound.setInteger("tier", tier);
        nbtTagCompound.setInteger("efficiencyFromCeremony", efficiencyFromCeremony);
        nbtTagCompound.setBoolean("isDoingEffect", isDoingEffect);
        nbtTagCompound.setInteger("currentCeremony", currentCeremony);
        nbtTagCompound.setInteger("ceremonyEffectTimer", ceremonyEffectTimer);
        nbtTagCompound.setInteger("dancingEfficiency", dancingEfficiency);
        nbtTagCompound.setIntArray("musicSelector", musicSelector);
        nbtTagCompound.setBoolean("isDoingStartup", isDoingStartup);
        nbtTagCompound.setInteger("armourEfficiency", armourEfficiency);
        nbtTagCompound.setInteger("tryingCeremonyID", tryingCeremonyID);
        nbtTagCompound.setInteger("totalMelody", totalMelody);
        nbtTagCompound.setBoolean("isMusicSelecting", isMusicSelecting);
        nbtTagCompound.setInteger("ceremonyStartupTimer", ceremonyStartupTimer);
        nbtTagCompound.setInteger("musicFromCeremony", musicFromCeremony);
        nbtTagCompound.setBoolean("isCeremony", isCeremony);
        nbtTagCompound.setInteger("continueTimer", continueTimer);
        nbtTagCompound.setBoolean("isCeremonyAwakening", isCeremonyAwakening);
        nbtTagCompound.setInteger("musicForEffect", musicForEffect);
    }

    @Override
    public int[] getMusicArray()
    {
        return musicCeremony;
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

    @Override
    public int getMusicForEffect()
    {
        return musicForEffect;
    }

    @Override
    public boolean getEffectMusic()
    {
        return !isCeremony;
    }
}
