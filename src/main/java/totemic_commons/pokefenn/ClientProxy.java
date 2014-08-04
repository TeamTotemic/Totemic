package totemic_commons.pokefenn;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import totemic_commons.pokefenn.client.rendering.entity.BuffaloRendering;
import totemic_commons.pokefenn.client.rendering.item.*;
import totemic_commons.pokefenn.client.rendering.model.ModelBuffalo;
import totemic_commons.pokefenn.client.rendering.tileentity.*;
import totemic_commons.pokefenn.entity.animal.EntityBuffalo;
import totemic_commons.pokefenn.tileentity.TileTotemTorch;
import totemic_commons.pokefenn.tileentity.music.TileDrum;
import totemic_commons.pokefenn.tileentity.music.TileWindChime;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;
import totemic_commons.pokefenn.tileentity.totem.TileTotemPole;

public class ClientProxy extends CommonProxy
{
    @Override
    public void initRendering()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityBuffalo.class, new BuffaloRendering(new ModelBuffalo(), 0.5F));

        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.totemTorch), new ItemTotemTorchRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.windChime), new ItemWindChimeRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.totemBase), new ItemTotemBaseRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.totemPole), new ItemTotemPoleRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.drum), new ItemDrumRenderer());


        ClientRegistry.bindTileEntitySpecialRenderer(TileTotemPole.class, new TileTotemSocketCubeRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(TileDrum.class, new TileDrumRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileTotemTorch.class, new TileTotemTorchRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileTotemBase.class, new TileTotemBaseRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileWindChime.class, new TileWindChimeRenderer());
    }
}
