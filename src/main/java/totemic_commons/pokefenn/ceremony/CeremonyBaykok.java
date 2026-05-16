package totemic_commons.pokefenn.ceremony;

import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.entity.boss.EntityBaykok;

public class CeremonyBaykok extends Ceremony
{
    public CeremonyBaykok(String modid, String name, int musicNeeded, int maxStartupTime, MusicInstrument... instruments)
    {
        super(modid, name, musicNeeded, maxStartupTime, 4 * 20, 0, instruments);
    }

    @Override
    public void effect(World world, int x, int y, int z)
    { }

    @Override
    public void effect(World world, int x, int y, int z, int time)
    {
        if(!world.isRemote && time == getEffectTime())
        {
            world.playBroadcastSound(1013, x, y, z, 0); //Wither spawn sound
            EnumFacing facing = EnumFacing.getFront(2 + world.rand.nextInt(4)); //random horizontal facing
            int spawnX = x + facing.getFrontOffsetX();
            int spawnZ = z + facing.getFrontOffsetZ();
            int spawnY = world.getTopSolidOrLiquidBlock(spawnX, spawnZ);
            EntityBaykok baykok = new EntityBaykok(world);
            baykok.setPosition(spawnX + 0.5, spawnY, spawnZ + 0.5);
            baykok.onSpawnWithEgg(null);
            world.spawnEntityInWorld(baykok);
        }
    }
}
