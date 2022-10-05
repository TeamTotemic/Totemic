package pokefenn.totemic.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import pokefenn.totemic.init.ModTileEntities;
import pokefenn.totemic.tile.totem.StateStartup;
import pokefenn.totemic.tile.totem.TileTotemBase;

public class CeremonyCheatItem extends Item {
    public CeremonyCheatItem(Properties props) {
        super(props);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return context.getLevel().getBlockEntity(context.getClickedPos(), ModTileEntities.totem_base.get())
                .map(TileTotemBase::getTotemState)
                .filter(state -> state instanceof StateStartup)
                .map(state -> {
                    ((StateStartup) state).startCeremony();
                    return InteractionResult.SUCCESS;
                })
                .orElse(InteractionResult.FAIL);
    }
}