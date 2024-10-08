package AsyncConcreteSystemComponents;

import AsyncAbstractSystemComponents.AbstractAsyncInputOnlyComponent;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class LightBulb extends AbstractAsyncInputOnlyComponent
{
    private LightGUI lightGUI;

    public LightBulb() throws IOException {
        lightGUI = new LightGUI();
    }

    @Override
    protected void processInput(String s)
    {
        System.out.println("PROCESS INPUT: "+s);
        lightGUI.toggleLight(!s.contains("OFF"));
    }



    public static class LightGUI extends JFrame {

        private boolean isLightOn = false;
        private ImageIcon iconOn;
        private ImageIcon iconOff;
        private JLabel lightBulb;
        private BufferedImage imageOn;
        private BufferedImage imageOff;

        public LightGUI() throws IOException {
            setTitle("Light Bulb");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new FlowLayout());

            //office image light on
            imageOn = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("office_simple.JPG")));
            System.out.println(imageOn.toString());
            iconOn = new ImageIcon(imageOn);

            //office image light off
            imageOff = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("office_simple_off.JPG")));
            iconOff = new ImageIcon(imageOff);
            lightBulb = new JLabel(iconOff);
            getContentPane().add(lightBulb);
            lightBulb.setVisible(true);
            pack();
            setVisible(true);
        }

        public void toggleLight(boolean lightOn) {
            if (lightOn)
            {
                //turn light on
                lightBulb.setIcon(iconOn);
            }
            else
            {
                //turn light off
                lightBulb.setIcon(iconOff);
            }
        }

    }
}
