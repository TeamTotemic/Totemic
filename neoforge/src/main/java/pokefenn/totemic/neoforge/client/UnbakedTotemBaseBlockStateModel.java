package pokefenn.totemic.neoforge.client;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;

import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.SimpleModelWrapper;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.model.block.CustomUnbakedBlockStateModel;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.totem.TotemWoodType;
import pokefenn.totemic.init.ModContent;

public record UnbakedTotemBaseBlockStateModel(Variant.SimpleModelState modelState) implements CustomUnbakedBlockStateModel {
    public static final MapCodec<UnbakedTotemBaseBlockStateModel> CODEC = Variant.SimpleModelState.MAP_CODEC.xmap(UnbakedTotemBaseBlockStateModel::new, UnbakedTotemBaseBlockStateModel::modelState);

    @Override
    public BlockStateModel bake(ModelBaker modelBakery) {
        final var woodTypeRegistry = TotemicAPI.get().registry().woodTypes();

        var state = modelState.asModelState();
        var builder = ImmutableMap.<TotemWoodType, BlockStateModelPart>builderWithExpectedSize(woodTypeRegistry.size());
        for(var woodType: woodTypeRegistry) {
            var bakedPart = SimpleModelWrapper.bake(modelBakery, getWoodTypeModelLocation(woodType), state);
            builder.put(woodType, bakedPart);
        }

        return new DataDependentBlockStateModel<>(builder.buildOrThrow(), TotemPoleModelData.WOOD_TYPE_PROPERTY, ModContent.oak.get());
    }

    @Override
    public void resolveDependencies(Resolver resolver) {
        for(var woodType: TotemicAPI.get().registry().woodTypes()) {
            resolver.markDependency(getWoodTypeModelLocation(woodType));
        }
    }

    private static Identifier getWoodTypeModelLocation(TotemWoodType woodType) {
        var woodName = woodType.getRegistryName();
        return woodName.withPath("block/" + woodName.getPath() + "_totem_base");
    }

    @Override
    public MapCodec<? extends CustomUnbakedBlockStateModel> codec() {
        return CODEC;
    }
}
