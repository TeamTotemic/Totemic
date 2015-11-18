package totemic_commons.pokefenn.item.equipment.music;

import java.util.List;
import java.util.Objects;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.item.ItemTotemic;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public abstract class ItemMusic extends ItemTotemic
{
    public MusicInstrument musicHandler;

    public ItemMusic(String name, MusicInstrument musicHandler)
    {
        super(name);
        this.musicHandler = Objects.requireNonNull(musicHandler);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        int musicOutput = musicHandler.getBaseOutput();
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
