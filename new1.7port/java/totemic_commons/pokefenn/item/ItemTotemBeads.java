package totemic_commons.pokefenn.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.totem.TotemEffectOcelot;
import totemic_commons.pokefenn.totem.TotemUtil;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 04/02/14
 * Time: 18:03
 */
public class ItemTotemBeads extends ItemTotemic
{
    public ItemTotemBeads()
    {
        super();
        setUnlocalizedName(Strings.TOTEM_BEADS_NAME);
        setMaxStackSize(1);
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4)
    {
        NBTTagCompound tag = itemStack.getTagCompound();

        //list.add("Current charge:" + tag.getInteger("charge"));

        if (!itemStack.hasTagCompound())
        {
            list.add("No Totems set to this currently");

        }

    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        NBTTagCompound tag = itemStack.getTagCompound();

        if (!world.isRemote)
        {
            //tag.setName("horse");
        }

        return itemStack;
    }

    public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5)
    {
        NBTTagCompound tag = itemStack.getTagCompound();

        if (entity != null)
        {
            if (!world.isRemote && entity instanceof EntityPlayer)
            {
                if (((EntityPlayer) entity).inventory.hasItem(ModItems.chlorophyllCrystal))
                {
                    if (getChlorophyll(((EntityPlayer) entity)) != null)
                    {
                        //Todo loop through totems and stuff
                        if (TotemUtil.canDoEffect(getChlorophyll(((EntityPlayer) entity)), TotemUtil.decrementAmount(7)))
                        {
                            TotemEffectOcelot.effectBead(((EntityPlayer) entity), world, this, 7);
                        }
                    }
                }
            }
        }
    }

    public static void decreaseChlorophyll(EntityPlayer player, int i)
    {

        for (int j = 1; j <= player.inventory.getSizeInventory(); j++)
        {
            if (player.inventory.getStackInSlot(j) != null)
            {
                if (player.inventory.getStackInSlot(j).getItem() == ModItems.chlorophyllCrystal)
                {
                    if (player.inventory.getStackInSlot(j).getItemDamage() >= 1)
                    {
                        player.inventory.getStackInSlot(i).setItemDamage(ModItems.chlorophyllCrystal.getMaxDamage() - player.inventory.getStackInSlot(j).getItemDamage() - TotemUtil.decrementAmount(i));
                        return;
                    }
                }
            }
        }
    }

    public static ItemStack getChlorophyll(EntityPlayer player)
    {
        for (int j = 1; j <= player.inventory.getSizeInventory(); j++)
        {
            if (player.inventory.getStackInSlot(j) != null)
            {
                if (player.inventory.getStackInSlot(j).getItem() == ModItems.chlorophyllCrystal)
                {
                    return player.inventory.getStackInSlot(j);
                }

            } else
                return null;
        }
        return null;
    }

}
