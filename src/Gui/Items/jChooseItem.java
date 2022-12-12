package Gui.Items;

import Gui.Customers.jViewCustomer;
import Entities.Item;
import Gui.jHomePage;
import Gui.Customers.jNewCustomer;
import Services.CustomerServices;
import Services.ItemServices;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import utils.filterItems;

public class jChooseItem extends javax.swing.JPanel {
    public jChooseItem(jHomePage jhp,JPanel parent ) {
        initComponents();
        _jHomePage = jhp;
        _ItemServices = new ItemServices();
        _CustomerServices= new CustomerServices();
        _filterItems = new filterItems();
        _parent= parent;   
        if (parent instanceof jNewCustomer )
        {
            //fn tzabat el gui
             _jNewCustomer =(jNewCustomer)parent;
             _jViewCustomer=null;
          // jNewCustomer.
        }
        else 
        {   
           _jViewCustomer =(jViewCustomer)parent;
           _jNewCustomer=null;
        } 

    }
    public void edit()
    {
        _jViewCustomer.updateflag=!_jViewCustomer.updateflag;
        jedit.setEnabled(false);
        jupdate.setEnabled(true);
        jcancel.setEnabled(true);
        jRemove.setEnabled(true);
        jAdd.setEnabled(true);
        jBack.setEnabled(true);
        jItems.setEnabled(true);
    }
    void update()
    {        
        _jViewCustomer.updateflag=!_jViewCustomer.updateflag;
        jedit.setEnabled(true);
        jDone.setVisible(false);
        jupdate.setEnabled(false);
        jcancel.setEnabled(false);
        jRemove.setEnabled(false);
        jBack.setEnabled(true);
        jAdd.setEnabled(false);
        renderData();
    }
    void Cancel()
    {        _jViewCustomer.updateflag=!_jViewCustomer.updateflag;
        jedit.setEnabled(true);
        jBack.setEnabled(true);
        jupdate.setEnabled(false);
        jcancel.setEnabled(false);
        jRemove.setEnabled(false);
        jAdd.setEnabled(false);
        _jViewCustomer.updateflag=!_jViewCustomer.updateflag;      
    }
    void setDefault()
    {
        jcancel.setVisible(true);
        jedit.setVisible(true); 
        jupdate.setVisible(true);
        jRemove.setVisible(true);
        jDone.setVisible(true);
        jBack.setVisible(true);
        jAddedItems.setVisible(true);
        jItems.setVisible(true);
        jcancel.setEnabled(true);
        jedit.setEnabled(true); 
        jupdate.setEnabled(true);
        jRemove.setEnabled(true);
        jDone.setEnabled(true);
        jBack.setEnabled(true);
        jAddedItems.setEnabled(true);
        jItems.setEnabled(true);
       
        
    }
    void guisetforshowpurchases()
    {
        System.out.print("vbbb");
          jcancel.setEnabled(false);
        jupdate.setEnabled(false);
        jRemove.setEnabled(false);
        jDone.setVisible(false);
        jAdd.setEnabled(false);
        jItems.setEnabled(false);
        
    }
    void guisetforupdatepurchases()
    {
        jDone.setVisible(false);
        jedit.setEnabled(false);
        jBack.setEnabled(false);
    }
    void guisetnewcustomer()
    {
        jcancel.setVisible(false);
        jedit.setVisible(false);
        jupdate.setVisible(false);
        
    }
    
