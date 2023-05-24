package decorator.girl;

import javax.swing.*;
import java.awt.*;

public class Original extends JFrame implements Morrigan {

    private String t = "2219751.jpg";

    public void setImage(String t) {
        this.t = t;
    }

    public Original()  {
        super("显示");
    }

    @Override
    public void display() {
        this.setLayout(new FlowLayout());
        JLabel l1 = new JLabel(new ImageIcon("C:\\Users\\chenlt\\Pictures\\"+t));
        this.add(l1);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
