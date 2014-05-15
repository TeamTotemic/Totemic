package totemic_commons.pokefenn.block.totem;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
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

        if(tileCeremonyIntelligence != null && !world.isRemote)
        {
            if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.totemicStaff || player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.infusedTotemicStaff)
            {
                /*
                //TODO make this work
                if(player.isSneaking()/* && tileCeremonyIntelligence.isDoingStartup)
                {
                    for(int i = 0; i < tileCeremonyIntelligence.musicSelector.length; i++)
                    {
                        System.out.println("seriously...");
                        tileCeremonyIntelligence.musicSelector[i] = 0;
                    }
                }

                if(!tileCeremonyIntelligence.isDoingEffect)
                {
                    if(tileCeremonyIntelligence.isMusicSelecting)
                    {
                        for(int i = 0; i < 4; i++)
                        {
                            player.addChatComponentMessage(new ChatComponentText("Musical Selection " + i + " =" + MusicEnum.values()[i].name()));
                        }
                    }

                }*/
            }
        }

        return true;
    }

    @Override
    public void onBasicRightClick(int x, int y, int z, EntityPlayer player, World world)
    {
        TileCeremonyIntelligence tileCeremonyIntelligence = (TileCeremonyIntelligence) world.getTileEntity(x, y, z);

        if(!tileCeremonyIntelligence.isDoingEffect)
        {
            if(tileCeremonyIntelligence.isMusicSelecting)
            {
                for(int i = 0; i < 4; i++)
                {
                    player.addChatComponentMessage(new ChatComponentText("Musical Selection " + i + " =" + MusicEnum.values()[i].name()));
                }
            }

        }

        if(player.isSneaking() && tileCeremonyIntelligence.isDoingStartup)
        {
            for(int i = 0; i < tileCeremonyIntelligence.musicSelector.length; i++)
            {
                tileCeremonyIntelligence.musicSelector[i] = 0;
            }
        }
    }

    @Override
    public void onInfusedRightClick(int x, int y, int z, EntityPlayer player, World world)
    {
        TileCeremonyIntelligence tileCeremonyIntelligence = (TileCeremonyIntelligence) world.getTileEntity(x, y, z);

        if(player.isSneaking() && tileCeremonyIntelligence.isDoingStartup)
        {
            for(int i = 0; i < tileCeremonyIntelligence.musicSelector.length; i++)
            {
                tileCeremonyIntelligence.musicSelector[i] = 0;
            }
        }

        if(!tileCeremonyIntelligence.isDoingEffect)
        {
            if(tileCeremonyIntelligence.isMusicSelecting)
            {
                for(int i = 0; i < 4; i++)
                {
                    player.addChatComponentMessage(new ChatComponentText("Musical Selection " + i + " =" + MusicEnum.values()[i].name()));
                }
            }

        }

    }
}
