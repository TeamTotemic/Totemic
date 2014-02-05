package totemic_commons.pokefenn.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import totemic_commons.pokefenn.lib.Strings;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 04/02/14
 * Time: 18:03
 */
public class ItemTotemBeads extends ItemNormal
{
    public ItemTotemBeads(int id)
    {
        super(id);
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
            tag.setString("horse", "horse");

        }

        return itemStack;
    }

    public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5)
    {
        NBTTagCompound tag = itemStack.getTagCompound();

        if (entity != null)
        {

            if (world.isRemote && entity instanceof EntityPlayer)
            {
            /*
            if (tag.getString("horse").equals("horse"))
            {
                if (world.getWorldTime() % 80L == 0)
                {
                    ((EntityPlayer) entity).addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 120, 1));
                }

            }
            */

            }
        }

    }

}
