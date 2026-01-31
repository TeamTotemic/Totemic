package pokefenn.totemic.init;

import java.util.function.Supplier;

import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

public class BoatTypeEnumParams {
    public static final EnumProxy<Boat.Type> TOTEMIC_CEDAR_ENUM_PROXY = new EnumProxy<>(Boat.Type.class,
            ModBlocks.cedar_planks,
            "totemic:cedar",
            ModItems.cedar_boat,
            ModItems.cedar_chest_boat,
            (Supplier<Item>) () -> Items.STICK,
            false);
}
