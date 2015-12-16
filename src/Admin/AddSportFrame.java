/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import DataBase.ConnectionTimeOutException;
import DataBase.DBOperations;
import Domain.Sport;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Irfad Hussain
 */
public class AddSportFrame extends javax.swing.JFrame {

    /**
     * Creates new form AddSportFrame
     */
    
    private AdminFrame parent;
    private DBOperations dbHandler;
    
    public AddSportFrame(AdminFrame parent) {
        initComponents();
        this.parent = parent;
        this.dbHandler = DBOperations.getInstace();
        setLocationRelativeTo(parent);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtSportName = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAllocation = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstResources = new javax.swing.JList();
        btnSelectResource = new javax.swing.JButton();
        btnRemoveResource = new javax.swing.JButton();
        btnAddSport = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setText("Sport Name :");

        tblAllocation.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Resource", "Utilization"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblAllocation);
        if (tblAllocation.getColumnModel().getColumnCount() > 0) {
            tblAllocation.getColumnModel().getColumn(1).setPreferredWidth(100);
        }

        jScrollPane2.setViewportView(lstResources);

        btnSelectResource.setText("<<");
        btnSelectResource.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectResourceActionPerformed(evt);
            }
        });

        btnRemoveResource.setText(">>");
        btnRemoveResource.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveResourceActionPerformed(evt);
            }
        });

        btnAddSport.setText("Add Sport");
        btnAddSport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSportName, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(230, 230, 230))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnAddSport, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnSelectResource, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                                    .addComponent(btnRemoveResource, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(30, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtSportName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane2)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSelectResource)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoveResource)))
                .addGap(18, 18, 18)
                .addComponent(btnAddSport, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        parent.setEnabled(true);
    }//GEN-LAST:event_formWindowClosing

    private void btnAddSportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSportActionPerformed
        if (txtSportName.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Sport Name field Cannot be empty", "Field empty", JOptionPane.WARNING_MESSAGE);
        }
        try {
            Sport sport = new Sport();
            sport.setSportName(txtSportName.getText());
            dbHandler.addSport(sport);
            JOptionPane.showMessageDialog(this, "Sport added successfully.", "Operation Success", JOptionPane.INFORMATION_MESSAGE);
            formWindowClosing(null);
            this.dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error! Cannot add sport", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ConnectionTimeOutException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error! Cannot connect to database", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAddSportActionPerformed

    private void btnSelectResourceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectResourceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSelectResourceActionPerformed

    private void btnRemoveResourceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveResourceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRemoveResourceActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddSport;
    private javax.swing.JButton btnRemoveResource;
    private javax.swing.JButton btnSelectResource;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList lstResources;
    private javax.swing.JTable tblAllocation;
    private javax.swing.JTextField txtSportName;
    // End of variables declaration//GEN-END:variables
}
