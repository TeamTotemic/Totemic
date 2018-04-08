package pokefenn.totemic.item.equipment.music;

import net.minecraft.block.*;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.music.ItemInstrument;
import pokefenn.totemic.api.music.MusicAPI;
import pokefenn.totemic.lib.Strings;
import pokefenn.totemic.util.EntityUtil;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Set;
import java.util.WeakHashMap;

public class ItemNetherPipe extends ItemInstrument
{

    private final Set<Entity> blaze = Collections.newSetFromMap(new WeakHashMap<>());

    public ItemNetherPipe()
    {
        setRegistryName(Strings.NETHER_PIPE_NAME);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.NETHER_PIPE_NAME);
        setCreativeTab(Totemic.tabsTotem);
        setMaxStackSize(1);
        setMaxDamage(10);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft)
    {
        if (entityLiving instanceof EntityPlayer && !world.isRemote)
        {
            if (!entityLiving.isSneaking())
            {
                int with = 0;
                //This is how many plants have been withered by a play.

                EntityPlayer entityplayer = (EntityPlayer) entityLiving;
                int radius = 3;

                for (int i = -radius; i <= radius; i++)
                    for (int j = -radius; j <= radius; j++)
                        for (int k = -radius; k < radius; k++)
                        {

                            int x = (int) entityplayer.posX + i;
                            int z = (int) entityplayer.posZ + j;
                            int y = (int) entityplayer.posY + k;

                            if (!world.isAirBlock(new BlockPos(x, y, z)))
                            {
                                BlockPos blockPos = new BlockPos(x, y, z);
                                Block block = world.getBlockState(blockPos).getBlock();
                                if (block instanceof IPlantable)
                                    if (((block instanceof BlockCrops) || block instanceof BlockNetherWart || block instanceof BlockStem) && (!block.getUnlocalizedName().contains("sap") && !block.getUnlocalizedName().contains("grass") && !block.getUnlocalizedName().contains("plant")))
                                    {
                                        BlockPos pos = new BlockPos(x, y, z);
                                        IBlockState state = world.getBlockState(pos);
                                        //Unfortunately there is no way to reverse growth for plants, and also the AGE Property is not common in any way
                                        //Because of that I have had to hard code this. It's ugly and really bad. Sorry.
                                        if (block instanceof BlockStem && state.getValue(BlockStem.AGE) > 3)
                                        {
                                            with++;
                                            wither(pos, block, BlockCrops.AGE, world);
                                        } else if (block instanceof BlockCrops && state.getValue(BlockCrops.AGE) > 3)
                                        {
                                            with++;
                                            wither(pos, block, BlockCrops.AGE, world);
                                        } else if (block instanceof BlockNetherWart && state.getValue(BlockNetherWart.AGE) > 2)
                                        {
                                            with += 2;
                                            wither(pos, block, BlockNetherWart.AGE, world);
                                        } else if (block instanceof BlockCarrot && state.getValue(BlockCarrot.AGE) > 3)
                                        {
                                            with++;
                                            wither(pos, block, BlockCarrot.AGE, world);
                                        } else if (block instanceof BlockPotato && state.getValue(BlockPotato.AGE) > 3)
                                        {
                                            with++;
                                            wither(pos, block, BlockPotato.AGE, world);
                                        } else if (block instanceof BlockBeetroot && state.getValue(BlockBeetroot.AGE) > 3)
                                        {
                                            with++;
                                            wither(pos, block, BlockBeetroot.AGE, world);
                                        }

                                    }
                            }

                        }

                if (with > 2)
                {
                    int bonusMusic = with;
                    if (with > 10)
                        bonusMusic = 10;
                    TotemicAPI.get().music().playMusic0(world, entityplayer.posX, entityplayer.posY, entityplayer.posZ, entityplayer, instrument, MusicAPI.DEFAULT_RANGE, instrument.getBaseOutput() + bonusMusic);
                    with = 0;
                }
            } else
            {
                playSelector(stack, entityLiving);
            }
        }
    }

    void wither(BlockPos pos, Block block, PropertyInteger prop, World world)
    {
        //Sets the block to being one metadata below its age
        world.setBlockState(pos, block.getDefaultState().withProperty(prop, block.getMetaFromState(world.getBlockState(pos)) - (2 + itemRand.nextInt(1))));
        ((WorldServer) world).spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX(), pos.getY() + 0.5, pos.getZ(), 4, 0.6D, 0.5D, 0.6D, 0.0D);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        for (int i = 0; i < 10; i++)
            world.spawnParticle(EnumParticleTypes.FLAME, playerIn.posX + 0.1, playerIn.posY + 1F, playerIn.posZ + 0.1, 0, 0.01, 0);
        return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 82000;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }


}
