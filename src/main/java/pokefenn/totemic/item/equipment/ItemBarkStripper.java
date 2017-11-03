package pokefenn.totemic.item.equipment;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pokefenn.totemic.block.BlockCedarLog;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.item.ItemTotemic;
import pokefenn.totemic.lib.Strings;
import pokefenn.totemic.util.ItemUtil;

public class ItemBarkStripper extends ItemTotemic
{
    public static final String TIME_KEY = "time";

    public ItemBarkStripper()
    {
        super(Strings.BARK_STRIPPER_NAME);
        setMaxStackSize(1);
        setMaxDamage(131);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(player.isSneaking())
            return EnumActionResult.FAIL;

        ItemStack stack = player.getHeldItem(hand);
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if(block instanceof BlockCedarLog)
        {
            NBTTagCompound tag = ItemUtil.getOrCreateTag(stack);
            int time = tag.getInteger(TIME_KEY);

            time++;
            if(time >= 5)
            {
                time = 0;
                //Random random = world.rand;

                world.setBlockState(pos, ModBlocks.stripped_cedar_log.getDefaultState()
                        .withProperty(BlockLog.LOG_AXIS, state.getValue(BlockLog.LOG_AXIS)));
                //EntityItem bark = new EntityItem(world, block.blockX, block.blockY, block.blockZ, new ItemStack(ModItems.subItems, 1 + random.nextInt(3), ItemTotemicItems.cedarBark));
                //world.spawnEntityInWorld(bark);
                stack.damageItem(1, player);
            }

            tag.setInteger(TIME_KEY, time);

            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }

}
