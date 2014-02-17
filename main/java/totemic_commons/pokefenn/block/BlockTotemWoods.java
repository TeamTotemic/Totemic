package totemic_commons.pokefenn.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.Textures;

import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 09/12/13
 * Time: 14:35
 */
public class BlockTotemWoods extends BlockLog
{


    public BlockTotemWoods(int id)
    {

        super(id);
        setUnlocalizedName(Strings.TOTEM_WOODS_NAME);
        setHardness(1F);
        setCreativeTab(Totemic.tabsTotem);

    }


    @SideOnly(Side.CLIENT)
    private Icon topIcon;
    @SideOnly(Side.CLIENT)
    private Icon sideIcon;
    @SideOnly(Side.CLIENT)
    private Icon bottomIcon;

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister register)
    {
        topIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.TOTEM_TABLE_TOP);
        sideIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.TOTEM_TABLE_SIDE);
        bottomIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.TOTEM_TABLE_BOTTOM);

    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return ModBlocks.totemWoods.blockID;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIcon(int side, int meta)
    {
        switch (side)
        {
            case 0:
                return bottomIcon;
            case 1:
                return topIcon;
            default:
                return sideIcon;
        }

    }

    @Override
    public void getSubBlocks(int blockId, CreativeTabs creativeTab, List subTypes)
    {
        subTypes.add(new ItemStack(blockId, 1, 0));
    }



    /**
     * This will turn into a metadata wood block thingy :)
     */


}








