package totemic_commons.pokefenn.block

import net.minecraft.block.material.Material
import net.minecraft.world.World
import net.minecraft.tileentity.TileEntity
import totemic_commons.pokefenn.tileentity.TileChlorophyllSolidifier
import net.minecraft.block.Block

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 20/12/13
 * Time: 16:48

 */
class BlockMachines(id: Int) extends Block(id: Int, Material.rock) {

    val blockNames = Array("chlorophyllSolidifier", "test1")

    override def createTileEntity(world: World, meta: Int): TileEntity = meta match {
        case 0 => new TileChlorophyllSolidifier
        case _ => null
    }

    //This code does nothing currently, will depricate the other code later =3

    //override def hasTileEntity(meta:Int) = meta == 0 || meta == 1

    /*
    override def getSubBlocks(id:Int, tab:CreativeTabs, list:List){


    }

    override def getMetadata(meta: Int) {


    }
    */


}
