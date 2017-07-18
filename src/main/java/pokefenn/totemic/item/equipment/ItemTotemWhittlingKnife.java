package pokefenn.totemic.item.equipment;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
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
import pokefenn.totemic.ModBlocks;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.apiimpl.RegistryImpl;
import pokefenn.totemic.block.totem.BlockTotemBase;
import pokefenn.totemic.block.totem.BlockTotemPole;
import pokefenn.totemic.item.ItemTotemic;
import pokefenn.totemic.lib.Strings;
import pokefenn.totemic.lib.WoodVariant;
import pokefenn.totemic.tileentity.totem.TileTotemPole;
import pokefenn.totemic.util.ItemUtil;

public class ItemTotemWhittlingKnife extends ItemTotemic
{
    public static final String KNIFE_TOTEM_KEY = "totem";
    public static final String TOTEM_BASE_PLACEHOLDER_NAME = "";

    public ItemTotemWhittlingKnife()
    {
        super(Strings.TOTEM_WHITTLING_KNIFE_NAME);
        setMaxStackSize(1);
        setMaxDamage(250);
    }

    @SideOnly(Side.CLIENT)
    private static String getCarvingName(@Nullable TotemEffect effect)
    {
        return I18n.format((effect != null) ? effect.getUnlocalizedName() : ModBlocks.totem_base.getUnlocalizedName() + ".name");
    }

    @Nullable
    public static TotemEffect getCarvingEffect(ItemStack stack)
    {
        NBTTagCompound tag = stack.getTagCompound();
        if(tag == null)
            return null;

        String name = tag.getString(KNIFE_TOTEM_KEY);
        if(!name.equals(TOTEM_BASE_PLACEHOLDER_NAME))
            return Totemic.api.registry().getTotem(name);
        else
            return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        tooltip.add(I18n.format(getUnlocalizedName() + ".tooltip1"));
        tooltip.add(I18n.format(getUnlocalizedName() + ".tooltip2"));
        tooltip.add(I18n.format(getUnlocalizedName() + ".tooltip3", getCarvingName(getCarvingEffect(stack))));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack)
    {
        return I18n.format(getUnlocalizedName() + ".display", getCarvingName(getCarvingEffect(stack)));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if(player.isSneaking())
            return new ActionResult<>(EnumActionResult.SUCCESS, changeIndex(stack, true));
        else
            return new ActionResult<>(EnumActionResult.FAIL, stack);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack stack = player.getHeldItem(hand);
        if(player.isSneaking())
        {
            player.setHeldItem(hand, changeIndex(stack, true));
            return EnumActionResult.SUCCESS;
        }
        else
        {
            IBlockState state = world.getBlockState(pos);
            WoodVariant wood = WoodVariant.fromLog(state);
            if(wood == null)
            {
                //Fall back to oak if it is an unrecognized log type
                if(state.getBlock().isWood(world, pos) && state.getBlock() != ModBlocks.stripped_cedar_log)
                    wood = WoodVariant.OAK;
                else
                    return EnumActionResult.FAIL;
            }

            TotemEffect effect = getCarvingEffect(stack);
            if(effect != null)
            {
                world.setBlockState(pos, ModBlocks.totem_pole.getDefaultState().withProperty(BlockTotemPole.WOOD, wood), 0);
                TileTotemPole tile = (TileTotemPole) world.getTileEntity(pos);

                tile.setEffect(effect);
                tile.markForUpdate();
            }
            else
            {
                world.setBlockState(pos, ModBlocks.totem_base.getDefaultState().withProperty(BlockTotemBase.WOOD, wood), 3);
            }

            state = world.getBlockState(pos);
            state.getBlock().onBlockPlacedBy(world, pos, state, player, stack);
            stack.damageItem(1, player);

            return EnumActionResult.SUCCESS;
        }
    }

    public static ItemStack changeIndex(ItemStack itemStack, boolean direction)
    {
        List<String> totemList = ((RegistryImpl) Totemic.api.registry()).getTotemList();
        ItemStack stack = itemStack.copy();
        int index = totemList.indexOf(ItemUtil.getOrCreateTag(stack).getString(KNIFE_TOTEM_KEY));

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

        String name = (index != -1) ? totemList.get(index) : TOTEM_BASE_PLACEHOLDER_NAME;
        stack.getTagCompound().setString(KNIFE_TOTEM_KEY, name);
        return stack;
    }

}
