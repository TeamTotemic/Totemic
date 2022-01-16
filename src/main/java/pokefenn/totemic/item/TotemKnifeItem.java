package pokefenn.totemic.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import pokefenn.totemic.api.TotemWoodType;
import pokefenn.totemic.api.TotemicRegistries;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.init.ModBlocks;

public class TotemKnifeItem extends Item {
    public static final String KNIFE_TOTEM_KEY = "effect";

    public TotemKnifeItem(Properties props) {
        super(props);
    }

    @OnlyIn(Dist.CLIENT)
    private static MutableComponent getCarvingName(@Nullable TotemEffect effect) {
        if(effect != null)
            return effect.getDisplayName();
        else
            return new TranslatableComponent("block.totemic.totem_base");
    }

    @Nullable
    public static TotemEffect getCarvingEffect(ItemStack stack) {
        if(stack.hasTag()) {
            String key = stack.getTag().getString(KNIFE_TOTEM_KEY);
            if(!key.isEmpty())
                return TotemicRegistries.totemEffects().getValue(new ResourceLocation(key));
            else
                return null;
        }
        else
            return null;
    }

    @SuppressWarnings("resource")
    @Override
    public InteractionResult useOn(UseOnContext c) {
        if(c.getPlayer().isCrouching()) {
            //TODO
            return InteractionResult.FAIL;
        }
        else {
            BlockState state = c.getLevel().getBlockState(c.getClickedPos());

            TotemWoodType woodType = TotemWoodType.fromLog(state);
            if(woodType == null) {
                //Fall back to oak if it is an unrecognized log type
                if(BlockTags.LOGS_THAT_BURN.contains(state.getBlock()))
                    woodType = TotemWoodType.OAK;
                else
                    return InteractionResult.FAIL;
            }

            BlockState newState;
            TotemEffect effect = getCarvingEffect(c.getItemInHand());
            if(effect != null) {
                newState = ModBlocks.getTotemPoles().get(woodType, effect).getStateForPlacement(new BlockPlaceContext(c));
            }
            else {
                newState = ModBlocks.getTotemBases().get(woodType).getStateForPlacement(new BlockPlaceContext(c));
            }

            c.getLevel().setBlock(c.getClickedPos(), newState, 3);
            newState.getBlock().setPlacedBy(c.getLevel(), c.getClickedPos(), newState, c.getPlayer(), c.getItemInHand());
            c.getItemInHand().hurtAndBreak(1, c.getPlayer(), player -> player.broadcastBreakEvent(c.getHand()));
            c.getLevel().playSound(c.getPlayer(), c.getClickedPos(), SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);

            return InteractionResult.sidedSuccess(c.getLevel().isClientSide);
        }
    }

    @Override
    public Component getName(ItemStack stack) {
        return new TranslatableComponent(getDescriptionId(stack), getCarvingName(getCarvingEffect(stack)));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(new TranslatableComponent(getDescriptionId() + ".tooltip"));
    }
}
