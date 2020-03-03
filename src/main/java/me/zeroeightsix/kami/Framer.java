package me.zeroeightsix.kami;

import com.google.common.hash.Hashing;

import javax.swing.*;


public class Framer extends JFrame {
    public Framer() {
        this.setTitle("NutGod UUID Verify Failed");
        this.setDefaultCloseOperation(2);
        this.setLocationRelativeTo(null);
        String message = "Why you try to use the best client with no permission eh black boi" + "\n" ;
        JOptionPane.showMessageDialog(this, message, "NutGod UUID Verify Failed", -1, UIManager.getIcon("OptionPane.warningIcon"));
    }

}