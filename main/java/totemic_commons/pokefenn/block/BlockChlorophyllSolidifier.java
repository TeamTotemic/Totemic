package totemic_commons.pokefenn.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.fluid.ModFluids;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.Textures;
import totemic_commons.pokefenn.tileentity.TileChlorophyllSolidifier;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 28/01/14
 * Time: 13:11
 */
public class BlockChlorophyllSolidifier extends BlockTile
{
    public BlockChlorophyllSolidifier(int id)
    {
        super(id, Material.rock);
        setUnlocalizedName(Strings.CHLOROPHYLL_SOLIDIFIER_NAME);
        setCreativeTab(Totemic.tabsTotem);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {

        TileChlorophyllSolidifier tileChlorophyllSolidifier = (TileChlorophyllSolidifier) world.getBlockTileEntity(x, y, z);

        ItemStack heldItem = player.inventory.getCurrentItem();

        int SLOT_ONE = TileChlorophyllSolidifier.SLOT_ONE;

        if (tileChlorophyllSolidifier != null && !world.isRemote)
        {


            if (tileChlorophyllSolidifier.getStackInSlot(SLOT_ONE) == null && heldItem != null && heldItem.itemID != ModItems.bottleChlorophyll.itemID && heldItem.itemID != ModItems.bucketChlorophyll.itemID && !heldItem.hasTagCompound())
            {

                tileChlorophyllSolidifier.setInventorySlotContents(SLOT_ONE, new ItemStack(heldItem.getItem(), 1, heldItem.getItemDamage()));
                heldItem.stackSize--;

            } else if (tileChlorophyllSolidifier.getStackInSlot(SLOT_ONE) != null && heldItem == null)
            {
                EntityItem entityitem = new EntityItem(player.worldObj, player.posX + 0.5D, player.posY + 0.5D, player.posZ + 0.5D, tileChlorophyllSolidifier.getStackInSlot(SLOT_ONE));
                player.worldObj.spawnEntityInWorld(entityitem);
                tileChlorophyllSolidifier.setInventorySlotContents(SLOT_ONE, null);


            } else if (heldItem != null)
            {

                if (heldItem.itemID == ModItems.bottleChlorophyll.itemID)
                {
                    tileChlorophyllSolidifier.fill(ForgeDirection.getOrientation(side), new FluidStack(ModFluids.fluidChlorophyll, 1000), true);
                    heldItem.stackSize--;

                } else if (heldItem.itemID == ModItems.bucketChlorophyll.itemID)
                {
                    tileChlorophyllSolidifier.fill(ForgeDirection.getOrientation(side), new FluidStack(ModFluids.fluidChlorophyll, 1000), true);
                    player.destroyCurrentEquippedItem();
                    EntityItem entityBucket = new EntityItem(player.worldObj, player.posX + 0.5D, player.posY + 0.5D, player.posZ + 0.5D, new ItemStack(Item.bucketEmpty));
                    player.worldObj.spawnEntityInWorld(entityBucket);

                }

            }

            world.markBlockForUpdate(x, y, z);
        }

        return !player.isSneaking();
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileChlorophyllSolidifier();
    }

    @SideOnly(Side.CLIENT)
    private Icon topAndBotIcon;
    @SideOnly(Side.CLIENT)
    private Icon sideIcon;

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister register)
    {
        topAndBotIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.CHLOROPHYLL_SOLIDIFIER_TOP_AND_BOT);
        sideIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.CHLOROPHYLL_SOLIDIFIER_SIDES);

    }

    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta)
    {
        if (side == 0 || side == 1)
        {
            return topAndBotIcon;
        } else return sideIcon;
    }


}
