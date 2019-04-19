package pokefenn.totemic.api;

import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.material.MaterialColor;

public final class WoodType { //TODO
    private static final List<WoodType> woodTypes = ImmutableList.of(
        new WoodType("oak", MaterialColor.WOOD, MaterialColor.OBSIDIAN),
        new WoodType("spruce", MaterialColor.OBSIDIAN, MaterialColor.BROWN),
        new WoodType("birch", MaterialColor.SAND, MaterialColor.QUARTZ),
        new WoodType("jungle", MaterialColor.DIRT, MaterialColor.OBSIDIAN),
        new WoodType("acacia", MaterialColor.ADOBE, MaterialColor.STONE),
        new WoodType("dark_oak", MaterialColor.BROWN, MaterialColor.BROWN),
        new WoodType("cedar", MaterialColor.PINK, MaterialColor.ADOBE)
    );

    private final String name;
    private final MaterialColor woodColor;
    private final MaterialColor barkColor;

    public WoodType(String name, MaterialColor woodColor, MaterialColor barkColor) {
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

    public static List<WoodType> getWoodTypes() {
        return woodTypes;
    }
}
