package totemic_commons.pokefenn.block.tipi;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayer.SleepResult;
import net.minecraft.init.Biomes;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.TileTipi;

public class BlockTipi extends Block implements ITileEntityProvider
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockTipi()
    {
        super(Material.CLOTH);
        setRegistryName(Strings.TIPI_NAME);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.TIPI_NAME);
        setHardness(0.2F);
        setSoundType(SoundType.CLOTH);
        setCreativeTab(Totemic.tabsTotem);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        EnumFacing dir = EnumFacing.fromAngle(placer.rotationYaw);
        return getDefaultState().withProperty(FACING, dir);
    }

    private boolean tipiSleep(World world, BlockPos pos, EntityPlayer player)
    {
        if(!world.isRemote)
        {
            if(world.getBiome(pos) != Biomes.HELL)
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
                                otherPlayer = playerEntity;
                        }
                    }

                    if(otherPlayer != null)
                    {
                        player.sendMessage(new TextComponentTranslation("tile.bed.occupied"));
                        return true;
                    }

                    SleepResult sleepresult = player.trySleep(pos);

                    if(sleepresult == SleepResult.OK)
                        return true;
                    else
                    {
                        if(sleepresult == SleepResult.NOT_POSSIBLE_NOW)
                            player.sendMessage(new TextComponentTranslation("tile.bed.noSleep"));
                        else if(sleepresult == SleepResult.NOT_SAFE)
                            player.sendMessage(new TextComponentTranslation("tile.bed.notSafe"));

                        return true;
                    }
                }
                else
                    player.sendMessage(new TextComponentTranslation("totemicmisc.tipi.nether"));
            }
            else
                player.sendMessage(new TextComponentTranslation("totemicmisc.tipi.cantSleep"));

        }
        return true;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
            EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        return tipiSleep(world, pos, player);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        int height = 5;
        int radius = 2;
        for(int i = -radius; i <= radius; i++)
            for(int j = 0; j <= height; j++)
                for(int k = -radius; k <= radius; k++)
                {
                    BlockPos p = pos.add(i, j, k);
                    IBlockState s = world.getBlockState(p);

                    if(s.getBlock() == ModBlocks.dummy_tipi)
                    {
                        world.setBlockToAir(p);
                    }
                    else if(s.getBlock() == ModBlocks.tipi)
                    {
                        if(!player.capabilities.isCreativeMode)
                            s.getBlock().dropBlockAsItem(world, p, world.getBlockState(p), 0);
                        world.setBlockToAir(p);
                    }
                }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(this);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return null;
    }

    @Override
    public boolean isBed(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity player)
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
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
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
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return new AxisAlignedBB(0, 0, 0, 1, 0.0625, 1);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
}
