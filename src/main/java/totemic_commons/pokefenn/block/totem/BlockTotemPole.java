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
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.ITotemicStaffUsage;
import totemic_commons.pokefenn.api.totem.TotemRegistry;
import totemic_commons.pokefenn.block.BlockTileTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.WoodVariant;
import totemic_commons.pokefenn.tileentity.totem.TileTotemPole;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 02/02/14
 * Time: 13:03
 */
public class BlockTotemPole extends BlockTileTotemic implements ITotemicStaffUsage
{
    public BlockTotemPole()
    {
        super(Material.wood);
        setBlockName(Strings.TOTEM_POLE_NAME);
        setCreativeTab(Totemic.tabsTotem);
        setBlockBounds(0.1875F, -0.03125F, 0.1875F, 0.8125F, 0.96875F, 0.8125F);
        setStepSound(soundTypeWood);
    }

    @Override
    public Item getItemDropped(int par1, Random random, int par2)
    {
        return Item.getItemFromBlock(this);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack)
    {
        world.setBlockMetadataWithNotify(x, y, z, itemStack.getItemDamage(), 1);
    }

    @Override
    public int damageDropped(int meta)
    {
        return meta;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        for(int i = 0; i < WoodVariant.count; i++)
            list.add(new ItemStack(item, 1, i));
    }

    @Override
    public void onBasicRightClick(int x, int y, int z, EntityPlayer player, World world, ItemStack itemStack)
    {
        TileTotemPole tileTotemSocket = (TileTotemPole) world.getTileEntity(x, y, z);
        if(tileTotemSocket.getTotemId() != 0)
        {
            player.addChatComponentMessage(new ChatComponentTranslation("totemicmisc.activeEffect", TotemRegistry.fromId(tileTotemSocket.getTotemId()).getLocalizedName()));
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        WoodVariant wood = WoodVariant.values()[meta];
        return wood.log.getIcon(side, wood.logMeta);
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

    @Override
    public int quantityDropped(Random rand)
    {
        return 0;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileTotemPole();
    }
}
