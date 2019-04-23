package pokefenn.totemic.world;

import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import pokefenn.totemic.init.ModVillagers;

public abstract class ComponentTotemicPiece extends StructureVillagePieces.Village {
    public ComponentTotemicPiece() {
    }

    public ComponentTotemicPiece(StructureVillagePieces.Start start, int type) {
        super(start, type);
    }

    @Override
    protected VillagerRegistry.VillagerProfession chooseForgeProfession(int count, VillagerRegistry.VillagerProfession prof) {
        VillagerRegistry.VillagerProfession result = ModVillagers.generateVillagerForPiece();

        if (result == null) {
            return super.chooseForgeProfession(count, prof);
        }

        return result;
    }
}
