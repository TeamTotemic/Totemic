package pokefenn.totemic.client.renderer.item.properties;

import org.jspecify.annotations.Nullable;

import com.mojang.serialization.MapCodec;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import pokefenn.totemic.item.MedicineBagItem;

public record IsMedicineBagOpen() implements ConditionalItemModelProperty {
    public static final MapCodec<IsMedicineBagOpen> MAP_CODEC = MapCodec.unit(new IsMedicineBagOpen());

    @Override
    public boolean get(ItemStack itemStack, @Nullable ClientLevel level, @Nullable LivingEntity owner, int seed, ItemDisplayContext displayContext) {
        return MedicineBagItem.isOpen(itemStack);
    }

    @Override
    public MapCodec<? extends ConditionalItemModelProperty> type() {
        return MAP_CODEC;
    }

}
