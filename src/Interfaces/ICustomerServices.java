package Interfaces;

import Entities.Customer;
import Entities.Item;
import java.util.ArrayList;
import java.util.UUID;

public interface ICustomerServices {
    void create(Customer customer, ArrayList<UUID> itemsIds);
    boolean deleteAssignedItems(UUID customerId);
    boolean delete(UUID customerId);
    ArrayList<Customer> getAll();
    Customer getById(UUID customerId);
    boolean update(UUID customerId, Customer customer);
    ArrayList<Item> getAssignedItems(UUID customerId);
    void assignItemsToCustomer(UUID customerId, ArrayList<UUID> itemsIds);
    void updateAssignedItemsToCustomer(UUID customerId, ArrayList<UUID> itemsIds);
}
  