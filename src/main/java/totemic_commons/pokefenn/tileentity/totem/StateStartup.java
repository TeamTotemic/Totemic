package totemic_commons.pokefenn.tileentity.totem;

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.music.MusicInstrument;

public class StateStartup extends TotemState
{
    public static final int ID = 2;

    private Ceremony ceremony;
    private int time = 0;
    private final TObjectIntMap<MusicInstrument> music = new TObjectIntHashMap<>(Totemic.api.registry().getInstruments().size(), 0.75F);
    private int totalMusic = 0;
    private final TObjectIntMap<MusicInstrument> timesPlayed = new TObjectIntHashMap<>(Totemic.api.registry().getInstruments().size(), 0.75F);

    public StateStartup(TileTotemBase tile)
    {
        super(tile);
    }

    public StateStartup(TileTotemBase tile, Ceremony ceremony)
    {
        this(tile);
        this.ceremony = ceremony;
    }

    @Override
    public void update()
    {
        World world = tile.getWorld();
        BlockPos pos = tile.getPos();

        if(!world.isRemote)
        {
            if(!canStartCeremony())
            {
                if(time >= ceremony.getAdjustedMaxStartupTime(world.getDifficulty()))
                {
                    ((WorldServer) world).spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 16, 0.6D, 0.5D, 0.6D, 0.0D);
                    tile.setState(new StateTotemEffect(tile));
                }
            }
            else
            {
                ((WorldServer) world).spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 24, 0.6D, 0.5D, 0.6D, 1.0D);
                //TODO: Switch state to ceremony effect
            }
        }
        else
        {
            setCeremonyOverlay();
        }

        time++;
    }

    private boolean canStartCeremony()
    {
        return totalMusic >= ceremony.getMusicNeeded();
    }

    @SideOnly(Side.CLIENT)
    private void setCeremonyOverlay()
    {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if(tile.getDistanceSq(player.posX, player.posY, player.posZ) <= 8*8)
        {
            //GameOverlay.activeTotem = tile;
        }
        else
        {
            //if(GameOverlay.activeTotem == tile)
                //GameOverlay.activeTotem = null;
        }
    }

    @Override
    public boolean addMusic(MusicInstrument instr, int amount)
    {
        timesPlayed.adjustOrPutValue(instr, 1, 1);
        amount = getDiminishedMusic(instr, amount);

        int oldVal = music.get(instr);
        int newVal = Math.min(oldVal + amount, instr.getMusicMaximum());
        music.put(instr, newVal);
        totalMusic += (newVal - oldVal);
        return newVal > oldVal;
    }

    private int getDiminishedMusic(MusicInstrument instr, int amount)
    {
        if(timesPlayed.get(instr) >= amount)
            return amount * 3 / 4;
        else
            return amount;
    }

    @Override
    public int getID()
    {
        return ID;
    }

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        // TODO Auto-generated method stub

    }

}
