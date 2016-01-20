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

    /**
     * Checks whether arr1 is a prefix of arr2, i.e.
     * the elements of arr1 appear in order at the beginning of arr2 (compared using ==).
     */
    public static boolean isPrefix(Object[] arr1, Object[] arr2)
    {
        if(arr1.length > arr2.length)
            return false;

        for(int i = 0; i < arr1.length; i++)
        {
            if(arr1[i] != arr2[i])
                return false;
        }
        return true;
    }
}
