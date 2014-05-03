package totemic_commons.pokefenn.client.sound;

import net.minecraft.block.Block;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class SoundDrum extends Block.SoundType
{
    public SoundDrum(String name, String stepName, String placeName, float volume)
    {
        super("soundDrum", volume, 1F);
    }
}
