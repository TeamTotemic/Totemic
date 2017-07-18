package pokefenn.totemic.util;

public class MathUtil
{
    public static float lerp(float start, float end, float value)
    {
        return (start + (value * (end - start)));
    }

    public static float sinerp(float start, float end, float value)
    {
        return lerp(start, end, (float) Math.sin(value * Math.PI * 0.5F));
    }
}
