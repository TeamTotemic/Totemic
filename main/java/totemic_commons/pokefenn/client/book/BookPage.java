package totemic_commons.pokefenn.client.book;

import org.w3c.dom.Element;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 26/02/14
 * Time: 18:37
 */
public abstract class BookPage
{
    protected GuiTotempedia manual;
    protected int side;

    public void init (GuiTotempedia manual, int side)
    {
        this.manual = manual;
        this.side = side;
    }

    public abstract void readPageFromXML (Element element);

    public void renderBackgroundLayer (int localwidth, int localheight)
    {
    }

    public abstract void renderContentLayer (int localwidth, int localheight);
}