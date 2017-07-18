package pokefenn.totemic.apiimpl;

import static pokefenn.totemic.Totemic.logger;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicRegistry;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.lexicon.LexiconCategory;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.util.MiscUtil;

public class RegistryImpl implements TotemicRegistry
{
    private static class Lazy
    {
        static final IForgeRegistry<MusicInstrument> INSTRUMENTS = GameRegistry.findRegistry(MusicInstrument.class);
        static final IForgeRegistry<TotemEffect> TOTEM_EFFECTS = GameRegistry.findRegistry(TotemEffect.class);
        static final IForgeRegistry<Ceremony> CEREMONIES = GameRegistry.findRegistry(Ceremony.class);
    }

    private static String convertName(String name)
    {
        int index = name.lastIndexOf(':');
        String prefix = (index >= 0) ? name.substring(0, index) : "";
        String bareName = name.substring(index + 1);
        String snakeCasedName = MiscUtil.camelToSnakeCase(bareName);
        return (index >= 0) ? (prefix.toLowerCase(Locale.ROOT) + ':' + snakeCasedName) : snakeCasedName;
    }

    @Override
    public TotemEffect addTotem(TotemEffect effect)
    {
        if(effect.getRegistryName() == null)
        {
            @SuppressWarnings("deprecation")
            String name = effect.getName();
            if(!name.equals(name.toLowerCase(Locale.ROOT)))
            {
                String oldName = name;
                name = convertName(oldName);
                logger.warn("The Totem Effect name is not lowercase, converting from camel to snake case: '{}' -> '{}'", oldName, name);
            }
            effect.setRegistryName(name);
        }

        Lazy.TOTEM_EFFECTS.register(effect);
        return effect;
    }

    @Override
    @Nullable
    public TotemEffect getTotem(String name)
    {
        return Lazy.TOTEM_EFFECTS.getValue(new ResourceLocation(name));
    }

    @Override
    public Map<String, TotemEffect> getTotems()
    {
        //FIXME
        throw new UnsupportedOperationException();
        //return Collections.unmodifiableMap(totemEffects);
    }

    public List<String> getTotemList()
    {
        throw new UnsupportedOperationException();
        //return Collections.unmodifiableList(totemList);
    }

    @Override
    public MusicInstrument addInstrument(MusicInstrument instrument)
    {
        if(instrument.getRegistryName() == null)
        {
            @SuppressWarnings("deprecation")
            String name = instrument.getName();
            if(!name.equals(name.toLowerCase(Locale.ROOT)))
            {
                String oldName = name;
                name = convertName(oldName);
                logger.warn("The Music Instrument name is not lowercase, converting from camel to snake case: '{}' -> '{}'", oldName, name);
            }
            instrument.setRegistryName(name);
        }

        Lazy.INSTRUMENTS.register(instrument);
        return instrument;
    }

    @Override
    @Nullable
    public MusicInstrument getInstrument(String name)
    {
        return Lazy.INSTRUMENTS.getValue(new ResourceLocation(name));
    }

    @Override
    public Map<String, MusicInstrument> getInstruments()
    {
        throw new UnsupportedOperationException();
        //return Collections.unmodifiableMap(instruments);
    }

    @Override
    public Ceremony addCeremony(Ceremony ceremony)
    {
        /*if(ceremonies.containsKey(ceremony.getName()))
            throw new IllegalArgumentException("Duplicate Ceremony entry for ID " + ceremony.getName());
        //Search for ambiguous selectors
        //The selectors for ceremonies have to be prefix-free in order to ensure
        //that every ceremony can actually be selected
        for(Ceremony other: ceremonies.values())
        {
            if(MathUtil.isPrefix(ceremony.getSelectors(), other.getSelectors()))
                throw new IllegalArgumentException(String.format(
                    "Could not add Ceremony %1$s because its selectors are prefixing the selectors of %2$s. This would make selecting %2$s impossible.\n%3$s prefixes %4$s",
                    ceremony.getName(), other.getName(), ceremony.getSelectors(), other.getSelectors()));

            if(MathUtil.isPrefix(other.getSelectors(), ceremony.getSelectors()))
                throw new IllegalArgumentException(String.format(
                    "Could not add Ceremony %1$s because its selectors are prefixed by the selectors of %2$s. This would make selecting %1$s impossible.\n%3$s is prefixed by %4$s",
                    ceremony.getName(), other.getName(), ceremony.getSelectors(), other.getSelectors()));
        }

        ceremonies.put(ceremony.getName(), ceremony);*/
        if(ceremony.getRegistryName() == null)
        {
            @SuppressWarnings("deprecation")
            String name = ceremony.getName();
            if(!name.equals(name.toLowerCase(Locale.ROOT)))
            {
                String oldName = name;
                name = convertName(oldName);
                logger.warn("The Ceremony name is not lowercase, converting from camel to snake case: '{}' -> '{}'", oldName, name);
            }
            ceremony.setRegistryName(name);
        }

        Lazy.CEREMONIES.register(ceremony);
        return ceremony;
    }

    @Override
    @Nullable
    public Ceremony getCeremony(String name)
    {
        return Lazy.CEREMONIES.getValue(new ResourceLocation(name));
    }

    @Override
    public Map<String, Ceremony> getCeremonies()
    {
        throw new UnsupportedOperationException();
        //return Collections.unmodifiableMap(ceremonies);
    }

    @Deprecated
    @Override
    public LexiconCategory addCategory(LexiconCategory cat)
    {
        return Totemic.api.lexicon().addCategory(cat);
    }

    @Deprecated
    @Override
    public List<LexiconCategory> getCategories()
    {
        return Totemic.api.lexicon().getCategories();
    }
}
