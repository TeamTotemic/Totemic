package totemic_commons.pokefenn.client.book.pages;

import org.w3c.dom.Element;
import totemic_commons.pokefenn.client.book.GuiTotempedia;

public abstract class BookPage
{
    protected GuiTotempedia manual;
    protected int side;

    public void init(GuiTotempedia manual, int side)
    {
        this.manual = manual;
        this.side = side;
    }

    public abstract void readPageFromXML(Element element);

    public void renderBackgroundLayer(int localwidth, int localheight)
    {
    }

    public abstract void renderContentLayer(int localwidth, int localheight);
}
