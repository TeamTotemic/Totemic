package totemic_commons.pokefenn.block.tipi;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.block.BlockTileTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.TileTipi;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockTipi extends BlockTileTotemic
{
    public BlockTipi()
    {
        super(Material.cloth);
        setUnlocalizedName(Strings.TIPI_NAME);
        setBlockBounds(0, 0, 0, 0, 0, 0);
        setCreativeTab(null);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack)
    {
        int dir = MathHelper.floor_double((entity.rotationYaw * 4F) / 360F + 0.5D) & 3;
        //world.setBlockMetadataWithNotify(pos, dir, 0); FIXME
    }

    public boolean tipiSleep(World world, BlockPos pos, EntityPlayer player)
    {
        if(!world.isRemote)
        {
            if(world.getBiomeGenForCoords(pos) != BiomeGenBase.hell)
            {
                if(world.provider.canRespawnHere())
                {
                    EntityPlayer entityplayer1 = null;

                    for(Object playerEntity : world.playerEntities)
                    {
                        EntityPlayer entityplayer2 = (EntityPlayer) playerEntity;

                        if(entityplayer2.isPlayerSleeping())
                        {
                            BlockPos playerPos = entityplayer2.playerLocation;

                            if(playerPos.equals(pos))
                            {
                                entityplayer1 = entityplayer2;
                            }
                        }
                    }

                    if(entityplayer1 != null)
                    {
                        player.addChatComponentMessage(new ChatComponentTranslation("tile.bed.occupied", new Object[0]));
                        return true;
                    }

                    EntityPlayer.EnumStatus enumstatus = player.trySleep(pos);

                    if(enumstatus == EntityPlayer.EnumStatus.OK)
                    {
                        return true;
                    } else
                    {
                        if(enumstatus == EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW)
                        {
                            player.addChatComponentMessage(new ChatComponentTranslation("tile.bed.noSleep", new Object[0]));
                        } else if(enumstatus == EntityPlayer.EnumStatus.NOT_SAFE)
                        {
                            player.addChatComponentMessage(new ChatComponentTranslation("tile.bed.notSafe", new Object[0]));
                        }

                        return true;
                    }
                } else
                {
                    player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("totemicmisc.tipi.nether")));
                }
            } else
                player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("totemicmisc.tipi.cantSleep")));

        }
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Item getItem(World world, BlockPos pos) {
        return ModItems.tipi;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return ModItems.tipi;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
    {
        return null;
    }

    @Override
    public boolean isBed(IBlockAccess world, BlockPos pos, Entity player)
    {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileTipi();
    }

    //TODO: OBJ or JSON model maybe?
    @Override
    public int getRenderType()
    {
        return -1;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
}
