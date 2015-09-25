package totemic_commons.pokefenn.block.tipi;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
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
        setBlockName(Strings.TIPI_NAME);
        setBlockBounds(0, 0, 0, 0, 0, 0);
        setCreativeTab(null);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
    {
        int dir = MathHelper.floor_double((entity.rotationYaw * 4F) / 360F + 0.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, dir, 0);
    }

    public boolean tipiSleep(World world, int x, int y, int z, EntityPlayer player)
    {
        if(!world.isRemote)
        {
            if(world.getBiomeGenForCoords(x, z) != BiomeGenBase.hell)
            {
                if(world.provider.canRespawnHere())
                {
                    EntityPlayer entityplayer1 = null;

                    for(Object playerEntity : world.playerEntities)
                    {
                        EntityPlayer entityplayer2 = (EntityPlayer) playerEntity;

                        if(entityplayer2.isPlayerSleeping())
                        {
                            ChunkCoordinates chunkcoordinates = entityplayer2.playerLocation;

                            if(chunkcoordinates.posX == x && chunkcoordinates.posY == y && chunkcoordinates.posZ == z)
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

                    //BlockBed.func_149979_a(world, x, y, z, false);

                    EntityPlayer.EnumStatus enumstatus = player.sleepInBedAt(x, y, z);

                    if(enumstatus == EntityPlayer.EnumStatus.OK)
                    {
                        //BlockBed.func_149979_a(world, x, y, z, true);
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
    public Item getItem(World world, int x, int y, int z) {
        return ModItems.tipi;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        return false;
    }

    @Override
    public boolean isBed(IBlockAccess world, int x, int y, int z, EntityLivingBase player)
    {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileTipi();
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

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
