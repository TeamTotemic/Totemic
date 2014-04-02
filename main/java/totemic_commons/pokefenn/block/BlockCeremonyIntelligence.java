package totemic_commons.pokefenn.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.ceremony.CeremonyRegistry;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.TileCeremonyIntelligence;
import totemic_commons.pokefenn.tileentity.TileTotemic;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockCeremonyIntelligence extends BlockTileTotemic
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

        if (tileCeremonyIntelligence != null && !world.isRemote)
        {
            if (player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.infusedTotemicStaff)
                if (tileCeremonyIntelligence.isBurning)
                    if (!tileCeremonyIntelligence.isDoingEffect)
                    {
                        tileCeremonyIntelligence.tryCeremony(tileCeremonyIntelligence);
                        tileCeremonyIntelligence.player = player.getCommandSenderName();
                        System.out.println("Can do ritual");
                    }

        }

        return true;
    }
}
