package totemic_commons.pokefenn.block.music;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import totemic_commons.pokefenn.block.BlockTileTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.network.PacketHandler;
import totemic_commons.pokefenn.network.client.PacketSound;
import totemic_commons.pokefenn.recipe.HandlerInitiation;
import totemic_commons.pokefenn.tileentity.music.TileWindChime;
import totemic_commons.pokefenn.util.TotemUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockWindChime extends BlockTileTotemic
{
    public BlockWindChime()
    {
        super(Material.iron);
        setBlockName(Strings.WIND_CHIME_NAME);
        setBlockBounds(0.2F, 0.0F, 0.2F, 0.8F, 1F, 0.8F);
        setHardness(1.5F);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor)
    {
        breakStuffs(world, x, y, z);
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return world.isAirBlock(x, y-1, z)
                && (world.isSideSolid(x, y+1, z, ForgeDirection.DOWN) || world.getBlock(x, y+1, z).isLeaves(world, x, y+1, z));
    }

    public void breakStuffs(World world, int x, int y, int z)
    {
        if(!world.isRemote)
        {
            if(!canPlaceBlockAt(world, x, y, z))
            {
                world.setBlockToAir(x, y, z);
                dropBlockAsItem(world, x, y, z, new ItemStack(this));
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        TileWindChime tileWindChime = (TileWindChime) world.getTileEntity(x, y, z);

        if(!world.isRemote && player.isSneaking() && !tileWindChime.isPlaying() && tileWindChime.canPlay)
        {
            tileWindChime.canPlay = false;
            PacketHandler.sendAround(new PacketSound(x, y, z, "windChime"), world.getTileEntity(x, y, z));
            TotemUtil.playMusicForSelector(world, x, y, z, HandlerInitiation.windChime, 0);
            TotemUtil.particlePacket(world, "note", x + 0.5D, y - 0.5D, z + 0.5D, 6, 0.0D, 0.0D, 0.0D, 0.0D);
            TotemUtil.particlePacket(world, "fireworksSpark", x + 0.5D, y - 0.5D, z + 0.5D, 6, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        return Blocks.stained_hardened_clay.getIcon(0, 0);
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileWindChime();
    }
}
