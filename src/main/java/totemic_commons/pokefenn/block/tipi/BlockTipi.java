package totemic_commons.pokefenn.block.tipi;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.block.BlockTileTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.TileTipi;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockTipi extends BlockTileTotemic
{
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyBool OCCUPIED = PropertyBool.create("occupied");

    public BlockTipi()
    {
        super(Material.cloth);
        setUnlocalizedName(Strings.TIPI_NAME);
        setBlockBounds(0, 0, 0, 0, 0, 0);
        setHardness(0.2F);
        setStepSound(soundTypeCloth);
        setCreativeTab(Totemic.tabsTotem);
        setDefaultState(blockState.getBaseState().withProperty(OCCUPIED, false));
    }

    @Override
    public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        EnumFacing dir = EnumFacing.fromAngle(placer.rotationYaw);
        return getDefaultState().withProperty(FACING, dir);
    }

    public boolean tipiSleep(World world, BlockPos pos, EntityPlayer player)
    {
        if(!world.isRemote)
        {
            pos = pos.up();
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
                            BlockPos playerPos = new BlockPos(entityplayer2.posX, entityplayer2.posY, entityplayer2.posZ);
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

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        BlockDummyTipi.breakTipi(world, pos);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Item getItem(World world, BlockPos pos) {
        return Item.getItemFromBlock(this);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(this);
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

    @Override
    protected BlockState createBlockState()
    {
        return new BlockState(this, FACING, OCCUPIED);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(FACING, EnumFacing.HORIZONTALS[meta]);
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

    @Override
    public boolean isFullCube()
    {
        return false;
    }
}
