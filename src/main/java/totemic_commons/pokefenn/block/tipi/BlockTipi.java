package totemic_commons.pokefenn.block.tipi;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
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
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.TileTipi;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockTipi extends Block implements ITileEntityProvider
{
    public static final PropertyDirection FACING = BlockDirectional.FACING;

    public BlockTipi()
    {
        super(Material.cloth);
        setUnlocalizedName(Strings.TIPI_NAME);
        setBlockBounds(0, 0, 0, 1, 0.0625F, 1);
        setHardness(0.2F);
        setStepSound(soundTypeCloth);
        setCreativeTab(Totemic.tabsTotem);
    }

    @Override
    public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        EnumFacing dir = EnumFacing.fromAngle(placer.rotationYaw);
        return getDefaultState().withProperty(FACING, dir);
    }

    private boolean tipiSleep(World world, BlockPos pos, EntityPlayer player)
    {
        if(!world.isRemote)
        {
            if(world.getBiomeGenForCoords(pos) != BiomeGenBase.hell)
            {
                if(world.provider.canRespawnHere())
                {
                    EntityPlayer otherPlayer = null;

                    for(EntityPlayer playerEntity : world.playerEntities)
                    {
                        if(playerEntity.isPlayerSleeping())
                        {
                            BlockPos playerPos = new BlockPos(playerEntity.posX, playerEntity.posY, playerEntity.posZ);
                            if(playerPos.equals(pos))
                            {
                                otherPlayer = playerEntity;
                            }
                        }
                    }

                    if(otherPlayer != null)
                    {
                        player.addChatComponentMessage(new ChatComponentTranslation("tile.bed.occupied"));
                        return true;
                    }

                    EntityPlayer.EnumStatus enumstatus = player.trySleep(pos);

                    if(enumstatus == EntityPlayer.EnumStatus.OK)
                    {
                        return true;
                    }
                    else
                    {
                        if(enumstatus == EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW)
                            player.addChatComponentMessage(new ChatComponentTranslation("tile.bed.noSleep"));
                        else if(enumstatus == EntityPlayer.EnumStatus.NOT_SAFE)
                            player.addChatComponentMessage(new ChatComponentTranslation("tile.bed.notSafe"));

                        return true;
                    }
                }
                else
                    player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("totemicmisc.tipi.nether")));
            }
            else
                player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("totemicmisc.tipi.cantSleep")));

        }
        return true;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
            EnumFacing side, float hitX, float hitY, float hitZ)
    {
        return tipiSleep(world, pos, player);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        BlockDummyTipi.breakTipi(world, pos);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Item getItem(World world, BlockPos pos)
    {
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
    public void setBedOccupied(IBlockAccess world, BlockPos pos, EntityPlayer player, boolean occupied)
    {
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileTipi();
    }

    @Override
    protected BlockState createBlockState()
    {
        return new BlockState(this, FACING);
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
