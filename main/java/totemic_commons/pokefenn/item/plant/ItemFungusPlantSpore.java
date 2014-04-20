package totemic_commons.pokefenn.item.plant;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.item.ItemTotemic;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemFungusPlantSpore extends ItemTotemic
{
    public Block blockType;

    public ItemFungusPlantSpore(Block blockType)
    {
        this.blockType = blockType;
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.FUNGUS_PLANT_SPORE);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int xPos, int yPos, int zPos, int side, float xClick, float yClick, float zClick)
    {
        if(side != 1)
            return false;
        else if(player.canPlayerEdit(xPos, yPos, zPos, side, stack) && player.canPlayerEdit(xPos, yPos + 1, zPos, side, stack))
        {
            Block soil = world.getBlock(xPos, yPos, zPos);

            if(soil != null && soil == Blocks.mycelium && world.isAirBlock(xPos, yPos + 1, zPos))
            {
                world.setBlock(xPos, yPos + 1, zPos, this.blockType, stack.getItemDamage() * 4, 3);
                --stack.stackSize;
                return true;
            } else
            {
                return false;
            }

        }
        return false;

    }

}
