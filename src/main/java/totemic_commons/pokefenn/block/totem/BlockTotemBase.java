package totemic_commons.pokefenn.block.totem;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.TotemicStaffUsage;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.block.BlockTileTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.WoodVariant;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 29/01/14
 * Time: 20:20
 */
public class BlockTotemBase extends BlockTileTotemic implements TotemicStaffUsage
{
    public BlockTotemBase()
    {
        super(Material.wood);
        setBlockName(Strings.TOTEM_BASE_NAME);
        setCreativeTab(Totemic.tabsTotem);
        setBlockBounds(0.125F, 0.0F, 0.125F, 0.875F, 1.0F, 0.875F);
        setStepSound(soundTypeWood);
    }

    @Override
    public Item getItemDropped(int par1, Random random, int par2)
    {
        return Item.getItemFromBlock(this);
    }

    @Override
    public int damageDropped(int meta)
    {
        return meta;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack)
    {
        world.setBlockMetadataWithNotify(x, y, z, itemStack.getItemDamage(), 2);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        for(int i = 0; i < WoodVariant.count; i++)
            list.add(new ItemStack(item, 1, i));
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player)
    {
        if(!world.isRemote)
        {
            TileTotemBase tileTotemBase = (TileTotemBase) world.getTileEntity(x, y, z);
            if(tileTotemBase != null)
                if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.totemicStaff && tileTotemBase.isCeremony)
                {
                    tileTotemBase.resetAfterCeremony(true);
                }
        }
    }

    @Override
    public int quantityDropped(Random rand)
    {
        return 0;
    }

    @Override
    public boolean onTotemicStaffRightClick(World world, int x, int y, int z, EntityPlayer player, ItemStack stack)
    {
        if(world.isRemote)
            return true;
        TileTotemBase tileTotemBase = (TileTotemBase) world.getTileEntity(x, y, z);
        if(tileTotemBase != null)
        {
            if(tileTotemBase.isDoingStartup())
            {
                Ceremony trying = tileTotemBase.startupCeremony;
                player.addChatComponentMessage(new ChatComponentText("The Totem Base is doing startup"));
                player.addChatComponentMessage(new ChatComponentText(trying.getLocalizedName()));
                player.addChatComponentMessage(new ChatComponentText("Music amount: " + tileTotemBase.totalCeremonyMelody + " / "
                        + trying.getMusicNeeded()));
                player.addChatComponentMessage(new ChatComponentText("Startup time: " + tileTotemBase.ceremonyStartupTimer + " / "
                        + trying.getMaxStartupTime().getTime()));
            }
            if(tileTotemBase.isDoingCeremonyEffect())
            {
                player.addChatComponentMessage(new ChatComponentText("The Totem Base is doing its effect"));
                player.addChatComponentMessage(new ChatComponentText(tileTotemBase.currentCeremony.getLocalizedName()));
            }

            if(!tileTotemBase.isDoingCeremonyEffect() && !player.isSneaking())
            {
                if(tileTotemBase.canMusicSelect() && tileTotemBase.musicSelector[0] == null && tileTotemBase.musicSelector[1] == null && !tileTotemBase.isDoingCeremonyEffect() && !tileTotemBase.isDoingStartup())
                {
                    player.addChatComponentMessage(new ChatComponentText("No Musical selection"));
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileTotemBase();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        return ModBlocks.totemPole.getIcon(side, meta);
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
        return -1;
    }
}
