package totemic_commons.pokefenn.block

import rukalib_commons.pokefenn.block.BlockTile
import net.minecraft.block.material.Material
import net.minecraft.world.World
import net.minecraft.tileentity.TileEntity
import totemic_commons.pokefenn.lib.Strings
import totemic_commons.pokefenn.Totemic
import totemic_commons.pokefenn.tileentity.TileTotemTable

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 14/01/14
 * Time: 20:50

 */
class BlockTotemTable(id:Int) extends BlockTile(id, Material.wood) {

    setUnlocalizedName(Strings.TOTEM_TABLE_NAME)
    setHardness(1F)
    setCreativeTab(Totemic.tabsTotem)

    def createNewTileEntity(world: World): TileEntity ={

        new TileTotemTable
    }




}
