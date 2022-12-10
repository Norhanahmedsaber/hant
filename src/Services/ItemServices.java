package Services;
import Entities.Item;
import Interfaces.IItemServices;
import java.util.ArrayList;
import java.util.UUID;

public class ItemServices implements IItemServices {
    public static ArrayList<Item> items = new ArrayList<Item>();

    @Override
    public void create(Item item) {
        items.add(item);
    }

    @Override
    public void delete(UUID itemId) {
        System.out.println(itemId); 
        items.remove(itemId);
         
    }

    @Override
    public Item getById(UUID itemId) {
        for(int i=0;i<items.size();i++) {
            if(items.get(i).id == itemId) {
                return items.get(i);
            }
        }
        return null;}

    @Override
    public void update(UUID ItemId, Item item) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Item> getAllItems() {
        return items;
    }

    @Override
    public void addItemsToCustomer(UUID customerId , ArrayList<UUID> itemsIds) {
        
        System.out.println("Added Succesfully");
    }
    
    
}
