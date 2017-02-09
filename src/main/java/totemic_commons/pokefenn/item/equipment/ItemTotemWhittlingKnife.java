package totemic_commons.pokefenn.item.equipment;

import java.util.List;

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
import net.minecraft.util.math.MathHelper;
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

    private static final List<TotemEffect> totemList = ((RegistryImpl) Totemic.api.registry()).getTotemList();

    public ItemTotemWhittlingKnife()
    {
        super(Strings.TOTEM_WHITTLING_KNIFE_NAME);
        setMaxStackSize(1);
        setContainerItem(this);
        setMaxDamage(254);
    }

    @SideOnly(Side.CLIENT)
    private String getCarvingName(int i)
    {
        if(i < totemList.size())
            return I18n.format(totemList.get(i).getUnlocalizedName());
        else if(i == totemList.size())
            return I18n.format("tile.totemBase.name");
        else
            return "";
    }

    public static int getCarvingIndex(ItemStack stack)
    {
        NBTTagCompound tag = stack.getTagCompound();
        if(tag == null)
            return totemList.size();
        else
            return MathHelper.clamp_int(tag.getInteger(KNIFE_TOTEM_KEY), 0, totemList.size());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advanced)
    {
        list.add(I18n.format("item.totemic:totemWhittlingKnife.tooltip1"));
        list.add(I18n.format("item.totemic:totemWhittlingKnife.tooltip2"));
        list.add(I18n.format("item.totemic:totemWhittlingKnife.tooltip3", getCarvingName(getCarvingIndex(stack))));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack)
    {
        return I18n.format(getUnlocalizedName() + ".display", getCarvingName(getCarvingIndex(stack)));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand)
    {
        if(player.isSneaking())
            return new ActionResult<>(EnumActionResult.SUCCESS, changeIndex(itemStack, 1));
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
            if(world.isRemote)
                return EnumActionResult.SUCCESS;

            WoodVariant wood = WoodVariant.fromLog(world.getBlockState(pos));
            if(wood == null)
                return EnumActionResult.FAIL;

            int index = getCarvingIndex(stack);
            if(index == totemList.size())
            {
                world.setBlockState(pos, ModBlocks.totemBase.getDefaultState().withProperty(BlockTotemBase.WOOD, wood), 3);
            }
            else if(index < totemList.size())
            {
                world.setBlockState(pos, ModBlocks.totemPole.getDefaultState().withProperty(BlockTotemPole.WOOD, wood), 0);
                TileTotemPole tile = (TileTotemPole)world.getTileEntity(pos);

                tile.setEffect(totemList.get(index));
                tile.markForUpdate();
            }
            else
                return EnumActionResult.FAIL;

            IBlockState state = world.getBlockState(pos);
            state.getBlock().onBlockPlacedBy(world, pos, state, player, stack);
            stack.damageItem(1, player);

            return EnumActionResult.SUCCESS;
        }
    }

    public static ItemStack changeIndex(ItemStack itemStack, int i)
    {
        ItemStack stack = itemStack.copy();
        int newIndex = (i + getCarvingIndex(stack)) % (totemList.size() + 1);
        if(newIndex < 0)
            newIndex += totemList.size() + 1;
        ItemUtil.getOrCreateTag(stack).setInteger(KNIFE_TOTEM_KEY, newIndex);
        return stack;
    }

}
