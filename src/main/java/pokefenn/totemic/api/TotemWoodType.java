package pokefenn.totemic.api;

import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.material.MaterialColor;

public final class TotemWoodType { // TODO
    private static final List<TotemWoodType> woodTypes = ImmutableList.of(
            new TotemWoodType("oak", MaterialColor.WOOD, MaterialColor.PODZOL),
            new TotemWoodType("spruce", MaterialColor.PODZOL, MaterialColor.COLOR_BROWN),
            new TotemWoodType("birch", MaterialColor.SAND, MaterialColor.QUARTZ),
            new TotemWoodType("jungle", MaterialColor.DIRT, MaterialColor.PODZOL),
            new TotemWoodType("acacia", MaterialColor.COLOR_ORANGE, MaterialColor.STONE),
            new TotemWoodType("dark_oak", MaterialColor.COLOR_BROWN, MaterialColor.COLOR_BROWN),
            new TotemWoodType("cedar", MaterialColor.COLOR_PINK, MaterialColor.COLOR_ORANGE));

    private final String name;
    private final MaterialColor woodColor;
    private final MaterialColor barkColor;

    public TotemWoodType(String name, MaterialColor woodColor, MaterialColor barkColor) {
        this.name = name;
        this.woodColor = woodColor;
        this.barkColor = barkColor;
    }

    public String getName() {
        return name;
    }

    public MaterialColor getWoodColor() {
        return woodColor;
    }

    public MaterialColor getBarkColor() {
        return barkColor;
    }

    public static List<TotemWoodType> getWoodTypes() {
        return woodTypes;
    }
}
