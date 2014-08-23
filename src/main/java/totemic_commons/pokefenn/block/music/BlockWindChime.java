package totemic_commons.pokefenn.block.music;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.block.BlockTileTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.network.PacketHandler;
import totemic_commons.pokefenn.network.client.PacketWindChimeSound;
import totemic_commons.pokefenn.recipe.HandlerInitiation;
import totemic_commons.pokefenn.tileentity.music.TileWindChime;
import totemic_commons.pokefenn.util.EntityUtil;
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
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor)
    {
        breakStuffs(x, y, z, world);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack)
    {
        breakStuffs(x, y, z, world);
    }

    public static void breakStuffs(int x, int y, int z, World world)
    {
        if(!world.isRemote)
        {
            if(world.getTileEntity(x, y, z) instanceof TileWindChime)
            {
                if(!world.isAirBlock(x, y - 1, z) || world.isAirBlock(x, y + 1, z))
                {
                    world.setBlockToAir(x, y, z);
                    EntityUtil.spawnEntityInWorld(world, x, y, z, new ItemStack(ModBlocks.windChime, 1 , 0));
                }
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        TileWindChime tileWindChime = (TileWindChime) world.getTileEntity(x, y, z);

        if(!world.isRemote && player.isSneaking() && !tileWindChime.isPlaying)
        {
            PacketHandler.sendAround(new PacketWindChimeSound(x, y, z), world.getTileEntity(x, y, z));
            TotemUtil.playMusicFromBlockForCeremonySelector(world, x, y, z, HandlerInitiation.windChime, 0);
            MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("note", (double) x + 0.5D, (double) y - 0.5D, (double) z + 0.5D, 6, 0.0D, 0.0D, 0.0D, 0.0D);
            MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("fireworksSpark", (double) x + 0.5D, (double) y - 0.5D, (double) z + 0.5D, 6, 0.0D, 0.0D, 0.0D, 0.0D);

        }
        return true;
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

    /*
    @Override
    public int getMaximumMusic(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return 60;
    }

    @Override
    public int getMusicOutput(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return world.getBlock(x, y + 1, z) != null && world.getBlock(x, y, z).getMaterial() == Material.leaves ? 5 : 4;
    }

    @Override
    public int getRange(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return 7;
    }
    */
}
