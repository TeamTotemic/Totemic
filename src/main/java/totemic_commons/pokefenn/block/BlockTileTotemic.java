package totemic_commons.pokefenn.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.tileentity.TileTotemic;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 28/01/14
 * Time: 12:33
 */
public abstract class BlockTileTotemic extends BlockContainer
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
            int direction = 0;
            int facing = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

            if(facing == 0)
            {
                direction = EnumFacing.NORTH.ordinal();
            } else if(facing == 1)
            {
                direction = EnumFacing.EAST.ordinal();
            } else if(facing == 2)
            {
                direction = EnumFacing.SOUTH.ordinal();
            } else if(facing == 3)
            {
                direction = EnumFacing.WEST.ordinal();
            }

            if(itemStack.hasDisplayName())
            {
                ((TileTotemic) tile).setCustomName(itemStack.getDisplayName());
            }

            ((TileTotemic) tile).setOrientation(direction);

            world.markBlockForUpdate(pos);
        }
    }

}
