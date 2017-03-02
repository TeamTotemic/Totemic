package totemic_commons.pokefenn.item.equipment;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.totem.TotemEffect;
import totemic_commons.pokefenn.apiimpl.RegistryImpl;
import totemic_commons.pokefenn.block.totem.BlockTotemBase;
import totemic_commons.pokefenn.block.totem.BlockTotemPole;
import totemic_commons.pokefenn.item.ItemTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.WoodVariant;
import totemic_commons.pokefenn.tileentity.totem.TileTotemPole;
import totemic_commons.pokefenn.util.ItemUtil;

public class ItemTotemWhittlingKnife extends ItemTotemic
{
    public static final String KNIFE_TOTEM_KEY = "totem";
    public static final String TOTEM_BASE_PLACEHOLDER_NAME = "";

    private static final List<TotemEffect> totemList = ((RegistryImpl) Totemic.api.registry()).getTotemList();

    public ItemTotemWhittlingKnife()
    {
        super(Strings.TOTEM_WHITTLING_KNIFE_NAME);
        setMaxStackSize(1);
        setContainerItem(this);
        setMaxDamage(254);
    }

    @SideOnly(Side.CLIENT)
    private static String getCarvingName(@Nullable TotemEffect effect)
    {
        return I18n.format((effect != null) ? effect.getUnlocalizedName() : "tile.totemBase.name");
    }

    @Nullable
    public static TotemEffect getCarvingEffect(ItemStack stack)
    {
        NBTTagCompound tag = stack.getTagCompound();
        if(tag == null)
            return null;

        //Legacy compatibility with old method of storing the totem effect
        //TODO: Remove at some point
        if(tag.hasKey(KNIFE_TOTEM_KEY, 99))
        {
            int index = tag.getInteger(KNIFE_TOTEM_KEY);
            return (0 <= index && index < totemList.size()) ? totemList.get(index) : null;
        }

        String name = tag.getString(KNIFE_TOTEM_KEY);
        if(!name.equals(TOTEM_BASE_PLACEHOLDER_NAME))
            return Totemic.api.registry().getTotem(name);
        else
            return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced)
    {
        list.add(I18n.format("item.totemic:totemWhittlingKnife.tooltip1"));
        list.add(I18n.format("item.totemic:totemWhittlingKnife.tooltip2"));
        list.add(I18n.format("item.totemic:totemWhittlingKnife.tooltip3", getCarvingName(getCarvingEffect(stack))));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack)
    {
        return I18n.format(getUnlocalizedName() + ".display", getCarvingName(getCarvingEffect(stack)));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand)
    {
        if(player.isSneaking())
            return new ActionResult<>(EnumActionResult.SUCCESS, changeIndex(itemStack, true));
        else
            return new ActionResult<>(EnumActionResult.FAIL, itemStack);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos,
            EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(player.isSneaking())
        {
            player.setHeldItem(hand, onItemRightClick(stack, world, player, hand).getResult());
            return EnumActionResult.SUCCESS;
        }
        else
        {
            IBlockState state = world.getBlockState(pos);
            WoodVariant wood = WoodVariant.fromLog(state);
            if(wood == null)
            {
                //Fall back to oak if it is an unrecognized log type
                if(state.getBlock().isWood(world, pos))
                    wood = WoodVariant.OAK;
                else
                    return EnumActionResult.FAIL;
            }

            TotemEffect effect = getCarvingEffect(stack);
            if(effect != null)
            {
                world.setBlockState(pos, ModBlocks.totemPole.getDefaultState().withProperty(BlockTotemPole.WOOD, wood), 0);
                TileTotemPole tile = (TileTotemPole) world.getTileEntity(pos);

                tile.setEffect(effect);
                tile.markForUpdate();
            }
            else
            {
                world.setBlockState(pos, ModBlocks.totemBase.getDefaultState().withProperty(BlockTotemBase.WOOD, wood), 3);
            }

            state = world.getBlockState(pos);
            state.getBlock().onBlockPlacedBy(world, pos, state, player, stack);
            stack.damageItem(1, player);

            return EnumActionResult.SUCCESS;
        }
    }

    public static ItemStack changeIndex(ItemStack itemStack, boolean direction)
    {
        ItemStack stack = itemStack.copy();
        int index = totemList.indexOf(getCarvingEffect(stack)); //TODO: Maybe optimize this?

        //-1 represents the Totem Base
        if(index == -1)
        {
            index = direction ? 0 : totemList.size() - 1;
        }
        else
        {
            index += direction ? 1 : -1;
            if(index >= totemList.size())
                index = -1;
        }

        String name = (index != -1) ? totemList.get(index).getName() : TOTEM_BASE_PLACEHOLDER_NAME;
        ItemUtil.getOrCreateTag(stack).setString(KNIFE_TOTEM_KEY, name);
        return stack;
    }

}
