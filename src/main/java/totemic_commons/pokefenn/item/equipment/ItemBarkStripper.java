package totemic_commons.pokefenn.item.equipment;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.block.BlockCedarLog;
import totemic_commons.pokefenn.item.ItemTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.util.ItemUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemBarkStripper extends ItemTotemic
{
    public ItemBarkStripper()
    {
        super(Strings.BARK_STRIPPER_NAME);
        setMaxStackSize(1);
        setMaxDamage(126);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(player.isSneaking())
            return false;

        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if(block instanceof BlockCedarLog)
        {
            if(!world.isRemote)
            {
                NBTTagCompound tag = ItemUtil.getOrCreateTag(stack);
                int time = tag.getInteger(Strings.INSTR_TIME_KEY);

                time++;
                if(time >= 5)
                {
                    time = 0;
                    //Random random = world.rand;

                    world.setBlockState(pos, ModBlocks.redCedarStripped.getDefaultState()
                            .withProperty(BlockLog.LOG_AXIS, state.getValue(BlockLog.LOG_AXIS)));
                    //EntityItem bark = new EntityItem(world, block.blockX, block.blockY, block.blockZ, new ItemStack(ModItems.subItems, 1 + random.nextInt(3), ItemTotemicItems.cedarBark));
                    //world.spawnEntityInWorld(bark);
                    stack.damageItem(1, player);
                }

                tag.setInteger(Strings.INSTR_TIME_KEY, time);
            }
            return true;
        }
        return false;
    }

}
