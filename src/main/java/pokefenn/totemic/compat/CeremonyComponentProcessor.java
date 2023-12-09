package pokefenn.totemic.compat;

public class CeremonyComponentProcessor /*implements IComponentProcessor*/ {
    /*private Ceremony ceremony;

    @Override
    public void setup(IVariableProvider variables) {
        var ceremonyId = variables.get("ceremony").asString();
        ceremony = TotemicAPI.get().registry().ceremonies().getValue(new ResourceLocation(ceremonyId));
        if(ceremony == null)
            throw new IllegalArgumentException("Invalid Ceremony: '" + ceremonyId + "'");
    }

    @Override
    public IVariable process(String key) {
        return switch(key) {
            case "selector0" -> IVariable.from(getSelectorItem(0));
            case "selector1" -> IVariable.from(getSelectorItem(1));
            default -> null;
        };
    }

    private ItemStack getSelectorItem(int index) {
        return ceremony.getSelectors().get(index).getItem();
    }*/
}
