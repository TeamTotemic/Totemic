package pokefenn.totemic.client.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import pokefenn.totemic.api.totem.TotemWoodType;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.item.TotemPoleItem;

public final class BakedTotemBaseModel extends BakedModelWrapper<BakedModel> {
    public static final ModelProperty<TotemWoodType> WOOD_TYPE_PROPERTY = new ModelProperty<>();

    private final Map<TotemWoodType, BakedModel> bakedTotemModels;
    private final ItemOverrides itemOverrides;

    BakedTotemBaseModel(Map<TotemWoodType, BakedModel> bakedTotemModels) {
        super(Objects.requireNonNull(bakedTotemModels.get(ModContent.oak))); //default model
        this.bakedTotemModels = bakedTotemModels;
        this.itemOverrides = new ItemOverrides() {
            @Override
            public BakedModel resolve(BakedModel pModel, ItemStack pStack, ClientLevel pLevel, LivingEntity pEntity, int pSeed) {
                return bakedTotemModels.get(TotemPoleItem.getWoodType(pStack));
            }
        };
    }

    private BakedModel getModelFor(IModelData modelData) {
        var woodType = Objects.requireNonNullElse(modelData.getData(WOOD_TYPE_PROPERTY), ModContent.oak);
        return bakedTotemModels.get(woodType);
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull Random rand, @NotNull IModelData extraData) {
        return getModelFor(extraData).getQuads(state, side, rand, extraData);
    }

    @Override
    public TextureAtlasSprite getParticleIcon(@NotNull IModelData data) {
        return getModelFor(data).getParticleIcon(data);
    }

    @Override
    public ItemOverrides getOverrides() {
        return itemOverrides;
    }
}
