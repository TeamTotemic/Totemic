package pokefenn.totemic.ceremony;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.init.ModItems;

public enum FluteInfusionCeremony implements CeremonyInstance {
    INSTANCE;

    @Override
    public void effect(Level level, BlockPos pos, CeremonyEffectContext context) {
        final int range = 6;
        var aabb = TotemicEntityUtil.getAABBAround(pos, range);
        level.getEntities(EntityType.ITEM, aabb, e -> e.getItem().is(ModItems.flute.get()))
        .forEach(e -> {
            e.setItem(new ItemStack(ModItems.infused_flute.get()));
        });
        TotemicEntityUtil.getPlayersIn(level, aabb)
        .forEach(player -> {
            var inv = player.getInventory();
            for(int i = 0; i < inv.getContainerSize(); i++) {
                if(inv.getItem(i).getItem() == ModItems.flute.get()) {
                    inv.setItem(i, new ItemStack(ModItems.infused_flute.get()));
                    inv.setChanged();
                }
            }
        });
    }
}
