package totemic_commons.pokefenn.item.plant;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemMoonglowSeeds extends ItemSeeds
{
    public Block blockType;

    public ItemMoonglowSeeds(Block block, Block block2)
    {
        super(block, block2);
        this.blockType = block;
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.MOONGLOW_SEEDS_NAME);
        setCreativeTab(Totemic.tabsTotem);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int xPos, int yPos, int zPos, int side, float xClick, float yClick, float zClick)
    {
        if(side != 1)
            return false;
        else if(player.canPlayerEdit(xPos, yPos, zPos, side, stack) && player.canPlayerEdit(xPos, yPos + 1, zPos, side, stack))
        {
            Block soil = world.getBlock(xPos, yPos, zPos);

            if(soil != null && soil.canSustainPlant(world, xPos, yPos, zPos, ForgeDirection.UP, this) && world.isAirBlock(xPos, yPos + 1, zPos))
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
