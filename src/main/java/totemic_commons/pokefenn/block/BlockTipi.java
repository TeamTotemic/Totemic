package totemic_commons.pokefenn.block;

import net.minecraft.block.BlockBed;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.TileTipi;

import java.util.Iterator;

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
        //this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        TileTipi tileTipi = (TileTipi) world.getTileEntity(x, y, z);

        if(player.getHeldItem() != null && player.getHeldItem().getItem() == Items.dye)
        {
            tileTipi.colour = player.getHeldItem().getItemDamage();
        }

        if(!world.isRemote)
        {
            if(player.isSneaking())
            {
                world.setBlockToAir(x, y, z);
                EntityItem tipiItem = new EntityItem(world, x, y, z, new ItemStack(this, 1, 0));
                world.spawnEntityInWorld(tipiItem);
            } else if(canSleepTipi(x, y, z, world, player) || true && (world.getBlock(x, y - 1, z) != null && world.getBlock(x, y - 1, z).getMaterial() == Material.grass))
            {
                if(world.provider.canRespawnHere() && world.getBiomeGenForCoords(x, z) != BiomeGenBase.hell)
                {
                    EntityPlayer entityplayer1 = null;
                    Iterator iterator = world.playerEntities.iterator();

                    while(iterator.hasNext())
                    {
                        EntityPlayer entityplayer2 = (EntityPlayer) iterator.next();

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

                    BlockBed.func_149979_a(world, x, y, z, false);

                    EntityPlayer.EnumStatus enumstatus = player.sleepInBedAt(x, y, z);

                    if(enumstatus == EntityPlayer.EnumStatus.OK)
                    {
                        BlockBed.func_149979_a(world, x, y, z, true);
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
                    player.addChatComponentMessage(new ChatComponentText("You can not sleep in the nether!"));
                }
            } else
                player.addChatComponentMessage(new ChatComponentText("You can not sleep in a tipi without being in the open fields"));

        }
        return true;
    }

    public static boolean canSleepTipi(int x, int y, int z, World world, EntityPlayer player)
    {
        //Todo
        int radius = 4;
        for(int i = -radius; i <= radius; i++)
            for(int j = -radius; j <= radius; j++)
                for(int k = -radius; k <= radius; k++)
                {
                    System.out.println(world.getBlock(x + i, y + k + 5, z + j));
                    if(world.getBlock(x + i, y + k + 5, z + j) != null)
                    {
                        if(world.getBlock(x + i, y + k + 5, z + j).getMaterial() != Material.leaves || world.getBlock(x + i, y + k + 5, z + j).getMaterial() != Material.wood || world.getBlock(x + i, y + k + 5, z + j).getMaterial() != Material.air)
                        {
                            return false;
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
}
