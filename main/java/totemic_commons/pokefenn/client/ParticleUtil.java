package totemic_commons.pokefenn.client;

import net.minecraft.world.World;
import totemic_commons.pokefenn.configuration.ConfigurationSettings;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 18/01/14
 * Time: 12:12
 */
public class ParticleUtil
{

    public static void spawnParticle(World world, String particleName, double x, double y, double z, double velX, double velY, double velZ)
    {
        if (ConfigurationSettings.ENABLE_PARTICLE_FX)
        {

            world.spawnParticle(particleName, x, y, z, velX, velY, velZ);

        }
    }


}
