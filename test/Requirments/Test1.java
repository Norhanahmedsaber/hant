/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Requirments;

import Entities.Item;
import Entities.Log;
import Entities.Role;
import Entities.User;
import Gui.Users.*;
import Gui.jHomePage;
import Gui.jMainPage;
import Services.AccountServices;
import Services.CustomerServices;
import Services.ItemServices;
import Services.LogServices;
import Services.RoleServices;
import Services.UserServices;
import java.awt.Color;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class Test1 {
    AccountServices accountServices;
    UserServices userServices;
    RoleServices roleServices;
    ItemServices itemServices;
    CustomerServices customerServices;
    LogServices logServices;
    public Test1() {
        accountServices = new AccountServices();
    }
    private void setUpRoles() {
        Role admin = new Role();
        admin.id = UUID.randomUUID();
        admin.name = "admin";
        admin.canCreateCustomer = true;
        admin.canCreateItem = true;
        admin.canViewLogs = true;
        roleServices.create(admin);
        
        Role role1 = new Role();
        role1.id = UUID.randomUUID();
        role1.name = "employee1";
        role1.canCreateCustomer = false;
        role1.canCreateItem = true;
        role1.canViewLogs = false;
        roleServices.create(role1);
        
        Role role2 = new Role();
        role2.id = UUID.randomUUID();
        role2.name = "employee2";
        role2.canCreateCustomer = true;
        role2.canCreateItem = true;
        role2.canViewLogs = false;
        roleServices.create(role2);
        
        Role role3 = new Role();
        role3.id = UUID.randomUUID();
        role3.name = "employee3";
        role3.canCreateCustomer = false;
        role3.canCreateItem = false;
        role3.canViewLogs = false;
        roleServices.create(role3);
    }
    private void setUpUsers() {
        Role admin = roleServices.getByName("admin");
        User user1 = new User();
        user1.name = "Hussien";
        user1.userName = "admin";
        user1.password = "00000000";
        user1.role = admin;
        userServices.create(user1);
        
        Role role1 = roleServices.getByName("employee1");
        User user2 = new User();
        user2.name = "Anas Hesham";
        user2.userName = "user1";
        user2.password = "00000000";
        user2.role = role1;
        userServices.create(user2);
        
        Role role2 = roleServices.getByName("employee2");
        User user3 = new User();
        user3.name = "Anas Hesham";
        user3.userName = "user2";
        user3.password = "00000000";
        user3.role = role2;
        userServices.create(user3);
        
        Role role3 = roleServices.getByName("employee3");
        User user4 = new User();
        user4.name = "Anas Hesham";
        user4.userName = "user3";
        user4.password = "00000000";
        user4.role = role3;
        userServices.create(user4);

    }
    private Item getItem() {
        Item item1 = new Item();
        Calendar cl = Calendar.getInstance();
        
        item1.name="Oven";
        item1.category="Electrical devices";
        item1.description="Toshiba";
        cl.setTime(new Date());
        cl. add(Calendar.MONTH, 1);
        item1.createdAt= cl.getTime();
        item1.price=1000;
        
        return item1;
    }
    
    private ArrayList<Log> getLogs()
    {
        Log bean = new Log();
        UUID id = UUID.randomUUID();
        bean.id = id;
        bean.actedOn = "Item";
        bean.action = "Created";
        bean.userName = "anazz";
        bean.actedOnName = "anas";
        bean.userRole = "admin";
        bean.date = Date.from(Instant.EPOCH);
        
        Log bean2 = new Log();
        UUID id2 = UUID.randomUUID();
        bean.id = id2;
        bean.actedOn = "Item";
        bean.action = "Created";
        bean.userName = "anazz";
        bean.actedOnName = "anas";
        bean.userRole = "admin";
        bean.date = Date.from(Instant.EPOCH);
        
         ArrayList<Log> logs = new ArrayList<>();
         logs.add(bean);
         logs.add(bean2);
         return logs;
    }
    @Before
    public void setUp() {
        userServices.deleteAll();
        roleServices.deleteAll();
        setUpRoles();
        setUpUsers();
    }
    @Test
    public void testCase1() {
        //Login
        User user = accountServices.login("user3", "0000000"); // wrong password
        assertNull(user);
        
        // No Auth
        user = accountServices.login("user3", "00000000"); // user with no auth
        assertNotNull(user);
        
        assertFalse(user.role.canCreateItem);
        
        // Can create item only
        user = accountServices.login("user1", "00000000"); // user can only create item 
        assertNotNull(user);
        
        assertTrue(user.role.canCreateItem);
        
        Item item = getItem();
        itemServices.create(item);
        
        //user can create customer and item
        
        //admin
        user = accountServices.login("admin","00000000");
        assertNotNull(user);
        assertTrue(user.role.canCreateItem);
        
        
        assertTrue(user.role.canCreateCustomer);
      
        
        
        assertTrue(user.role.canViewLogs);
       
        ArrayList<Log> actualLogs = new ArrayList<>();
        actualLogs = getLogs();
       
        ArrayList<Log> expLogs = new ArrayList<Log>();
        expLogs=logServices.getAll();
        
        ArrayList<String> actual = new ArrayList<>();
        ArrayList<String> expected = new ArrayList<>();
        
        for(Log log : expLogs) {
            expected.add(log.toString());
        }
        for(Log log : actualLogs) {
            actual.add(log.toString());
        }
        assertArrayEquals(expected.toArray(), actual.toArray());
        
        
        
        
    }
  

}
