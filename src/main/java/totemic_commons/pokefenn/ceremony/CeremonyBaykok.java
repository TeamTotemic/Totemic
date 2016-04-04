package totemic_commons.pokefenn.ceremony;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.entity.boss.EntityBaykok;

public class CeremonyBaykok extends Ceremony
{
    public CeremonyBaykok(String name, int musicNeeded, int maxStartupTime, MusicInstrument... instruments)
    {
        super(name, musicNeeded, maxStartupTime, instruments);
    }

    @Override
    public void effect(World world, BlockPos pos, int time)
    {
        if(world.isRemote || time != getEffectTime() - 1)
            return;

        world.playBroadcastSound(1013, pos, 0); //Wither spawn sound
        BlockPos spos = pos.offset(EnumFacing.getHorizontal(world.rand.nextInt(4)));
        EntityBaykok baykok = new EntityBaykok(world);
        baykok.setPosition(spos.getX() + 0.5, spos.getY(), spos.getZ() + 0.5);
        baykok.onInitialSpawn(world.getDifficultyForLocation(spos), null);
        world.spawnEntityInWorld(baykok);
    }

    @Override
    public int getEffectTime()
    {
        return 4 * 20;
    }
}
