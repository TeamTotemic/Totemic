package totemic_commons.pokefenn.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import rukalib_commons.pokefenn.block.BlockNormal;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.Textures;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 13/11/13
 * Time: 14:21
 */
public class BlockBigBadTotemHead extends BlockNormal {


    public BlockBigBadTotemHead(int id) {

        super(id, Material.wood);
        setUnlocalizedName(Strings.BIG_BAD_TOTEM_HEAD_NAME);
        setHardness(1F);
        setCreativeTab(Totemic.tabsTotem);

    }

    @SideOnly(Side.CLIENT)
    private Icon allSidesIcon;

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister register) {
        allSidesIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.BIG_BAD_TOTEM_HEAD_ICON_ALL);

    }

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIcon(int side, int meta) {
        return allSidesIcon;

    }


}
