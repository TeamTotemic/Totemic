package totemic_commons.pokefenn.api.totem;

import java.util.List;
import java.util.Objects;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.TotemicAPI;

/**
 * Default implementation of a Totem Effect that adds a potion effect to nearby players
 */
public class TotemEffectPotion extends TotemEffect
{
    public final Potion potion;
    public final int interval;
    public final int defaultTime;
    public final int amplifier;

    /**
     * @param potion the potion effect
     * @param interval the time in ticks until the potion effect is renewed
     * @param defaultTime the default duration of the effect, will be modified depending on the properties of the Totem pole
     * @param amplifier the default amplifier of the effect, will be modified depending on the properties of the Totem pole
     */
    public TotemEffectPotion(String modid, String baseName, int horizontal, int vertical, int tier, Potion potion, int interval, int defaultTime, int amplifier)
    {
        super(modid, baseName, horizontal, vertical, tier);
        this.potion = Objects.requireNonNull(potion);
        this.interval = interval;
        this.defaultTime = defaultTime;
        this.amplifier = amplifier;
    }

    @Override
    public void effect(World world, BlockPos pos, int poleSize, int horizontal, int vertical, int melodyAmount, int totemWoodBonus, int repetitionBonus)
    {
        if(world.isRemote)
            return;

        if(world.getTotalWorldTime() % interval == 0)
        {

            for(EntityPlayer entity : getPlayersInRange(world, pos, horizontal, vertical))
            {
                TotemicAPI.get().totemEffect().addPotionEffect(entity, potion, defaultTime, amplifier, melodyAmount, totemWoodBonus, repetitionBonus);
            }
        }
    }

    public static List<EntityPlayer> getPlayersInRange(World world, BlockPos pos, int horizontal, int vertical)
    {
        return world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.getX() - 0.5F, pos.getY() - 0.5f, pos.getZ() - 0.5f, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f).expand(horizontal, vertical, horizontal));
    }
}
