package pokefenn.totemic.ceremony;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.init.ModItems;

public class FluteInfusionCeremony implements CeremonyInstance {
    @Override
    public void effect(Level level, BlockPos pos, CeremonyEffectContext context) {
        TotemicEntityUtil.getEntitiesInRange(ItemEntity.class, level, pos, 5, 5, e -> e.getItem().getItem() == ModItems.flute.get())
        .forEach(e -> {
            e.setItem(new ItemStack(ModItems.infused_flute.get()));
        });
        TotemicEntityUtil.getPlayersInRange(level, pos, 5, 5)
        .forEach(player -> {
            var inv = player.getInventory();
            for(int i = 0; i < inv.getContainerSize(); i++) {
                if(inv.getItem(i).getItem() == ModItems.flute.get())
                    inv.setItem(i, new ItemStack(ModItems.infused_flute.get()));
            }
            inv.setChanged();
        });
    }
}
