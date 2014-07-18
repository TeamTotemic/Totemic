package totemic_commons.pokefenn.block.music;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.api.music.IMusic;
import totemic_commons.pokefenn.api.music.MusicEnum;
import totemic_commons.pokefenn.block.BlockTileTotemic;
import totemic_commons.pokefenn.lib.RenderIds;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.music.TileWindChime;
import totemic_commons.pokefenn.util.TotemUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockWindChime extends BlockTileTotemic implements IMusic
{
    public BlockWindChime()
    {
        super(Material.grass);
        setBlockName(Strings.WIND_CHIME_NAME);
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
            if(world.getTileEntity(x, y, z) instanceof TileWindChime)
            {
                if(!world.isAirBlock(x, y - 1, z) && world.isAirBlock(x, y + 1, z))
                {
                    world.setBlockToAir(x, y, z);
                    EntityItem entityItem = new EntityItem(world, x, y, z, new ItemStack(ModBlocks.windChime, 1, 0));

                    world.spawnEntityInWorld(entityItem);
                }
            }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        if(!world.isRemote && player.isSneaking())
        {
            TotemUtil.playMusicFromBlockForCeremonySelector(world, player, x, y, z, musicEnum(new ItemStack(this, 1, 0), world, x, y, z, true, player), this.getRange(world, x, y, z, true, player));
            MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("note", (double) x + 0.5D, (double) y - 0.5D, (double) z + 0.5D, 6, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        return true;
    }

    @Override
    public int getRenderType()
    {
        return RenderIds.RENDER_ID_WIND_CHIME;
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

    @Override
    public MusicEnum musicEnum(ItemStack itemStack, World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return MusicEnum.WIND_CHIME;
    }

    @Override
    public int getMaximumMusic(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return 80;
    }

    @Override
    public int getMusicOutput(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return 4;
    }

    @Override
    public int getRange(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return 7;
    }
}
