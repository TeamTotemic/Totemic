/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Totemic Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 */
package totemic_commons.pokefenn.client;

public final class FontHelper
{
    public static boolean isFormatColor(char ch)
    {
        return ch >= '0' && ch <= '9' || ch >= 'a' && ch <= 'f' || ch >= 'A' && ch <= 'F';
    }

    public static boolean isFormatSpecial(char ch)
    {
        return ch >= 'k' && ch <= 'o' || ch >= 'K' && ch <= 'O' || ch == 'r' || ch == 'R';
    }

    public static String getFormatFromString(String str)
    {
        String s1 = "";
        int i = -1;
        int j = str.length();

        while((i = str.indexOf('§', i + 1)) != -1)
        {
            if(i < j - 1)
            {
                char c0 = str.charAt(i + 1);

                if(isFormatColor(c0))
                    s1 = "§" + c0;
                else if(isFormatSpecial(c0))
                    s1 = s1 + "§" + c0;
            }
        }

        return s1;
    }

}