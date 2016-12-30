package totemic_commons.pokefenn.item.equipment;

import java.util.Optional;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.totem.TotemEffect;
import totemic_commons.pokefenn.item.ItemTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.totem.TileTotemPole;
import totemic_commons.pokefenn.util.ItemUtil;

public class ItemMedicineBag extends ItemTotemic
{
    public ItemMedicineBag()
    {
        super(Strings.MEDICINE_BAG_NAME);
        setMaxStackSize(1);
    }

    public static Optional<TotemEffect> getEffect(ItemStack stack)
    {
        return Optional.ofNullable(stack.getTagCompound())
                .map(tag -> Totemic.api.registry().getTotem(tag.getString(Strings.MED_BAG_TOTEM_KEY)));
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        if(entity instanceof EntityLivingBase)
        {
            ItemUtil.getOrCreateTag(stack);
            getEffect(stack).ifPresent(eff -> eff.effect((EntityLivingBase) entity));
        }
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos,
            EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof TileTotemPole)
        {
            TileTotemPole pole = (TileTotemPole) tile;
            if(pole.getEffect() != null && pole.getEffect().isPortable())
            {
                ItemStack newStack = stack.copy();
                ItemUtil.getOrCreateTag(newStack).setString(Strings.MED_BAG_TOTEM_KEY, pole.getEffect().getName());
                player.setHeldItem(hand, newStack);
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.FAIL;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack)
    {
        return getEffect(stack)
                .map(eff -> I18n.format(getUnlocalizedName() + ".display", I18n.format(eff.getUnlocalizedName())))
                .orElseGet(() -> super.getItemStackDisplayName(stack));
    }
}
