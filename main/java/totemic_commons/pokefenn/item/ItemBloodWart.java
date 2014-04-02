package totemic_commons.pokefenn.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.block.plant.BlockBloodWart;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemBloodWart extends Item
{
    public Block blockType;

    public ItemBloodWart(Block block1, Block block2)
    {
        super();
        this.blockType = block1;
        setCreativeTab(Totemic.tabsTotem);
        setUnlocalizedName(Strings.BLOODWART_NAME);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int xPos, int yPos, int zPos, int side, float xClick, float yClick, float zClick)
    {
        if (side != 1)
            return false;
        else if (player.canPlayerEdit(xPos, yPos, zPos, side, stack) && player.canPlayerEdit(xPos, yPos + 1, zPos, side, stack))
        {
            Block soil = world.getBlock(xPos, yPos, zPos);

            if (soil != null && soil.canSustainPlant(world, xPos, yPos, zPos, ForgeDirection.UP, ((IPlantable)ModBlocks.bloodwart)) && world.isAirBlock(xPos, yPos + 1, zPos))
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
