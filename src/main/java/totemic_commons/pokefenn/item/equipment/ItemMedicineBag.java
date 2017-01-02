package totemic_commons.pokefenn.item.equipment;

import java.util.List;
import java.util.Optional;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
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
    public static final int MAX_CHARGE = 5 * 60 * 20;

    public ItemMedicineBag()
    {
        super(Strings.MEDICINE_BAG_NAME);
        setMaxStackSize(1);
        setHasSubtypes(true);
    }

    public static Optional<TotemEffect> getEffect(ItemStack stack)
    {
        return Optional.ofNullable(stack.getTagCompound())
                .map(tag -> Totemic.api.registry().getTotem(tag.getString(Strings.MED_BAG_TOTEM_KEY)));
    }

    public static int getCharge(ItemStack stack)
    {
        if(stack.hasTagCompound())
            return stack.getTagCompound().getInteger(Strings.MED_BAG_CHARGE_KEY);
        else
            return 0;
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
                getEffect(stack).ifPresent(eff -> eff.effect(world, (EntityPlayer) entity, charge));
                stack.getTagCompound().setInteger(Strings.MED_BAG_CHARGE_KEY, charge - 1);
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
        if(charge < MAX_CHARGE)
        {
            getEffect(stack).ifPresent(effect -> {
                if(EntityUtil.getTileEntitiesInRange(world, pos, 6, 6).stream()
                        .filter(tile -> tile instanceof TileTotemBase)
                        .map(tile -> (TileTotemBase) tile)
                        .anyMatch(tile -> tile.getState() instanceof StateTotemEffect && tile.getTotemEffectSet().contains(effect)))
                {
                    stack.getTagCompound().setInteger(Strings.MED_BAG_CHARGE_KEY, Math.min(charge + 20 * 20, MAX_CHARGE));
                }
            });
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
    {
        if(getEffect(stack).isPresent())
        {
            stack.setItemDamage((stack.getMetadata() == 0) ? 1 : 0);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
        else
            return new ActionResult<>(EnumActionResult.FAIL, stack);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos,
            EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!player.isSneaking())
        {
            ItemStack newStack = player.isCreative() ? stack.copy() : stack; //Workaround for creative mode, otherwise Minecraft will reset the item damage
            ActionResult<ItemStack> result = onItemRightClick(newStack, world, player, hand);
            if(result.getType() == EnumActionResult.SUCCESS)
                player.setHeldItem(hand, result.getResult());
            return result.getType();
        }
        else
        {
            TileEntity tile = world.getTileEntity(pos);
            if(tile instanceof TileTotemPole)
            {
                TotemEffect effect = ((TileTotemPole) tile).getEffect();
                if(effect != null)
                {
                    if(!effect.isPortable())
                    {
                        if(player.worldObj.isRemote)
                            player.addChatMessage(new TextComponentTranslation("totemicmisc.effectNotPortable", I18n.format(effect.getUnlocalizedName())));
                        return EnumActionResult.FAIL;
                    }

                    ItemStack newStack = stack.copy();
                    NBTTagCompound tag = ItemUtil.getOrCreateTag(newStack);
                    tag.setString(Strings.MED_BAG_TOTEM_KEY, effect.getName());
                    tag.setInteger(Strings.MED_BAG_CHARGE_KEY, 0);
                    player.setHeldItem(hand, newStack);
                    return EnumActionResult.SUCCESS;
                }
            }
            return EnumActionResult.FAIL;
        }
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        return 1.0 - getCharge(stack) / (double) MAX_CHARGE;
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
                key = (stack.getMetadata() == 0) ? "item.totemic:medicineBag.tooltipClosed" : "item.totemic:medicineBag.tooltipOpen"; //TODO: This is probably temporary until we have textures for the bag
            else
                key = "item.totemic:medicineBag.tooltipEmpty";
        }
        else
            key = "item.totemic:medicineBag.tooltip";
        tooltip.add(I18n.format(key));
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
