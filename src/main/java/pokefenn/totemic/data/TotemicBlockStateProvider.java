package pokefenn.totemic.data;

import net.minecraft.client.renderer.block.model.BlockModel.GuiLight;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import pokefenn.totemic.api.TotemWoodType;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.block.totem.TotemBaseBlock;
import pokefenn.totemic.block.totem.TotemPoleBlock;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModItems;

public class TotemicBlockStateProvider extends BlockStateProvider {
    public TotemicBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, TotemicAPI.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //Blocks
        logBlock(ModBlocks.cedar_log.get());
        logBlock(ModBlocks.stripped_cedar_log.get());
        axisBlock(ModBlocks.cedar_wood.get(), blockTexture(ModBlocks.cedar_log.get()), blockTexture(ModBlocks.cedar_log.get()));
        axisBlock(ModBlocks.stripped_cedar_wood.get(), blockTexture(ModBlocks.stripped_cedar_log.get()), blockTexture(ModBlocks.stripped_cedar_log.get()));
        simpleBlock(ModBlocks.cedar_leaves.get(), models().withExistingParent("totemic:cedar_leaves", "block/leaves").texture("all", "totemic:block/cedar_leaves"));
        models().withExistingParent("totemic:cedar_leaves_opaque", "block/leaves").texture("all", "totemic:block/cedar_leaves_opaque");
        simpleBlock(ModBlocks.cedar_sapling.get(), models().withExistingParent(ModBlocks.cedar_sapling.getId().toString(), "block/cross").texture("cross", blockTexture(ModBlocks.cedar_sapling.get())).renderType("cutout"));
        simpleBlock(ModBlocks.drum.get(), models().getExistingFile(modLoc("drum")));
        simpleBlock(ModBlocks.wind_chime.get(), blockEntityRenderer(ModBlocks.wind_chime, mcLoc("block/white_terracotta")));

        //Item Blocks
        for(var blockO: ModBlocks.REGISTER.getEntries())
            existingBlockItem(blockO);

        //Items
        var im = itemModels();
        im.basicItem(ModItems.flute.get());
        im.basicItem(ModItems.infused_flute.get());
        im.basicItem(ModItems.jingle_dress.get());
        im.basicItem(ModItems.rattle.get());
        im.basicItem(ModItems.eagle_bone_whistle.get());
        im.basicItem(ModItems.totem_whittling_knife.get());
        im.basicItem(ModItems.totemic_staff.get());
        im.basicItem(ModItems.ceremony_cheat.get());
        im.withExistingParent(ModItems.buffalo_spawn_egg.getId().toString(), "item/template_spawn_egg");
        im.basicItem(ModItems.buffalo_meat.get());
        im.basicItem(ModItems.cooked_buffalo_meat.get());
        im.basicItem(ModItems.buffalo_tooth.get());
        im.basicItem(ModItems.buffalo_hide.get());
        im.basicItem(ModItems.iron_bells.get());
        im.basicItem(ModItems.eagle_bone.get());
        im.basicItem(ModItems.eagle_feather.get());

        registerTotemModels();
    }

    private void registerTotemModels() {
        //TODO: It would be nice if those models and block states could be loaded dynamically rather than generated
        ModelFile totemBaseModel = models().getExistingFile(new ResourceLocation(TotemicAPI.MOD_ID, ModelProvider.BLOCK_FOLDER + "/totem_base"));
        for(var blockO: ModBlocks.getTotemBases().values()) {
            ResourceLocation blockName = blockO.getId();
            TotemBaseBlock block = blockO.get();

            //Block model
            BlockModelBuilder blockModel = models().getBuilder(blockName.toString()).parent(totemBaseModel);
            setTotemTextures(blockModel, block.woodType);

            //Block state
            waterloggedHorizontalBlock(block, blockModel);
            //Item model
            simpleBlockItem(block, blockModel);
        }
        for(var blockO: ModBlocks.getTotemPoles().values()) {
            ResourceLocation blockName = blockO.getId();
            TotemPoleBlock block = blockO.get();
            ResourceLocation effectName = block.effect.getRegistryName();

            ModelFile parentModel = models().getExistingFile(new ResourceLocation(effectName.getNamespace(), ModelProvider.BLOCK_FOLDER + "/totem_pole_" + effectName.getPath()));

            //Block model
            BlockModelBuilder blockModel = models().getBuilder(blockName.toString()).parent(parentModel);
            setTotemTextures(blockModel, block.woodType);

            //Block state
            waterloggedHorizontalBlock(block, blockModel);
            //Item model
            simpleBlockItem(block, blockModel);
        }
    }

    private BlockModelBuilder setTotemTextures(BlockModelBuilder model, TotemWoodType woodType) {
        return model
                .texture("wood", woodType.getWoodTexture())
                .texture("bark", woodType.getBarkTexture())
                .texture("top", woodType.getTopTexture())
                .texture("particle", woodType.getParticleTexture());
    }

    // The same as BlockStateProvider#horizontalBlock, but ignoring the Waterlogged property
    private void waterloggedHorizontalBlock(Block block, ModelFile model) {
        getVariantBuilder(block)
            .forAllStatesExcept(state -> ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
                    .build(),
            BlockStateProperties.WATERLOGGED);
    }

    private void existingBlockItem(RegistryObject<? extends Block> block) {
        simpleBlockItem(block.get(), models().getExistingFile(block.getId()));
    }

    private ModelFile blockEntityRenderer(RegistryObject<? extends Block> block, ResourceLocation particleTexture) {
        return models().getBuilder(block.getId().toString())
                .parent(new ModelFile.UncheckedModelFile("builtin/entity"))
                .texture("particle", particleTexture)
                .guiLight(GuiLight.SIDE)
                .transforms() //Values copied from models/block/block.json, as we cannot use block/block as the parent
                .transform(TransformType.GUI)
                    .rotation(30, 225, 0)
                    .translation(0, 0, 0)
                    .scale(0.625F)
                    .end()
                .transform(TransformType.GROUND)
                    .rotation(0, 0, 0)
                    .translation(0, 3, 0)
                    .scale(0.25F)
                    .end()
                .transform(TransformType.FIXED)
                    .rotation(0, 0, 0)
                    .translation(0, 0, 0)
                    .scale(0.5F)
                    .end()
                .transform(TransformType.THIRD_PERSON_RIGHT_HAND)
                    .rotation(75, 45, 0)
                    .translation(0, 2.5F, 0)
                    .scale(0.375F)
                    .end()
                .transform(TransformType.FIRST_PERSON_RIGHT_HAND)
                    .rotation(0, 45, 0)
                    .translation(0, 0, 0)
                    .scale(0.4F)
                    .end()
                .transform(TransformType.FIRST_PERSON_LEFT_HAND)
                    .rotation(0, 225, 0)
                    .translation(0, 0, 0)
                    .scale(0.4F)
                    .end()
                .end();
    }
}
