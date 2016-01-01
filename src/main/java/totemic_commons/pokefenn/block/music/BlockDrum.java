package totemic_commons.pokefenn.block.music;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import totemic_commons.pokefenn.block.BlockTileTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.network.PacketHandler;
import totemic_commons.pokefenn.network.client.PacketSound;
import totemic_commons.pokefenn.recipe.HandlerInitiation;
import totemic_commons.pokefenn.tileentity.music.TileDrum;
import totemic_commons.pokefenn.util.TotemUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockDrum extends BlockTileTotemic
{
    public BlockDrum()
    {
        super(Material.wood);
        setBlockName(Strings.DRUM_NAME);
        setBlockBounds(0.2F, 0.0F, 0.2F, 0.8F, 0.8F, 0.8F);
        setStepSound(soundTypeWood);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileDrum();
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player)
    {
        TileDrum tileDrum = (TileDrum) world.getTileEntity(x, y, z);

        if(!world.isRemote && tileDrum.canPlay)
            playDaMusicu((WorldServer)world, x, y, z, player, tileDrum, player.isSneaking());
    }

    public void playDaMusicu(WorldServer world, int x, int y, int z, EntityPlayer player, TileDrum tileDrum, boolean isSneaking)
    {
        if(!isSneaking)
        {
            if(!(player instanceof FakePlayer))
            {
                tileDrum.canPlay = false;
                TotemUtil.playMusic(world, x + 0.5, y + 0.5, z + 0.5, HandlerInitiation.drum, 0, 0);
                TotemUtil.particlePacket(world, "note", x + 0.5, y + 1.2, z + 0.5, 2, 0.0, 0.0, 0.0, 0.0);
                world.markBlockForUpdate(x, y, z);
            }
        } else
        {
            tileDrum.canPlay = false;
            TotemUtil.playMusicForSelector(world, x, y, z, HandlerInitiation.drum, 0);
            TotemUtil.particlePacket(world, "note", x + 0.5, y + 1.2, z + 0.5, 2, 0.0, 0.0, 0.0, 0.0);
            TotemUtil.particlePacket(world, "fireworksSpark", x + 0.5, y + 1.2, z + 0.5, 2, 0.0, 0.0, 0.0, 0.0);
            world.markBlockForUpdate(x, y, z);
        }

        PacketHandler.sendAround(new PacketSound(x, y, z, "drum"), world.getTileEntity(x, y, z));
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        TileDrum tileDrum = (TileDrum) world.getTileEntity(x, y, z);

        if(!world.isRemote)
        {
            if(tileDrum.canPlay)
            {
                playDaMusicu((WorldServer)world, x, y, z, player, tileDrum, player.isSneaking());
            }
        }

        return true;
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        return Blocks.log.getIcon(2, 0);
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
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

}
