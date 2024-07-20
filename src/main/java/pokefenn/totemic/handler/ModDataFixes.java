package pokefenn.totemic.handler;

import com.mojang.serialization.Dynamic;

import net.minecraft.util.datafix.fixes.ItemStackComponentizationFix;

public final class ModDataFixes {
    public static void fixItemStackComponents(ItemStackComponentizationFix.ItemStackData stackData, Dynamic<?> tag) {
        if(stackData.is("totemic:jingle_dress")) {
            stackData.removeTag("Charge");
        }

        if(stackData.is("totemic:totem_whittling_knife")) {
            stackData.moveTagToComponent("Carving", "totemic:carving");
        }

        if(stackData.is("totemic:medicine_bag") || stackData.is("totemic:creative_medicine_bag")) {
            stackData.moveTagToComponent("Totem", "totemic:carving");
            stackData.moveTagToComponent("Open", "totemic:open");
            if(stackData.is("totemic:medicine_bag"))
                stackData.moveTagToComponent("Charge", "totemic:mb_charge");
        }

        if(stackData.is("totemic:totem_base")) {
            stackData.moveTagToComponent("Wood", "totemic:wood_type");
        }

        if(stackData.is("totemic:totem_pole")) {
            stackData.moveTagToComponent("Wood", "totemic:wood_type");
            stackData.moveTagToComponent("Carving", "totemic:carving");
        }
    }
}
