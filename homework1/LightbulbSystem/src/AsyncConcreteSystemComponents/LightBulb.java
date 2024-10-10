package AsyncConcreteSystemComponents;

import AsyncAbstractSystemComponents.AbstractAsyncInputOnlyComponent;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class LightBulb extends AbstractAsyncInputOnlyComponent
{
    private LightGUI lightGUI;

    public LightBulb(boolean isActive) throws IOException
    {
        lightGUI = new LightGUI(isActive); // Pass the boolean variable to the LightGUI constructor
    }

    @Override
    protected void processInput(String s)
    {
        System.out.println("PROCESS INPUT: " + s);
        lightGUI.toggleLight(!s.contains("OFF"));
    }

    public static class LightGUI extends JFrame {

        private boolean isLightOn = false;
        private ImageIcon iconOn;
        private ImageIcon iconOff;
        private JLabel lightBulb;
        private BufferedImage imageOn;
        private BufferedImage imageOff;

        public LightGUI(boolean isActive) throws IOException {
            setTitle(isActive ? "LightBulb Active" : "LightBulb Inactive"); // Set title based on boolean value
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new FlowLayout());

            // Office image light on
            imageOn = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("office_simple.JPG")));
            System.out.println(imageOn.toString());
            iconOn = new ImageIcon(imageOn);

            // Office image light off
            imageOff = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("office_simple_off.JPG")));
            iconOff = new ImageIcon(imageOff);
            lightBulb = new JLabel(iconOff);
            getContentPane().add(lightBulb);
            lightBulb.setVisible(true);
            pack();
            setVisible(true);
        }

        public void toggleLight(boolean lightOn) {
            if (lightOn) {
                // Turn light on
                lightBulb.setIcon(iconOn);
            } else {
                // Turn light off
                lightBulb.setIcon(iconOff);
            }
        }
    }
}
