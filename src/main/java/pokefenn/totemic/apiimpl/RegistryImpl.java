package pokefenn.totemic.apiimpl;

import static pokefenn.totemic.Totemic.logger;

import java.util.*;

import javax.annotation.Nullable;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
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

    private static String checkAndFixName(String name, String description)
    {
        if(!name.contains(":"))
        {
            logger.warn("The {} name '{}' is missing the mod ID. Prefixing with 'totemic:'.", description, name);
            name = "totemic:" + name;
        }

        if(!name.equals(name.toLowerCase(Locale.ROOT)))
        {
            String oldName = name;
            name = MiscUtil.fixResourceName(oldName);
            logger.warn("The {} name is not lowercase, converting from camel to snake case: '{}' -> '{}'", description, oldName, name);
        }
        return name;
    }

    @Override
    public TotemEffect addTotem(TotemEffect effect)
    {
        if(effect.getRegistryName() == null)
        {
            @SuppressWarnings("deprecation")
            String name = effect.getName();
            effect.setRegistryName(checkAndFixName(name, "Totem Effect"));
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
        return wrapRegistry(Lazy.TOTEM_EFFECTS);
    }

    public List<String> getTotemList()
    {
        return Lists.transform(Lazy.TOTEM_EFFECTS.getValues(), t -> t.getRegistryName().toString());
    }

    @Override
    public MusicInstrument addInstrument(MusicInstrument instrument)
    {
        if(instrument.getRegistryName() == null)
        {
            @SuppressWarnings("deprecation")
            String name = instrument.getName();
            instrument.setRegistryName(checkAndFixName(name, "Music Instrument"));
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
        return wrapRegistry(Lazy.INSTRUMENTS);
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
            ceremony.setRegistryName(checkAndFixName(name, "Ceremony"));
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
        return wrapRegistry(Lazy.CEREMONIES);
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

    private static <T extends IForgeRegistryEntry<T>> Map<String, T> wrapRegistry(IForgeRegistry<T> registry)
    {
        Set<String> keys = new AbstractSet<String>()
        {
            @Override
            public Iterator<String> iterator()
            {
                return Iterators.transform(registry.getKeys().iterator(), ResourceLocation::toString);
            }

            @Override
            public int size()
            {
                return registry.getKeys().size();
            }

            @Override
            public boolean contains(Object o)
            {
                return registry.containsKey(new ResourceLocation((String) o));
            }

            @Override
            public boolean remove(Object o)
            {
                throw new UnsupportedOperationException();
            }
        };
        return Maps.asMap(keys, s -> registry.getValue(new ResourceLocation(s)));
    }
}