    public void done() {
        DefaultTableModel m= (DefaultTableModel) jAddedItems.getModel();
        int row=m.getRowCount();
        int col=5;
        ArrayList<Item> items =new ArrayList<Item>();
        for (int r=0;r<row;r++)
        {   
            Object [] itemdata=new Object[col];
            for(int c=0;c<col;c++)
            {
                itemdata[c]= m.getValueAt(r, c);
            }
            Item item =new Item();
            item.id=(UUID) itemdata[0];
            item.name= (String) itemdata[1];
            item.category=(String) itemdata[2];
            item.price=(int) itemdata[3];
            item.createdAt=(Date) itemdata[4];
            items.add(item);
        }
        _jNewCustomer.setselecteditems(items);
        _jHomePage.switchPanels(_parent);
    }
    public void resetPanel() {
        String [] titles= {"Id", "Name","Category","Price","CreatedAt"};
        DefaultTableModel m1 = (DefaultTableModel) jItems.getModel();
         m1.setColumnIdentifiers(titles);
         m1.setRowCount(0);
          DefaultTableModel m2 = (DefaultTableModel) jAddedItems.getModel();
         m2.setColumnIdentifiers(titles);
         m2.setRowCount(0);
        jSearch.setText("");
        jItemsCombo.selectWithKeyChar('n');
        jError.setText("");
    }
    private void removeItemFromPreviewTable() {
        if(jAddedItems.getSelectedRow() != -1) {
            DefaultTableModel m = (DefaultTableModel) jAddedItems.getModel();
            m.removeRow(jAddedItems.getSelectedRow());
            
         }
    }
    private void addItemToPreviewTable() {
        int row = jItems.getSelectedRow();//check ! -1
        if(row!=-1)
        {
            int colNumber = 5;
            Object[] result = new Object[colNumber];
            for (int i = 0; i < colNumber; i++) {
                result[i] = jItems.getModel().getValueAt(row, i);
            }
            DefaultTableModel model = (DefaultTableModel) jAddedItems.getModel();
            model.addRow(result);
        }
    }
    public void renderData() {
        ArrayList<Item> items = _ItemServices.getAllItems();
        if(!items.isEmpty()) {
            String search = jSearch.getText();
            String sortBy = (String) jItemsCombo.getSelectedItem();
            ArrayList<Item> filteredItems = _filterItems.filter(items, search, sortBy, false);
            if(!filteredItems.isEmpty()) {
                resetPanel();
                DefaultTableModel model = (DefaultTableModel) jItems.getModel();
                 
                for(int i=0;i<filteredItems.size();i++) {
                    Item item = filteredItems.get(i);
                    System.err.println(item.id);
                    Object[] data = { item.id, item.name, item.category, item.price, item.createdAt };
                    model.addRow(data);
                }
            }
        }
        ArrayList<Item> item ;
        if (_parent instanceof jNewCustomer){
            item =_jNewCustomer.getselecteditems();
        }
        else 
        {
            UUID id=_jViewCustomer._chosencustomer.id;
            item = _CustomerServices.getAssignedItems(id);
        }
        DefaultTableModel m=(DefaultTableModel) jAddedItems.getModel();
        for(int i=0;i<item.size();i++)
        {
            Item it= item.get(i);
            Object[] data= { it.id, it.name, it.category, it.price, it.createdAt };
            m.addRow(data);
        }
        if(_parent instanceof jViewCustomer && _jViewCustomer.updateflag)
        {
            setDefault();
            guisetforupdatepurchases();
            
        }
        else if (_parent instanceof jViewCustomer && !_jViewCustomer.updateflag)
        {
           // setDefault();
            guisetforshowpurchases();
        }
        else 
        {
            setDefault();
            guisetnewcustomer();
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBack = new javax.swing.JButton();
        jAdd = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSearch = new javax.swing.JTextField();
        jItemsCombo = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jItems = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jAddedItems = new javax.swing.JTable();
        jRemove = new javax.swing.JButton();
        jDone = new javax.swing.JButton();
        jError = new javax.swing.JLabel();
        jedit = new javax.swing.JButton();
        jupdate = new javax.swing.JButton();
        jcancel = new javax.swing.JButton();

        jBack.setText("Back");
        jBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jBackMouseClicked(evt);
            }
        });

        jAdd.setText("ADD");
        jAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jAddMouseClicked(evt);
            }
        });

        jLabel2.setText("Search :");

        jLabel3.setText("Sort By :");

        jItemsCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Category", "Name", "ID", "Price" }));

        jItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Category", "Name", "ID", "Price", "Title 5"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jItems);

        jAddedItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Category", "Name", "ID", "Price", "Title 5"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jAddedItems);

        jRemove.setText("Remove");
        jRemove.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRemoveMouseClicked(evt);
            }
        });

        jDone.setText("Done");
        jDone.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jDoneMouseClicked(evt);
            }
        });

        jError.setForeground(new java.awt.Color(255, 0, 0));

        jedit.setText("Edit");
        jedit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jeditMouseClicked(evt);
            }
        });

        jupdate.setText("Update");
        jupdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jupdateMouseClicked(evt);
            }
        });

        jcancel.setText("Cancel");
        jcancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jcancelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(90, 90, 90)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSearch)
                            .addComponent(jItemsCombo, 0, 409, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jDone)
                        .addGap(12, 12, 12)
                        .addComponent(jedit, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jError)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jupdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addComponent(jRemove)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBack)))
                .addGap(0, 14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jItemsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBack)
                    .addComponent(jAdd)
                    .addComponent(jRemove)
                    .addComponent(jDone)
                    .addComponent(jError)
                    .addComponent(jedit)
                    .addComponent(jupdate)
                    .addComponent(jcancel))
                .addContainerGap(21, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBackMouseClicked
        _jHomePage.switchPanels(_parent);
    }//GEN-LAST:event_jBackMouseClicked

    private void jAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jAddMouseClicked
        addItemToPreviewTable();
    }//GEN-LAST:event_jAddMouseClicked

    private void jRemoveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRemoveMouseClicked
        removeItemFromPreviewTable();
    }//GEN-LAST:event_jRemoveMouseClicked

    private void jDoneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDoneMouseClicked
        done();
    }//GEN-LAST:event_jDoneMouseClicked

    private void jupdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jupdateMouseClicked
        setDefault();
        update();
    }//GEN-LAST:event_jupdateMouseClicked
     
    private void jeditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jeditMouseClicked
       setDefault();
        edit();
    }//GEN-LAST:event_jeditMouseClicked
    private void jcancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcancelMouseClicked
        setDefault();
        Cancel();
    }//GEN-LAST:event_jcancelMouseClicked
     private final JPanel _parent;
    private final ItemServices _ItemServices;
    private final CustomerServices _CustomerServices; 
    private final jHomePage _jHomePage;
    private final jNewCustomer _jNewCustomer;
    private final jViewCustomer _jViewCustomer;
    private final filterItems _filterItems;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jAdd;
    private javax.swing.JTable jAddedItems;
    private javax.swing.JButton jBack;
    private javax.swing.JButton jDone;
    private javax.swing.JLabel jError;
    private javax.swing.JTable jItems;
    private javax.swing.JComboBox<String> jItemsCombo;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton jRemove;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTextField jSearch;
    private javax.swing.JButton jcancel;
    private javax.swing.JButton jedit;
    private javax.swing.JButton jupdate;
    // End of variables declaration//GEN-END:variables

    
}
