package totemic_commons.pokefenn;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import totemic_commons.pokefenn.client.rendering.entity.BuffaloRendering;
import totemic_commons.pokefenn.client.rendering.entity.DartRendering;
import totemic_commons.pokefenn.client.rendering.model.ModelBuffalo;
import totemic_commons.pokefenn.client.rendering.tileentity.*;
import totemic_commons.pokefenn.entity.animal.EntityBuffalo;
import totemic_commons.pokefenn.entity.projectile.EntityBaseDart;
import totemic_commons.pokefenn.lib.RenderIds;
import totemic_commons.pokefenn.misc.villager.TotemicVillagerInitiation;
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
        Minecraft mc = Minecraft.getMinecraft();

        RenderIds.RENDER_ID_TOTEM_POLE = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.RENDER_ID_TOTEM_TORCH = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.RENDER_ID_TOTEM_SOCKET_CUBE = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.RENDER_ID_DRUM = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.RENDER_ID_TOTEM_BASE = RenderingRegistry.getNextAvailableRenderId();
        RenderIds.RENDER_ID_WIND_CHIME = RenderingRegistry.getNextAvailableRenderId();

        RenderingRegistry.registerEntityRenderingHandler(EntityBaseDart.class, new DartRendering());
        RenderingRegistry.registerEntityRenderingHandler(EntityBuffalo.class, new BuffaloRendering(new ModelBuffalo(), 0.5F));

        //MinecraftForgeClient.registerItemRenderer(ModBlocks.totemTorch, new ItemTotemTorchRenderer);

        ClientRegistry.bindTileEntitySpecialRenderer(TileTotemPole.class, new TileTotemSocketCubeRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(TileDrum.class, new TileDrumRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileTotemTorch.class, new TileTotemTorchRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileTotemBase.class, new TileTotemBaseRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileWindChime.class, new TileWindChimeRenderer());

        VillagerRegistry.instance().registerVillagerSkin(TotemicVillagerInitiation.TOTEMIST_VILLAGER_ID, new ResourceLocation("totemic", "textures/entity/totemistVillager.png"));
    }

}
