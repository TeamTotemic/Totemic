package pokefenn.totemic.util;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class MiscUtil
{
    /**
     * Checks whether list1 is a prefix of list2, i.e.
     * the elements of list1 appear in order at the beginning of list2.
     */
    public static boolean isPrefix(List<?> list1, List<?> list2)
    {
        if(list1.size() > list2.size())
            return false;

        Iterator<?> it1 = list1.iterator();
        Iterator<?> it2 = list2.iterator();
        while(it1.hasNext())
        {
            if(!Objects.equals(it1.next(), it2.next()))
                return false;
        }

        return true;
    }

    public static String camelToSnakeCase(String str)
    {
        String[] components = StringUtils.splitByCharacterTypeCamelCase(str);
        return Arrays.stream(components)
                .map(s -> s.toLowerCase(Locale.ROOT))
                .collect(Collectors.joining("_"));
    }

    /**
     * If the mod ID prefix is missing from the name, adds Totemic's prefix.<br>
     * If the name is not lowercase, converts it from camel case to snake case.
     */
    public static String fixResourceName(String name)
    {
        int index = name.lastIndexOf(':');
        String prefix = (index >= 0) ? name.substring(0, index) : "totemic";
        String bareName = name.substring(index + 1);
        String snakeCasedName = camelToSnakeCase(bareName);
        return prefix.toLowerCase(Locale.ROOT) + ':' + snakeCasedName;
    }
}
