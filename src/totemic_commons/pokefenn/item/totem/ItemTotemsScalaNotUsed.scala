package totemic_commons.pokefenn.item.totem

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.client.renderer.texture.IconRegister
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import totemic_commons.pokefenn.item.ItemTotemic
import totemic_commons.pokefenn.lib.Strings
import totemic_commons.pokefenn.lib.Textures
import java.util.List
import net.minecraft.util.{Icon, MathHelper}

class ItemTotemsScalaNotUsed(id:Int) extends ItemTotemic(id:Int) {
/*
    this.setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.TOTEM_BAT_NAME)
    this.setHasSubtypes(true)

   var TOTEM_NAMES = Array[String]("Cactus", "Quartz Block", "Horse", "Squid")

  @SideOnly(Side.CLIENT) private val icons: Array[Icon] = null

  @SideOnly(Side.CLIENT) def registerIcons(iconRegister: IconRegister) {

    icons = new Array[Icon](TOTEM_NAMES.length)



  }

  @SideOnly(Side.CLIENT) override def addInformation(stack: ItemStack, player: EntityPlayer, list: List[_], par4: Boolean) {

  }



  @SideOnly(Side.CLIENT) override def getIconFromDamage(meta: Int):Icon=  {

    var i: Int = MathHelper.clamp_int(meta, 0, 4)
    return icons[i]


  }
  */

}