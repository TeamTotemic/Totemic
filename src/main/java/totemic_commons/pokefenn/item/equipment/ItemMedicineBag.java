package totemic_commons.pokefenn.item.equipment;

import java.util.List;
import java.util.Optional;

import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.totem.TotemEffect;
import totemic_commons.pokefenn.item.ItemTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.totem.StateTotemEffect;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;
import totemic_commons.pokefenn.tileentity.totem.TileTotemPole;
import totemic_commons.pokefenn.util.EntityUtil;
import totemic_commons.pokefenn.util.ItemUtil;

public class ItemMedicineBag extends ItemTotemic
{
    public static final String MED_BAG_TOTEM_KEY = "totem";
    public static final String MED_BAG_CHARGE_KEY = "charge";

    public ItemMedicineBag()
    {
        super(Strings.MEDICINE_BAG_NAME);
        setMaxStackSize(1);
    }

    public static Optional<TotemEffect> getEffect(ItemStack stack)
    {
        return Optional.ofNullable(stack.getTagCompound())
                .map(tag -> Totemic.api.registry().getTotem(tag.getString(MED_BAG_TOTEM_KEY)));
    }

    public static int getCharge(ItemStack stack)
    {
        if(stack.hasTagCompound())
            return stack.getTagCompound().getInteger(MED_BAG_CHARGE_KEY);
        else
            return 0;
    }

    public static int getMaxCharge(ItemStack stack)
    {
        int unbreaking = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack);
        return (4 + 2 * unbreaking) * 60 * 20;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        if(!world.isRemote && world.getTotalWorldTime() % 20 == 0)
            tryCharge(stack, world, entity.getPosition());

        if(stack.getMetadata() != 0)
        {
            int charge = getCharge(stack);
            if(charge > 0)
            {
                getEffect(stack).ifPresent(eff -> {
                    if(world.getTotalWorldTime() % eff.getInterval() == 0)
                    {
                        eff.medicineBagEffect(world, (EntityPlayer) entity, stack, charge);
                        if(!world.isRemote)
                            stack.getTagCompound().setInteger(MED_BAG_CHARGE_KEY, Math.max(charge - eff.getInterval(), 0));
                    }
                });
            }
            else
            {
                stack.setItemDamage(0);
            }
        }
    }

    private void tryCharge(ItemStack stack, World world, BlockPos pos)
    {
        int charge = getCharge(stack);
        int maxCharge = getMaxCharge(stack);
        if(charge < maxCharge)
        {
            getEffect(stack).ifPresent(effect -> {
                if(EntityUtil.getTileEntitiesInRange(TileTotemBase.class, world, pos, 6, 6).stream()
                        .anyMatch(tile -> tile.getState() instanceof StateTotemEffect && tile.getTotemEffectSet().contains(effect)))
                {
                    stack.getTagCompound().setInteger(MED_BAG_CHARGE_KEY, Math.min(charge + maxCharge / 12, maxCharge));
                }
            });
        }
    }

    private ActionResult<ItemStack> openOrClose(ItemStack stack)
    {
        if(getEffect(stack).isPresent() && getCharge(stack) > 0)
        {
            stack.setItemDamage((stack.getMetadata() == 0) ? 1 : 0);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
        else
            return new ActionResult<>(EnumActionResult.FAIL, stack);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        return openOrClose(player.getHeldItem(hand));
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack stack = player.getHeldItem(hand);
        if(!player.isSneaking())
        {
            ItemStack copyStack = player.isCreative() ? stack.copy() : stack; //Workaround for creative mode, otherwise Minecraft will reset the item damage
            ActionResult<ItemStack> result = openOrClose(copyStack);
            if(result.getType() == EnumActionResult.SUCCESS)
                player.setHeldItem(hand, result.getResult());
            return result.getType();
        }
        else
            return trySetEffect(stack, player, world, pos, hand);
    }

    private EnumActionResult trySetEffect(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand)
    {
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof TileTotemPole)
        {
            TotemEffect effect = ((TileTotemPole) tile).getEffect();
            if(effect != null)
            {
                if(!effect.isPortable())
                {
                    if(world.isRemote)
                        player.sendMessage(new TextComponentTranslation("totemicmisc.effectNotPortable", I18n.format(effect.getUnlocalizedName())));
                    return EnumActionResult.FAIL;
                }

                ItemStack newStack = stack.copy();
                NBTTagCompound tag = ItemUtil.getOrCreateTag(newStack);
                tag.setString(MED_BAG_TOTEM_KEY, effect.getName());
                tag.setInteger(MED_BAG_CHARGE_KEY, 0);
                player.setHeldItem(hand, newStack);
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.FAIL;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        return 1.0 - Math.min((double) getCharge(stack) / getMaxCharge(stack), 1.0);
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack)
    {
        return MathHelper.hsvToRGB(Math.min((float) getCharge(stack) / getMaxCharge(stack), 1.0F) / 3.0F, 1.0F, 1.0F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack)
    {
        return getEffect(stack)
                .map(eff -> I18n.format(getUnlocalizedName() + ".display", I18n.format(eff.getUnlocalizedName())))
                .orElseGet(() -> super.getItemStackDisplayName(stack));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        String key;
        if(getEffect(stack).isPresent())
        {
            if(getCharge(stack) > 0)
                key = (stack.getMetadata() == 0) ? "tooltipClosed" : "tooltipOpen";
            else
                key = "tooltipEmpty";
        }
        else
            key = "tooltip";
        tooltip.add(I18n.format(getUnlocalizedName() + "." + key));

        if(advanced)
            tooltip.add(I18n.format(getUnlocalizedName() + ".tooltipCharge", getCharge(stack), getMaxCharge(stack)));
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
    {
        return enchantment == Enchantments.EFFICIENCY || enchantment == Enchantments.UNBREAKING || super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public boolean isEnchantable(ItemStack stack)
    {
        return true;
    }

    @Override
    public int getItemEnchantability()
    {
        return 8;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return slotChanged || !ItemStack.areItemsEqual(oldStack, newStack);
    }

    @Override
    public boolean shouldCauseBlockBreakReset(ItemStack oldStack, ItemStack newStack)
    {
        return !ItemStack.areItemsEqual(oldStack, newStack);
    }
}
