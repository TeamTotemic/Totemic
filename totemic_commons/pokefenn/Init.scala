package totemic_commons.pokefenn

import totemic_commons.pokefenn.block.ModBlocks
import totemic_commons.pokefenn.item.ModItems
import cpw.mods.fml.common.FMLCommonHandler
import totemic_commons.pokefenn.handler.LocalizationHandler
import totemic_commons.pokefenn.configuration.ConfigurationHandler
import java.io.File
import totemic_commons.pokefenn.lib.Reference
import totemic_commons.pokefenn.fluid.ModFluids
import cpw.mods.fml.common.network.NetworkRegistry
import totemic_commons.pokefenn.proxy.CommonProxy

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 18/12/13
 * Time: 15:51

 */
object PreInit {

  def init(){

  Totemic.logger.setParent(FMLCommonHandler.instance.getFMLLogger)

  Totemic.logger.info("Totemic is Loading")

  LocalizationHandler.loadLanguages

  Totemic.proxy.registerRenderers

  //Initiates fluids into the game
  ModFluids.init

  //Initiates totemic blocks into the game
  ModBlocks.init

  //Initiates the mod items into the game
  ModItems.init

  }

}


object Init {

  def init(){
    Totemic.logger.info("Totemic is entering its Initlisasion stage")

    NetworkRegistry.instance.registerGuiHandler(Totemic.instance, Totemic.proxy)

    CommonProxy.registerTileEntities()


  }


}


object PostInit{

  def init(){


  }



}