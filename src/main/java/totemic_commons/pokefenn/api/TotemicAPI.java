package totemic_commons.pokefenn.api;

/**
 * This class provides access to Totemic's registries
 */
public final class TotemicAPI
{
    private static API instance;

    /**
     * Returns an instance of the Totemic API.
     *
     * Note that the API will be instantiated by Totemic during the preinitalization phase,
     * so it is not safe to call this method until the initalization phase.
     */
    public static API get()
    {
        if(instance == null)
            throw new IllegalStateException("The Totemic API has not been initalized yet, or Totemic is not installed");
        return instance;
    }

    public interface API
    {

    }
}
