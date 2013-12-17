package totemic_commons.pokefenn.totem;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;


public class TotemArrayList {
	
	
	public static List<TotemArrayList> recipes = new ArrayList<TotemArrayList>();
	
	
	public static void addTotems(){
		
		
		
	}

	
	private final ItemStack source;
    private final ItemStack result;
    private final int crystalValue;
    
    
        
        public TotemArrayList(ItemStack input, ItemStack output, int crystalCharge){
       	 
            this.source = input;
            this.result = output;
            this.crystalValue = crystalCharge;
        }
        
        public ItemStack getInput(){
            return this.source;
        }
        
        public ItemStack getResult(){
            return this.result;
        }
        
        public int getCrystalValue(){
        	return this.crystalValue;
        	
        }
        

	 
	  public static List<TotemArrayList> getRecipes() {
         return recipes;
 }
	
	
}
