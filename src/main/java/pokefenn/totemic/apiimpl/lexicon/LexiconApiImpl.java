package pokefenn.totemic.apiimpl.lexicon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pokefenn.totemic.api.lexicon.LexiconAPI;
import pokefenn.totemic.api.lexicon.LexiconCategory;

public class LexiconApiImpl implements LexiconAPI
{
    private final List<LexiconCategory> categories = new ArrayList<>();

    @Override
    public LexiconCategory addCategory(LexiconCategory cat)
    {
        categories.add(cat);
        return cat;
    }

    @Override
    public List<LexiconCategory> getCategories()
    {
        return Collections.unmodifiableList(categories);
    }
}
