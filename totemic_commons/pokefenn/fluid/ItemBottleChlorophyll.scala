package totemic_commons.pokefenn.fluid

import net.minecraft.item.Item
import net.minecraftforge.fluids.ItemFluidContainer
import totemic_commons.pokefenn.Totemic
import totemic_commons.pokefenn.lib.Strings

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 16/12/13
 * Time: 22:24

 */
class ItemBottleChlorophyll(id: Int) extends ItemFluidContainer(id: Int) {


  setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.BOTTLE_CHLOROPHYLL_NAME)
  setMaxStackSize(64)
  setCreativeTab(Totemic.tabsTotem)



}
