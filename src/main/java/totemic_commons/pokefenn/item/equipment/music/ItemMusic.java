package totemic_commons.pokefenn.item.equipment.music;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import totemic_commons.pokefenn.api.music.IMusic;
import totemic_commons.pokefenn.item.ItemTotemic;

import java.util.List;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public abstract class ItemMusic extends ItemTotemic implements IMusic
{
    public ItemMusic(String name)
    {
        super(name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        int musicOutput = getMusicOutput(player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ, true, player);
        if(musicOutput < 5)
            list.add(StatCollector.translateToLocal("totemic.music.lowMelody"));
        else if(musicOutput == 6)
            list.add(StatCollector.translateToLocal("totemic.music.mediumMelody"));
        else if(musicOutput == 7)
            list.add(StatCollector.translateToLocal("totemic.music.highMelody"));
        else if(musicOutput > 7)
            list.add(StatCollector.translateToLocal("totemic.music.veryHighMelody"));
    }

}
