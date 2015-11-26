package totemic_commons.pokefenn.item.equipment;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.item.ItemTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.WoodVariant;
import totemic_commons.pokefenn.tileentity.totem.TileTotemPole;

public class ItemTotemWhittlingKnife extends ItemTotemic
{
    public ItemTotemWhittlingKnife()
    {
        super(Strings.TOTEM_WHITTLING_KNIFE_NAME);
        setMaxStackSize(1);
        setContainerItem(this);
        setMaxDamage(254);
    }

    @SideOnly(Side.CLIENT)
    public String getCurrentlyCarving(int i)
    {
        if(i < Totemic.api.getTotemList().size())
            return Totemic.api.getTotemList().get(i).getLocalizedName();
        else if(i == Totemic.api.getTotemList().size())
            return StatCollector.translateToLocal("tile.totemBase.name");
        else
            return "";
    }

    public static int getCarvingIndex(ItemStack stack)
    {
        NBTTagCompound tag = stack.getTagCompound();
        if(tag == null)
            return 0;
        else
            return MathHelper.clamp_int(tag.getInteger(Strings.KNIFE_TOTEM_KEY), 0, Totemic.api.getTotemList().size());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add(StatCollector.translateToLocal("item.totemic:totemWhittlingKnife.tooltip1"));
        list.add(StatCollector.translateToLocal("item.totemic:totemWhittlingKnife.tooltip2"));
        list.add(StatCollector.translateToLocal("item.totemic:totemWhittlingKnife.tooltip3") + " " + getCurrentlyCarving(getCarvingIndex(stack)));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack) {
        return StatCollector.translateToLocalFormatted(getUnlocalizedName() + ".display", getCurrentlyCarving(getCarvingIndex(stack)));
    }

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemStack)
    {
        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        if(player.isSneaking())
            return changeIndex(itemStack, 1);
        else
            return itemStack;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        if(player.isSneaking())
        {
            player.setCurrentItemOrArmor(0, onItemRightClick(stack, world, player));
            return true;
        }
        else
        {
            if(world.isRemote)
                return true;

            Block block = world.getBlock(x, y, z);
            int meta = world.getBlockMetadata(x, y, z);
            WoodVariant wood = WoodVariant.fromLog(block, meta);
            if(wood == null)
                return false;

            int index = getCarvingIndex(stack);
            if(index == Totemic.api.getTotemList().size())
            {
                world.setBlock(x, y, z, ModBlocks.totemBase, wood.ordinal(), 3);
            }
            else if(index < Totemic.api.getTotemList().size())
            {
                world.setBlock(x, y, z, ModBlocks.totemPole, wood.ordinal(), 3);
                TileTotemPole tile = (TileTotemPole)world.getTileEntity(x, y, z);

                tile.effect = Totemic.api.getTotemList().get(getCarvingIndex(stack));
                tile.markDirty();
                world.markBlockForUpdate(x, y, z);
            }
            else
                return false;
            stack.damageItem(1, player);

            return true;
        }
    }

    public static ItemStack changeIndex(ItemStack itemStack, int i)
    {
        ItemStack stack = itemStack.copy();
        if(!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setInteger(Strings.KNIFE_TOTEM_KEY,
                Math.floorMod(i + getCarvingIndex(stack), Totemic.api.getTotemList().size() + 1));
        return stack;
    }

}


