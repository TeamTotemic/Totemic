package totemic_commons.pokefenn.apiimpl.totem;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import totemic_commons.pokefenn.api.totem.TotemBase;
import totemic_commons.pokefenn.api.totem.TotemEffect;
import totemic_commons.pokefenn.api.totem.TotemEffectAPI;

public class TotemEffectApiImpl implements TotemEffectAPI
{
    @Override
    public int getDefaultRange(TotemEffect effect, TotemBase totem, int repetition)
    {
        return getDefaultRange(effect, DEFAULT_BASE_RANGE, totem, repetition);
    }

    @Override
    public int getDefaultRange(TotemEffect effect, int baseRange, TotemBase totem, int repetition)
    {
        return baseRange + totem.getTotemEffectMusic() / 32 + (totem.getPoleSize() >= TotemBase.MAX_POLE_SIZE ? 1 : 0);
    }

    @Override
    public int getDefaultPotionTime(int baseTime, boolean isBad, Random rand, TotemBase totem, int repetition)
    {
        return baseTime + 20;
    }

    @Override
    public int getDefaultPotionStrength(int baseStrength, boolean isBad, Random rand, TotemBase totem, int repetition)
    {
        return baseStrength + (repetition - 1) / 2 + (totem.getTotemEffectMusic() > 96 ? 1 : 0);
    }

    @Override
    public void addPotionEffect(EntityLivingBase entity, Potion potion, int baseTime, int baseStrength, TotemBase totem, int repetition)
    {
        entity.addPotionEffect(new PotionEffect(potion,
                getDefaultPotionTime(baseTime, potion.isBadEffect(), entity.getRNG(), totem, repetition),
                getDefaultPotionStrength(baseStrength, potion.isBadEffect(), entity.getRNG(), totem, repetition),
                true, false));
    }
}
