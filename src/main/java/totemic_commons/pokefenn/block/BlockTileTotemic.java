package totemic_commons.pokefenn.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.tileentity.TileTotemic;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 28/01/14
 * Time: 12:33
 */
public abstract class BlockTileTotemic extends Block implements ITileEntityProvider
{
    public BlockTileTotemic(Material material)
    {
        super(material);
        setHardness(2);
        setCreativeTab(Totemic.tabsTotem);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entityLiving, ItemStack itemStack)
    {
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof TileTotemic)
        {
            if(itemStack.hasDisplayName())
            {
                ((TileTotemic) tile).setCustomName(itemStack.getDisplayName());
            }

            ((TileTotemic) tile).setOrientation(EnumFacing.fromAngle(entityLiving.rotationYaw));
            world.markBlockForUpdate(pos);
        }
    }
}
