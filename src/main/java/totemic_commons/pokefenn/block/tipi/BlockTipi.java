package totemic_commons.pokefenn.block.tipi;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
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
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
    {
        int dir = MathHelper.floor_double((double) ((entity.rotationYaw * 4F) / 360F) + 0.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, dir, 0);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        TileTipi tileTipi = (TileTipi) world.getTileEntity(x, y, z);

        if(player.getHeldItem() != null && player.getHeldItem().getItem() == Items.dye && player.getHeldItem().getItemDamage() != tileTipi.colour)
        {
            tileTipi.colour = player.getHeldItem().getItemDamage();

            if(!world.isRemote)
            {
                player.getHeldItem().stackSize--;
                return true;
            }
        }

        if(!world.isRemote)
        {
            if(player.isSneaking())
            {
                world.setBlockToAir(x, y, z);
                EntityItem tipiItem = new EntityItem(world, x, y, z, new ItemStack(this, 1, 0));
                world.spawnEntityInWorld(tipiItem);
            } else if(/*canSleepTipi(x, y, z, world, player) && */world.canBlockSeeTheSky(x, y + 1, z) && world.getBlock(x, y - 1, z) != null && (world.getBlock(x, y - 1, z).getMaterial() == Material.grass || world.getBlock(x, y - 1, z).getMaterial() == Material.ground))
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
                }
            } else
                player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("totemicmisc.tipi.cantSleep")));

        }
        return true;
    }

    public static boolean canSleepTipi(int x, int y, int z, World world, EntityPlayer player)
    {
        //Todo
        int blocks = 0;
        int radius = 5;
        for(int i = -radius; i <= radius; i++)
        {
            for(int j = -radius; j <= radius; j++)
            {
                for(int k = -radius; k <= radius; k++)
                {
                    if(world.getBlock(x + i, y + j + 5, z + k) != null)
                    {
                        Block block = world.getBlock(x + i, y + j + 5, z + j);

                        if(block.getMaterial() != Material.leaves && block.getMaterial() != Material.wood && block.getMaterial() != Material.air)
                        {
                            blocks++;
                            if(blocks > 6)
                                return false;
                        }
                    }
                }
            }
        }

        return true;
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
