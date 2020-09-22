package pokefenn.totemic.api;

import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.material.MaterialColor;

public final class TotemWoodType { //TODO
    private static final List<TotemWoodType> woodTypes = ImmutableList.of(
        new TotemWoodType("oak", MaterialColor.WOOD, MaterialColor.OBSIDIAN),
        new TotemWoodType("spruce", MaterialColor.OBSIDIAN, MaterialColor.BROWN),
        new TotemWoodType("birch", MaterialColor.SAND, MaterialColor.QUARTZ),
        new TotemWoodType("jungle", MaterialColor.DIRT, MaterialColor.OBSIDIAN),
        new TotemWoodType("acacia", MaterialColor.ADOBE, MaterialColor.STONE),
        new TotemWoodType("dark_oak", MaterialColor.BROWN, MaterialColor.BROWN),
        new TotemWoodType("cedar", MaterialColor.PINK, MaterialColor.ADOBE)
    );

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
