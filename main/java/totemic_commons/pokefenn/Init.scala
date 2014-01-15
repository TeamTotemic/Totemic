package totemic_commons.pokefenn

import cpw.mods.fml.common.FMLCommonHandler
import totemic_commons.pokefenn.fluid.ModFluids
import cpw.mods.fml.common.network.NetworkRegistry
import totemic_commons.pokefenn.recipe.{TotemicRecipes, ChlorophyllSolidifierRecipes}
import totemic_commons.pokefenn.util.OreDictionaryTotemic
import totemic_commons.pokefenn._

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 18/12/13
 * Time: 15:51

 */
object PreInit {

    def init() {

        //Creates the logger thingy :p
        Totemic.logger.setParent(FMLCommonHandler.instance.getFMLLogger)

        Totemic.logger.info("Totemic is Loading")

        //Initiates fluids into the game
        ModFluids.init

        //Initiates totemic blocks into the game
        ModBlocks.init

        //Initiates the mod items into the game
        ModItems.init

    }

}

object Init {

    def init() {

        Totemic.logger.info("Totemic is entering its Initlisation stage")

        //Starts ore dictionary code
        OreDictionaryTotemic.init()

        //Vannila recipes
        TotemicRecipes.init()

        //Gui handler code
        NetworkRegistry.instance.registerGuiHandler(Totemic.instance, Totemic.proxy)

        //Init tile entities into the game
        Totemic.proxy.registerTileEntities()

        ClientProxy.initTERendering()

        ChlorophyllSolidifierRecipes.addRecipes()

    }


}


object PostInit {

    def init() {

    }


}