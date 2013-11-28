package totemic_commons.pokefenn.block

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IconRegister
import net.minecraft.util.Icon
import totemic_commons.pokefenn.Totemic
import totemic_commons.pokefenn.lib.Strings
import totemic_commons.pokefenn.lib.Textures

/**
 * Created with IntelliJ IDEA.
 * User: ${Pokefenn}
 * Date: 13/11/13
 * Time: 08:07
 */
class BlockBigBadTotemBase(id:Int) extends Block(id:Int, Material.wood) {

    this.setUnlocalizedName(Strings.BIG_BAD_TOTEM_BASE_NAME)
    this.setHardness(1F)
    this.setCreativeTab(Totemic.tabsTotem)


  @SideOnly(Side.CLIENT) override def registerIcons(register: IconRegister) {
    allSidesIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.BIG_BAD_TOTEM_BASE_ICON_ALL)
  }

  @SideOnly(Side.CLIENT) override def getIcon(side: Int, meta: Int): Icon = {
    return allSidesIcon
  }

  @SideOnly(Side.CLIENT) private var allSidesIcon: Icon = null
}