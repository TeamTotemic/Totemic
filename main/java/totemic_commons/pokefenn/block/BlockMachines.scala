package totemic_commons.pokefenn.block

import net.minecraft.block.material.Material
import net.minecraft.world.World
import net.minecraft.tileentity.TileEntity
import totemic_commons.pokefenn.tileentity.{TileTotemBase, TileTotemTable, TileTotemDraining, TileChlorophyllSolidifier}
import net.minecraft.block.Block

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 20/12/13
 * Time: 16:48

 */
class BlockMachines(id: Int) extends Block(id: Int, Material.rock) {

    val blockNames = Array("chlorophyllSolidifier", "totemDraining", "totemTable", "totemBase")

    override def createTileEntity(world: World, meta: Int): TileEntity = meta match
    {
        case 0 => new TileChlorophyllSolidifier
        case 1 => new TileTotemDraining
        case 2 => new TileTotemTable
        case 3 => new TileTotemBase
        case _ => null

    }

    /*
    @SideOnly(Side.CLIENT) override def getSubBlocks(par1: Int, par2CreativeTabs: CreativeTabs, par3List: List[_]) {

        val i = blockNames.length

        for (i <- 1 to 10)
        {
            //par3List.add(new ItemStack(par1, 1, 0))
            List(new ItemStack(par1, 1, i)) :+ blockNames.length

        }

    }
    */

}
