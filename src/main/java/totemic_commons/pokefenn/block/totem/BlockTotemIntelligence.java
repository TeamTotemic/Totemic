package totemic_commons.pokefenn.block.totem;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.ITotemicStaffUsage;
import totemic_commons.pokefenn.block.BlockTileTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.Textures;
import totemic_commons.pokefenn.tileentity.totem.TileTotemIntelligence;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 29/01/14
 * Time: 20:20
 */
public class BlockTotemIntelligence extends BlockTileTotemic implements ITotemicStaffUsage
{

    private Random rand = new Random();

    public BlockTotemIntelligence()
    {
        super(Material.wood);
        setBlockName(Strings.TOTEM_INTELLIGENCE_NAME);
        setCreativeTab(Totemic.tabsTotem);

    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        int SLOT_ONE = TileTotemIntelligence.SLOT_ONE;

        TileTotemIntelligence tileTotemIntelligence = (TileTotemIntelligence) world.getTileEntity(x, y, z);

        ItemStack heldItem = player.inventory.getCurrentItem();


        if(tileTotemIntelligence != null && !world.isRemote)
        {

            if(tileTotemIntelligence.isItemValidForSlot(SLOT_ONE, heldItem))
            {
                tileTotemIntelligence.setInventorySlotContents(SLOT_ONE, heldItem);
                player.destroyCurrentEquippedItem();

            } else if(tileTotemIntelligence.getStackInSlot(SLOT_ONE) != null && heldItem == null)
            {
                EntityItem entityitem = new EntityItem(player.worldObj, player.posX + 0.5D, player.posY + 0.5D, player.posZ + 0.5D, tileTotemIntelligence.getStackInSlot(SLOT_ONE));
                world.spawnEntityInWorld(entityitem);
                tileTotemIntelligence.setInventorySlotContents(SLOT_ONE, null);

            }

            world.markBlockForUpdate(x, y, z);

        }


        return !(heldItem != null && heldItem.getItem() == ModItems.totemicStaff || heldItem != null && heldItem.getItem() == ModItems.infusedTotemicStaff);

    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand)
    {
        TileTotemIntelligence tileTotemIntelligence = (TileTotemIntelligence) world.getTileEntity(x, y, z);

    }


    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {
        dropInventory(world, x, y, z);

        if(world.getTileEntity(x, y, z) instanceof TileTotemIntelligence)
        {
            world.markBlockForUpdate(x, y, z);
        }

        super.breakBlock(world, x, y, z, block, meta);
    }

    private void dropInventory(World world, int x, int y, int z)
    {

        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if(!(tileEntity instanceof IInventory))
            return;

        IInventory inventory = (IInventory) tileEntity;

        for(int i = 0; i < inventory.getSizeInventory(); i++)
        {

            ItemStack itemStack = inventory.getStackInSlot(i);

            if(itemStack != null && itemStack.stackSize > 0)
            {
                float dX = rand.nextFloat() * 0.8F + 0.1F;
                float dY = rand.nextFloat() * 0.8F + 0.1F;
                float dZ = rand.nextFloat() * 0.8F + 0.1F;

                EntityItem entityItem = new EntityItem(world, x + dX, y + dY, z + dZ, new ItemStack(itemStack.getItem(), itemStack.stackSize, itemStack.getItemDamage()));

                if(itemStack.hasTagCompound())
                {
                    entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
                }

                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);
                itemStack.stackSize = 0;
            }
        }

    }

    @SideOnly(Side.CLIENT)
    private IIcon topIcon;
    @SideOnly(Side.CLIENT)
    private IIcon sideIcon;
    @SideOnly(Side.CLIENT)
    private IIcon bottomIcon;

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister register)
    {
        topIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.TOTEM_TABLE_TOP);
        sideIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.TOTEM_TABLE_SIDE);
        bottomIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.TOTEM_TABLE_BOTTOM);

    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        return topIcon;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileTotemIntelligence();
    }

    @Override
    public void onBasicRightClick(int x, int y, int z, EntityPlayer player, World world)
    {
        Random rand = new Random();
        TileTotemIntelligence tileEntity = (TileTotemIntelligence) world.getTileEntity(x, y, z);

        player.addChatMessage(new ChatComponentText("Chlorophyll Crystal Essence = " + tileEntity.plantEssence));
        player.attackEntityFrom(DamageSource.generic, 2 + rand.nextInt(4));
    }

    @Override
    public void onInfusedRightClick(int x, int y, int z, EntityPlayer player, World world)
    {
        TileTotemIntelligence tileEntity = (TileTotemIntelligence) world.getTileEntity(x, y, z);

        player.addChatMessage(new ChatComponentText("Chlorophyll Crystal Essence = " + tileEntity.plantEssence));
    }
}
