package pokefenn.totemic.client.model;

import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;

public class BuffaloModel<T extends Entity> extends CowModel<T> { //FIXME: Implement this. Can we convert the old Buffalo model?
    public BuffaloModel(ModelPart part) {
        super(part);
    }
}
