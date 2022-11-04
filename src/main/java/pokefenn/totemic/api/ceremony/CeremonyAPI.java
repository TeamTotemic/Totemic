package pokefenn.totemic.api.ceremony;

/**
 * Provides access to functionality commonly used for implementing ceremonies.
 * <p>
 * Use {@code TotemicAPI.get().ceremony()} to get an instance of this interface.
 */
public interface CeremonyAPI {
    /**
     * The minimum number of music instruments for selecting a ceremony.
     */
    static final int MIN_SELECTORS = 2;
    /**
     * The maximum number of music instruments for selecting a ceremony.
     */
    static final int MAX_SELECTORS = 2;


}
