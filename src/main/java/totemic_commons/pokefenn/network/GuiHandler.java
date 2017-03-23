package totemic_commons.pokefenn.network;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import totemic_commons.pokefenn.client.gui.GuiLexicon;
import vazkii.botania.totemic_custom.api.lexicon.ILexicon;

public class GuiHandler implements IGuiHandler
{
    @Override
    @Nullable
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return null;
    }

    @Override
    @Nullable
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if(ID == 0)
        {
            GuiLexicon lex = GuiLexicon.currentOpenLexicon;
            ItemStack stack = player.getHeldItemMainhand();
            if(!(stack.getItem() instanceof ILexicon))
            {
                stack = player.getHeldItemOffhand();
                if(!(stack.getItem() instanceof ILexicon))
                    return null;
            }
            GuiLexicon.stackUsed = stack;
            return lex;
        }
        return null;
    }

}
