package pokefenn.totemic.util;

import java.util.Iterator;
import java.util.List;

public class MathsUtil
{
    public static float lerp(float start, float end, float value)
    {
        return (start + (value * (end - start)));
    }

    public static float sinerp(float start, float end, float value)
    {
        return lerp(start, end, (float) Math.sin(value * Math.PI * 0.5F));
    }

    /**
     * Checks whether list1 is a prefix of list2, i.e.
     * the elements of list1 appear in order at the beginning of list2 (compared using ==).
     */
    public static boolean isPrefix(List<?> list1, List<?> list2)
    {
        if(list1.size() > list2.size())
            return false;

        Iterator<?> it1 = list1.iterator();
        Iterator<?> it2 = list2.iterator();
        while(it1.hasNext())
        {
            if(it1.next() != it2.next())
                return false;
        }

        return true;
    }
}
