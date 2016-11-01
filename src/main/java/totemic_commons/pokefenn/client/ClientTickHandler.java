package totemic_commons.pokefenn.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import totemic_commons.pokefenn.client.gui.GuiLexicon;

public class ClientTickHandler
{
    public static int ticksWithLexicaOpen = 0;
    public static int pageFlipTicks = 0;

    @SubscribeEvent
    public void tickEnd(TickEvent.ClientTickEvent event)
    {
        if(event.phase == TickEvent.Phase.END && event.type == TickEvent.Type.CLIENT)
        {
            GuiScreen gui = Minecraft.getMinecraft().currentScreen;

            int ticksToOpen = 10;
            if(gui instanceof GuiLexicon)
            {
                if(ticksWithLexicaOpen < 0)
                    ticksWithLexicaOpen = 0;
                if(ticksWithLexicaOpen < ticksToOpen)
                    ticksWithLexicaOpen++;
                if(pageFlipTicks > 0)
                    pageFlipTicks--;
            }
            else
            {
                pageFlipTicks = 0;
                if(ticksWithLexicaOpen > 0)
                {
                    if(ticksWithLexicaOpen > ticksToOpen)
                        ticksWithLexicaOpen = ticksToOpen;
                    ticksWithLexicaOpen--;
                }
            }
        }
    }

    public static void notifyPageChange()
    {
        if(pageFlipTicks == 0)
            pageFlipTicks = 5;
    }
}
