package totemic_commons.pokefenn.item;

import java.util.List;
import java.util.function.Function;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.entity.animal.EntityBaldEagle;
import totemic_commons.pokefenn.entity.animal.EntityBuffalo;
import totemic_commons.pokefenn.entity.boss.EntityBaykok;
import totemic_commons.pokefenn.lib.Strings;

public class ItemTotemicSpawnEgg extends ItemMonsterPlacer
{
    private static final EggInfo[] EGGS = {
        new EggInfo(EntityBuffalo::new, Strings.BUFFALO_NAME, 0x2A1C12, 0x885F3E),
        new EggInfo(EntityBaykok::new, Strings.BAYKOK_NAME, 0xE0E0E0, 0xF8DAD2),
        new EggInfo(EntityBaldEagle::new, Strings.BALD_EAGLE_NAME, 0x4B4136, 0xF5E6A3)
    };

    public ItemTotemicSpawnEgg()
    {
        setCreativeTab(Totemic.tabsTotem);
        setUnlocalizedName("monsterPlacer");
        setTextureName("spawn_egg");
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        EggInfo info = getEggInfo(stack);
        return StatCollector.translateToLocal(this.getUnlocalizedName() + ".name") + " "
            + StatCollector.translateToLocal("entity.totemic." + info.entityName + ".name");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getColorFromItemStack(ItemStack stack, int layer)
    {
        EggInfo info = getEggInfo(stack);
        return layer == 0 ? info.primaryColor : info.secondaryColor;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs tabs, List<ItemStack> itemList)
    {
        for(int i = 0; i < EGGS.length; i++)
            itemList.add(new ItemStack(item, 1, i));
    }

    /* For some reasoin, the method ItemMonsterPlacer.spawnCreature is static, so we can't override it.
     * We are forced to copy the methods onItemUse and onItemRightClick just so we can replace the call to
     * spawnCreature with a call to spawnTotemicCreature. */
    @Override
    public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        if (p_77648_3_.isRemote)
        {
            return true;
        }
        else
        {
            Block block = p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);
            p_77648_4_ += Facing.offsetsXForSide[p_77648_7_];
            p_77648_5_ += Facing.offsetsYForSide[p_77648_7_];
            p_77648_6_ += Facing.offsetsZForSide[p_77648_7_];
            double d0 = 0.0D;

            if (p_77648_7_ == 1 && block.getRenderType() == 11)
            {
                d0 = 0.5D;
            }

            Entity entity = spawnTotemicCreature(p_77648_3_, p_77648_1_, (double)p_77648_4_ + 0.5D, (double)p_77648_5_ + d0, (double)p_77648_6_ + 0.5D);

            if (entity != null)
            {
                if (entity instanceof EntityLivingBase && p_77648_1_.hasDisplayName())
                {
                    ((EntityLiving)entity).setCustomNameTag(p_77648_1_.getDisplayName());
                }

                if (!p_77648_2_.capabilities.isCreativeMode)
                {
                    --p_77648_1_.stackSize;
                }
            }

            return true;
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer player)
    {
        if (worldIn.isRemote)
        {
            return itemStackIn;
        }
        else
        {
            MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(worldIn, player, true);

            if (movingobjectposition == null)
            {
                return itemStackIn;
            }
            else
            {
                if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
                {
                    int i = movingobjectposition.blockX;
                    int j = movingobjectposition.blockY;
                    int k = movingobjectposition.blockZ;

                    if (!worldIn.canMineBlock(player, i, j, k))
                    {
                        return itemStackIn;
                    }

                    if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, itemStackIn))
                    {
                        return itemStackIn;
                    }

                    if (worldIn.getBlock(i, j, k) instanceof BlockLiquid)
                    {
                        Entity entity = spawnTotemicCreature(worldIn, itemStackIn, (double)i, (double)j, (double)k);

                        if (entity != null)
                        {
                            if (entity instanceof EntityLivingBase && itemStackIn.hasDisplayName())
                            {
                                ((EntityLiving)entity).setCustomNameTag(itemStackIn.getDisplayName());
                            }

                            if (!player.capabilities.isCreativeMode)
                            {
                                --itemStackIn.stackSize;
                            }
                        }
                    }
                }

                return itemStackIn;
            }
        }
    }

    private static Entity spawnTotemicCreature(World world, ItemStack stack, double x, double y, double z)
    {
        try
        {
            EggInfo info = getEggInfo(stack);
            Entity entity = info.entityFactory.apply(world);
            if(entity != null && entity instanceof EntityLivingBase)
            {
                EntityLiving entityliving = (EntityLiving)entity;
                entity.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
                entityliving.rotationYawHead = entityliving.rotationYaw;
                entityliving.renderYawOffset = entityliving.rotationYaw;
                entityliving.onSpawnWithEgg((IEntityLivingData)null);
                world.spawnEntityInWorld(entity);
                entityliving.playLivingSound();
            }
            return entity;
        }
        catch(Exception e)
        {
            Totemic.logger.catching(e);
            return null;
        }
    }

    private static EggInfo getEggInfo(ItemStack stack)
    {
        return EGGS[MathHelper.clamp_int(stack.getItemDamage(), 0, EGGS.length - 1)];
    }
    
    private static class EggInfo
    {
        final Function<World, ? extends Entity> entityFactory;
        final String entityName;
        final int primaryColor;
        final int secondaryColor;

        EggInfo(Function<World, ? extends Entity> entityFactory, String entityName, int primaryColor, int secondaryColor)
        {
            this.entityFactory = entityFactory;
            this.entityName = entityName;
            this.primaryColor = primaryColor;
            this.secondaryColor = secondaryColor;
        }
    }
}
