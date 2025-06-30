package pokefenn.totemic;

import java.util.List;

import it.unimi.dsi.fastutil.ints.IntBooleanPair;
import it.unimi.dsi.fastutil.objects.ObjectBooleanPair;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.MinecraftForge;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.api.ceremony.StartupContext;
import pokefenn.totemic.api.event.CeremonyEvent;
import pokefenn.totemic.api.event.MedicineBagEffectEvent;
import pokefenn.totemic.api.event.TotemEffectEvent;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.totem.MedicineBagEffect;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.api.totem.TotemEffectContext;

public class TotemicEventHooks {
    //Make the hook methods in this class instance rather static methods, to facilitate future abstraction from NeoForge's event system.
    //For the same reason, we return pairs rather than event instances from methods.
    private static final TotemicEventHooks INSTANCE = new TotemicEventHooks();

    public static TotemicEventHooks get() {
        return INSTANCE;
    }

    //Ceremony Events
    /**
     * @return a Pair of the Ceremony to be selected and a boolean describing whether the call to {@link CeremonyInstance#canSelect} should be skipped.
     */
    public ObjectBooleanPair<Ceremony> fireCeremonySelection(LevelAccessor level, BlockPos pos, List<MusicInstrument> selectors, Ceremony ceremony) {
        var event = new CeremonyEvent.Selection(level, pos, selectors, ceremony);
        MinecraftForge.EVENT_BUS.post(event);
        return ObjectBooleanPair.of(event.getCeremony(), event.getSkipSelectionCheck());
    }

    public boolean fireCeremonyStartupTick(LevelAccessor level, BlockPos pos, Ceremony ceremony, CeremonyInstance instance, StartupContext context) {
        return !MinecraftForge.EVENT_BUS.post(new CeremonyEvent.StartupTick(level, pos, ceremony, instance, context));
    }

    public void fireCeremonyStartupFail(LevelAccessor level, BlockPos pos, Ceremony ceremony, CeremonyInstance instance, StartupContext context) {
        MinecraftForge.EVENT_BUS.post(new CeremonyEvent.StartupFail(level, pos, ceremony, instance, context));
    }

    public boolean fireCeremonyStartupSuccess(LevelAccessor level, BlockPos pos, Ceremony ceremony, CeremonyInstance instance, StartupContext context) {
        return !MinecraftForge.EVENT_BUS.post(new CeremonyEvent.StartupSuccess(level, pos, ceremony, instance, context));
    }

    public boolean fireCeremonyEffectTick(LevelAccessor level, BlockPos pos, Ceremony ceremony, CeremonyInstance instance, CeremonyEffectContext context) {
        return !MinecraftForge.EVENT_BUS.post(new CeremonyEvent.EffectTick(level, pos, ceremony, instance, context));
    }

    //Totem Effect Events
    public boolean fireTotemEffectEvent(LevelAccessor level, BlockPos pos, TotemEffect effect, int repetition, TotemEffectContext context) {
        return !MinecraftForge.EVENT_BUS.post(new TotemEffectEvent(level, pos, effect, repetition, context));
    }

    /**
     * @return a pair of the charge to deduct from the Medicine Bag and a boolean describing whether {@link MedicineBagEffect#medicineBagEffect} should be called.
     */
    public IntBooleanPair fireMedicineBagEffectEvent(MedicineBagEffect effect, TotemCarving carving, Player player, ItemStack medicineBag, int charge, int chargeToDeduct) {
        var event = new MedicineBagEffectEvent(effect, carving, player, medicineBag, charge, chargeToDeduct);
        boolean canceled = MinecraftForge.EVENT_BUS.post(event);
        return IntBooleanPair.of(event.getChargeToDeduct(), !canceled);
    }
}
