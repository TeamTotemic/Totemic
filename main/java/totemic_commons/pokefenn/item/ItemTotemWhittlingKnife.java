package totemic_commons.pokefenn.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.block.BlockTotemWoods;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.util.EntityUtil;

public class ItemTotemWhittlingKnife extends ItemNormal
{

    private static final String[] ITEMS_NAMES = new String[]{"toolHandle", "whittlingHead", "whittlingWhole"/*, "choppingHead", "choppingWhole", "paintHead", "paintWhite", "paintBlack", "generalHead", "generalWhole"*/};

    @SideOnly(Side.CLIENT)
    private Icon[] icons;

    public ItemTotemWhittlingKnife(int id)
    {
        super(id);
        setMaxStackSize(1);
        setCreativeTab(Totemic.tabsTotem);
        setUnlocalizedName(Strings.TOTEM_WHITTLING_KNIFE_NAME);
        setContainerItem(this);
        setMaxDamage(3);
    }

    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer player, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (!world.isRemote)
        {
            MovingObjectPosition block = EntityUtil.raytraceFromEntity(world, player, true, 5);

            if (block != null)
            {
                Block blockQuery = Block.blocksList[world.getBlockId(block.blockX, block.blockY, block.blockZ)];

                if (blockQuery != null)
                {
                    if(blockQuery instanceof BlockTotemWoods)
                    {
                        world.setBlock(block.blockX, block.blockY, block.blockZ, ModBlocks.totemSocket.blockID);
                        //player.getHeldItem().setItemDamage(player.getHeldItem().getItemDamage() + 1);
                        player.getHeldItem().damageItem(1, player);
                    }

                }
            }
            //if(player.get)
        }

        return true;
    }

    /*

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {

        StringBuilder unlocalizedName = new StringBuilder();
        int meta = MathHelper.clamp_int(itemStack.getItemDamage(), 0, ITEMS_NAMES.length - 1);

        unlocalizedName.append("item.");
        unlocalizedName.append(Strings.RESOURCE_PREFIX);
        unlocalizedName.append(ITEMS_NAMES[meta]);

        return unlocalizedName.toString();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int meta)
    {

        int j = MathHelper.clamp_int(meta, 0, ITEMS_NAMES.length - 1);
        return icons[j];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {

        icons = new Icon[ITEMS_NAMES.length];

        for (int i = 0; i < ITEMS_NAMES.length; ++i)
        {
            icons[i] = iconRegister.registerIcon(Strings.RESOURCE_PREFIX + ITEMS_NAMES[i]);
        }
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(int id, CreativeTabs creativeTab, List list)
    {

        for (int meta = 0; meta < ITEMS_NAMES.length; ++meta)
        {
            list.add(new ItemStack(id, 1, meta));
        }

    }
    */


}


