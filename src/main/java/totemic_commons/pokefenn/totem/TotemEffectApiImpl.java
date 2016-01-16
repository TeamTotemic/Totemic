package totemic_commons.pokefenn.totem;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import totemic_commons.pokefenn.api.totem.TotemBase;
import totemic_commons.pokefenn.api.totem.TotemEffectAPI;

public class TotemEffectApiImpl implements TotemEffectAPI
{
    @Override
    public int getDefaultPotionTime(int baseTime, boolean isBad, Random rand, TotemBase totem)
    {
        if(isBad)
            return baseTime - (totemWoodBonus * 8) - (repetitionBonus * 7) - (melodyAmount / 32);
        else
            return baseTime + (repetitionBonus * 10) + rand.nextInt(41) + melodyAmount + (totemWoodBonus * 10);
    }

    @Override
    public int getDefaultPotionStrength(int baseStrength, boolean isBad, Random rand, int melodyAmount, int totemWoodBonus, int repetitionBonus)
    {
       if(isBad)
           return baseStrength - (melodyAmount > 112 ? 1 : 0);
       else
           return baseStrength + (repetitionBonus >= 5 || melodyAmount > 112 ? 1 : 0);
    }

    @Override
    public void addPotionEffect(EntityPlayer player, Potion potion, int defaultTime, int defaultStrength,
            int melodyAmount, int totemWoodBonus, int repetitionBonus)
    {
        player.addPotionEffect(new PotionEffect(potion.id,
                getDefaultPotionTime(defaultTime, potion.isBadEffect(), player.getRNG(), melodyAmount, totemWoodBonus, repetitionBonus),
                getDefaultPotionStrength(defaultStrength, potion.isBadEffect(), player.getRNG(), melodyAmount, totemWoodBonus, repetitionBonus),
                true, false));
    }
}
