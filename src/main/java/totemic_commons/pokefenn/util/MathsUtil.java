package totemic_commons.pokefenn.util;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class MathsUtil
{

    //Method from Electrodynamics
    public static float lerp(float start, float end, float value)
    {
        return (start + (value * (end - start)));
    }

    //Method from Electrodynamics
    public static float sinerp(float start, float end, float value)
    {
        return lerp(start, end, (float) Math.sin(value * Math.PI * 0.5F));
    }
}
