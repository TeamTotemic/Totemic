package totemic_commons.pokefenn.totem;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import totemic_commons.pokefenn.api.totem.TotemEffectAPI;

public class TotemEffectApiImpl implements TotemEffectAPI
{
    @Override
    public int getDefaultPotionTime(int baseTime, boolean isPositive, Random rand, int melodyAmount, int totemWoodBonus, int repetitionBonus)
    {
        if(isPositive)
            return baseTime + (repetitionBonus * 10) + rand.nextInt(41) + melodyAmount + (totemWoodBonus * 10);
        else
            return baseTime - (totemWoodBonus * 8) - (repetitionBonus * 7) - (melodyAmount / 32);
    }

    @Override
    public int getDefaultPotionStrength(int baseStrength, boolean isPositive, Random rand, int melodyAmount, int totemWoodBonus, int repetitionBonus)
    {
       if(isPositive)
           return baseStrength + (repetitionBonus >= 5 || melodyAmount > 112 ? 1 : 0);
       else
           return baseStrength - (melodyAmount > 112 ? 1 : 0);
    }

    @Override
    public void addPotionEffect(EntityPlayer player, Potion potion, boolean isPositive, int defaultTime, int defaultStrength,
            int melodyAmount, int totemWoodBonus, int repetitionBonus)
    {
        player.addPotionEffect(new PotionEffect(potion.id,
                getDefaultPotionTime(defaultTime, isPositive, player.getRNG(), melodyAmount, totemWoodBonus, repetitionBonus),
                getDefaultPotionStrength(defaultStrength, isPositive, player.getRNG(), melodyAmount, totemWoodBonus, repetitionBonus),
                true, false));
    }
}
