package totemic_commons.pokefenn.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import totemic_commons.pokefenn.client.gui.GuiLexicon;
import vazkii.botania.totemic_custom.api.lexicon.ILexicon;

public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if(ID == 0)
        {
            GuiLexicon lex = GuiLexicon.currentOpenLexicon;
            GuiLexicon.stackUsed = player.getHeldItemMainhand();
            if(GuiLexicon.stackUsed == null || !(GuiLexicon.stackUsed.getItem() instanceof ILexicon))
                return null;
            return lex;
        }
        return null;
    }

}

