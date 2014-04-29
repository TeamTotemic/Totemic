package totemic_commons.pokefenn.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import totemic_commons.pokefenn.lib.RenderIds;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.Textures;
import totemic_commons.pokefenn.tileentity.TileTotemTorch;

import java.util.Random;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockTotemTorch extends BlockTileTotemic
{
    public BlockTotemTorch()
    {
        super(Material.wood);
        setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 1.3F, 0.7F);
        setBlockName(Strings.TOTEM_TORCH_NAME);
        setLightLevel(1F);
    }

    @SideOnly(Side.CLIENT)
    public IIcon iconThingy;

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand)
    {
        TileTotemTorch tileEntity = (TileTotemTorch) world.getTileEntity(x, y, z);

        //if (tileEntity.isActive)
        for(int i = 0; i < 32; i++)
        {
            world.spawnParticle("flame", x + 0.5, y + 1F, z + 0.5, 0, 0, 0);
            world.spawnParticle("smoke", x + 0.5, y + 1F, z + 0.5, 0, 0, 0);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        iconThingy = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + "totemsRange");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess p_149673_1_, int p_149673_2_, int p_149673_3_, int p_149673_4_, int p_149673_5_)
    {
        return this.iconThingy;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        return true;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {

        return RenderIds.RENDER_ID_TOTEM_TORCH;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileTotemTorch();
    }
}
