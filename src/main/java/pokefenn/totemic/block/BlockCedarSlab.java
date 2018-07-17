package pokefenn.totemic.block;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import com.google.common.base.Optional;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyHelper;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.lib.Strings;

public abstract class BlockCedarSlab extends BlockSlab
{
    public static final IProperty<Integer> VARIANT = PropertyVoid.create("variant");

    public BlockCedarSlab()
    {
        super(Material.WOOD);
        setRegistryName(!isDouble() ? Strings.CEDAR_SLAB_NAME : Strings.DOUBLE_CEDAR_SLAB_NAME);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.CEDAR_SLAB_NAME);
        setHardness(2F);
        setSoundType(SoundType.WOOD);
        setCreativeTab(Totemic.tabsTotem);
        Blocks.FIRE.setFireInfo(this, 5, 20);

        if(!isDouble())
            setDefaultState(blockState.getBaseState().withProperty(HALF, EnumBlockHalf.BOTTOM));
    }

    @Override
    public String getUnlocalizedName(int meta)
    {
        return getUnlocalizedName();
    }

    @Override
    public IProperty<?> getVariantProperty()
    {
        return VARIANT;
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack)
    {
        return 0;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(ModBlocks.cedar_slab);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(ModBlocks.cedar_slab);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return isDouble() ? new BlockStateContainer(this, VARIANT) : new BlockStateContainer(this, HALF, VARIANT);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        if(!isDouble() && state.getValue(HALF) == EnumBlockHalf.TOP)
            return 8;
        else
            return 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState state = getDefaultState();
        if(!isDouble())
            return state.withProperty(HALF, (meta & 8) == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
        else
            return state;
    }

    //Property that can only take one value (Integer.valueOf(0)). Necessary for BlockSlab to work.
    //Replace with PropertyEnum if we introduce more than one type of cedar wood.
    private static class PropertyVoid extends PropertyHelper<Integer>
    {
        protected PropertyVoid(String name)
        {
            super(name, Integer.class);
        }

        static PropertyVoid create(String name)
        {
            return new PropertyVoid(name);
        }

        @Override
        public Collection<Integer> getAllowedValues()
        {
            return Collections.singleton(0);
        }

        @Override
        public Optional<Integer> parseValue(String value)
        {
            return Optional.of(0);
        }

        @Override
        public String getName(Integer value)
        {
            return "0";
        }
    }
}
