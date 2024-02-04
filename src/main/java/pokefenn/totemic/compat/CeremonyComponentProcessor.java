package pokefenn.totemic.compat;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.ceremony.Ceremony;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

public class CeremonyComponentProcessor implements IComponentProcessor {
    private Ceremony ceremony;

    @Override
    public void setup(Level level, IVariableProvider variables) {
        var ceremonyId = variables.get("ceremony").asString();
        ceremony = TotemicAPI.get().registry().ceremonies().getValue(new ResourceLocation(ceremonyId));
        if(ceremony == null)
            throw new IllegalArgumentException("Invalid Ceremony: '" + ceremonyId + "'");
    }

    @Override
    public IVariable process(Level level, String key) {
        return switch(key) {
            case "selector0" -> IVariable.from(getSelectorItem(0));
            case "selector1" -> IVariable.from(getSelectorItem(1));
            default -> null;
        };
    }

    private ItemStack getSelectorItem(int index) {
        return ceremony.getSelectors().get(index).getItem();
    }
}
