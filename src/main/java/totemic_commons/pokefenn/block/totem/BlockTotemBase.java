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
import totemic_commons.pokefenn.configuration.ConfigurationSettings;
import totemic_commons.pokefenn.lib.RenderIds;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.Textures;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 29/01/14
 * Time: 20:20
 */
public class BlockTotemBase extends BlockTileTotemic implements ITotemicStaffUsage
{

    private Random rand = new Random();

    public BlockTotemBase()
    {
        super(Material.wood);
        setBlockName(Strings.TOTEM_BASE_NAME);
        setCreativeTab(Totemic.tabsTotem);

    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        int SLOT_ONE = TileTotemBase.SLOT_ONE;

        TileTotemBase tileTotemIntelligence = (TileTotemBase) world.getTileEntity(x, y, z);

        ItemStack heldItem = player.inventory.getCurrentItem();


        if(tileTotemIntelligence != null && !world.isRemote)
        {
            /*

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
            */

        }


        return !(heldItem != null && heldItem.getItem() == ModItems.totemicStaff || heldItem != null && heldItem.getItem() == ModItems.infusedTotemicStaff);

    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileTotemBase();
    }

    @Override
    public void onBasicRightClick(int x, int y, int z, EntityPlayer player, World world)
    {
        Random rand = new Random();
        TileTotemBase tileEntity = (TileTotemBase) world.getTileEntity(x, y, z);

        player.addChatMessage(new ChatComponentText("Chlorophyll Crystal Essence = " + tileEntity.plantEssence));
        player.attackEntityFrom(DamageSource.generic, 2 + rand.nextInt(4));
    }

    @Override
    public void onInfusedRightClick(int x, int y, int z, EntityPlayer player, World world)
    {
        TileTotemBase tileEntity = (TileTotemBase) world.getTileEntity(x, y, z);

        player.addChatMessage(new ChatComponentText("Chlorophyll Crystal Essence = " + tileEntity.plantEssence));
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return RenderIds.RENDER_ID_TOTEM_BASE;
    }
}
