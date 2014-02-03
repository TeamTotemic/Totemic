package totemic_commons.pokefenn.util;

import cpw.mods.fml.common.Loader;
import totemic_commons.pokefenn.Totemic;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 01/12/13
 * Time: 16:09
 */
public class CompatInit
{

    public static void init()
    {

        if (Loader.isModLoaded("Thaumcraft"))
        {

            Totemic.logger.info("Is that thaumcraft I see?");
            Totemic.logger.info("This is more like Tech! ");

        }

        if (Loader.isModLoaded("AWWayofTime"))
        {

            Totemic.logger.info("Oh, is this that Blood Magic I spy with my bloody eye?");
            Totemic.logger.info("I'll show you real sacrifice!");

        }

        if (Loader.isModLoaded("Ars Magica 2"))
        {

            Totemic.logger.info("Ars Magica?");
            Totemic.logger.info("Stop doing cheap mobs and talk to me again ;)");

        }

        if (Loader.isModLoaded("Witchery"))
        {

            Totemic.logger.info("Oh, so this is that witch craft mod?");
            Totemic.logger.info("Try using your VooDoo on me and see if it makes a difference.");

        }

        if (Loader.isModLoaded("gregtech_addon"))
        {

            Totemic.logger.info("Gregtech is unsupported by Totemic and all of my mods.");
            Totemic.logger.info("If there are any problems, go cry to greg instead of me =3");

        }

        if (Loader.isModLoaded("Optifine"))
        {

            Totemic.logger.info("Optifine is unsupported by Totemic and all of my mods.");
            Totemic.logger.info("It causes alot of problems, if there are any, try removing optifine.");

        }

        if (Loader.isModLoaded("Botania"))
        {

            Totemic.logger.info("Oh, hey Botania!");
            Totemic.logger.info("Why can't we be friends... why can't we be friends... why can't we be friends! *runs away sobbing*");

        }

    }

}
