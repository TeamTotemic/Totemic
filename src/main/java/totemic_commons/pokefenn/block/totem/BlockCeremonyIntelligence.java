package totemic_commons.pokefenn.block.totem;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.api.ITotemicStaffUsage;
import totemic_commons.pokefenn.api.music.MusicEnum;
import totemic_commons.pokefenn.block.BlockTileTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.totem.TileCeremonyIntelligence;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockCeremonyIntelligence extends BlockTileTotemic implements ITotemicStaffUsage
{

    public BlockCeremonyIntelligence()
    {
        super(Material.wood);
        setBlockName(Strings.CEREMONY_INTELLIGENCE_NAME);

    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileCeremonyIntelligence();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        TileCeremonyIntelligence tileCeremonyIntelligence = (TileCeremonyIntelligence) world.getTileEntity(x, y, z);

        if(tileCeremonyIntelligence != null && !world.isRemote && player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.totemicStaff)
        {
            if(player.isSneaking() && tileCeremonyIntelligence.isMusicSelecting)
            {
                for(int i = 0; i < tileCeremonyIntelligence.musicSelector.length; i++)
                {
                    tileCeremonyIntelligence.musicSelector[i] = 0;
                }
            }

            if(!tileCeremonyIntelligence.isDoingEffect && !player.isSneaking())
            {
                if(tileCeremonyIntelligence.isMusicSelecting)
                {
                    if(tileCeremonyIntelligence.musicSelector[0] == 0 && tileCeremonyIntelligence.musicSelector[1] == 0 && tileCeremonyIntelligence.musicSelector[2] == 0 && tileCeremonyIntelligence.musicSelector[3] == 0)
                    {
                        player.addChatComponentMessage(new ChatComponentText("No Music for seclector."));
                        return true;
                    }

                    for(int i = 0; i < 4; i++)
                    {
                        if(tileCeremonyIntelligence.musicSelector[i] == 0)
                        {
                            player.addChatComponentMessage(new ChatComponentText("No Music for selection on " + (i + 1)));
                        } else
                            player.addChatComponentMessage(new ChatComponentText("Musical Selection " + (i + 1) + " is " + MusicEnum.values()[tileCeremonyIntelligence.musicSelector[i]]));
                    }
                    //    if(tileCeremonyIntelligence.musicSelector[0] != 0 || tileCeremonyIntelligence.musicSelector[1] != 0 || tileCeremonyIntelligence.musicSelector[2] != 0 || tileCeremonyIntelligence.musicSelector[3] != 0)
                    //        player.addChatComponentMessage(new ChatComponentText("Musical Selections are: " + MusicEnum.values()[tileCeremonyIntelligence.musicSelector[0] + 1].name() + ", " + MusicEnum.values()[tileCeremonyIntelligence.musicSelector[1] + 1].name() + ", " + MusicEnum.values()[tileCeremonyIntelligence.musicSelector[2] + 1].name() + " and " + MusicEnum.values()[tileCeremonyIntelligence.musicSelector[3] + 1].name()));
                }

            }

        }
        return true;
    }


    @Override
    public void onBasicRightClick(int x, int y, int z, EntityPlayer player, World world, ItemStack itemStack)
    {
        TileCeremonyIntelligence tileCeremonyIntelligence = (TileCeremonyIntelligence) world.getTileEntity(x, y, z);
    }

}
