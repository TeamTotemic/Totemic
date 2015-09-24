package totemic_commons.pokefenn.item.equipment;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.api.totem.TotemRegistry;
import totemic_commons.pokefenn.item.ItemTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.WoodVariant;
import totemic_commons.pokefenn.tileentity.totem.TileTotemPole;

public class ItemTotemWhittlingKnife extends ItemTotemic
{

    int time = 0;

    public ItemTotemWhittlingKnife()
    {
        super(Strings.TOTEM_WHITTLING_KNIFE_NAME);
        setMaxStackSize(1);
        setContainerItem(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add("A knife for all your whittlin' needs");
        list.add("Shift and right click to change carving");
        if(stack.getItemDamage() < TotemRegistry.getTotemList().size())
            list.add("Currently Carving: " + TotemRegistry.getTotemList().get(stack.getItemDamage()).getLocalizedName());
        if(stack.getItemDamage() == TotemRegistry.getTotemList().size())
            list.add("Currently Carving: Totem Base");

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
            return new ItemStack(this, 1, (1 + itemStack.getItemDamage()) % (TotemRegistry.getTotemList().size() + 1));
        else
            return itemStack;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        if(world.isRemote)
            return true;

        Block block = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);
        WoodVariant wood = WoodVariant.fromLog(block, meta);
        if(wood == null)
            return false;

        if(stack.getItemDamage() == TotemRegistry.getTotemList().size())
        {
            world.setBlock(x, y, z, ModBlocks.totemBase, wood.ordinal(), 3);
        }
        else if(stack.getItemDamage() < TotemRegistry.getTotemList().size())
        {
            world.setBlock(x, y, z, ModBlocks.totemPole, wood.ordinal(), 3);
            TileTotemPole tile = (TileTotemPole)world.getTileEntity(x, y, z);

            tile.totemId = TotemRegistry.getTotemList().get(stack.getItemDamage()).getTotemId();
            tile.markDirty();
            world.markBlockForUpdate(x, y, z);
        }
        else
            return false;

        return true;
    }

}


