package totemic_commons.pokefenn.apiimpl.totem;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import totemic_commons.pokefenn.api.totem.TotemBase;
import totemic_commons.pokefenn.api.totem.TotemEffectAPI;

public class TotemEffectApiImpl implements TotemEffectAPI
{
    @Override
    public int getDefaultPotionTime(int baseTime, boolean isBad, Random rand, TotemBase totem, int repetition)
    {
        if(isBad)
            return baseTime - (totem.getWoodBonus() * 8) - (repetition * 7) - (totem.getTotemEffectMusic() / 32);
        else
            return baseTime + (repetition * 10) + rand.nextInt(41) + totem.getTotemEffectMusic() + (totem.getWoodBonus() * 10);
    }

    @Override
    public int getDefaultPotionStrength(int baseStrength, boolean isBad, Random rand, TotemBase totem, int repetition)
    {
       if(isBad)
           return baseStrength - (totem.getTotemEffectMusic() > 112 ? 1 : 0);
       else
           return baseStrength + (repetition >= 5 || totem.getTotemEffectMusic() > 112 ? 1 : 0);
    }

    @Override
    public void addPotionEffect(EntityLivingBase entity, Potion potion, int baseTime, int baseStrength, TotemBase totem, int repetition)
    {
        entity.addPotionEffect(new PotionEffect(potion.id,
                getDefaultPotionTime(baseTime, potion.isBadEffect(), entity.getRNG(), totem, repetition),
                getDefaultPotionStrength(baseStrength, potion.isBadEffect(), entity.getRNG(), totem, repetition),
                true, false));
    }
}
