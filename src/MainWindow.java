import de.ksquared.system.mouse.GlobalMouseListener;
import de.ksquared.system.mouse.MouseAdapter;
import de.ksquared.system.mouse.MouseEvent;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;

public class MainWindow extends JFrame {

    private JPanel rootPanel;
    private JButton stopButton ;
    private JButton startRecordButton ;
    private JButton playButton ;

    final GlobalMouseListener mouseListener = new GlobalMouseListener();
    LinkedList<MouseEvent> sequenceOfPoints = new LinkedList<MouseEvent>();

    public MainWindow(){
        super("Autoclicker 0.0.1");
        setContentPane(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500,500,500,500);
        try {
            Image startImage = ImageIO.read(getClass().getResource("resources/start1.jpg"));
            //playButton = new JButton();
            Image playImage = ImageIO.read(getClass().getResource("resources/play1.jpg"));
            Image stopImage = ImageIO.read(getClass().getResource("resources/wm_pause1.png"));

            playButton.setIcon(new ImageIcon(playImage));
            startRecordButton.setIcon(new ImageIcon(startImage));
            stopButton.setIcon(new ImageIcon(stopImage));
        } catch (IOException ex) {
        }

        pack();


        setDefaultCloseOperation(EXIT_ON_CLOSE);

        final MouseListenerThread thread = new MouseListenerThread();


        startRecordButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e2)
            {
               thread.start();
                stopButton.setEnabled(true);





            }
        });

        stopButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e2)
            {
                thread.stopListener();
                thread.stop();
             playButton.setEnabled(true);




            }
        });

        playButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e2)
            {

                ListIterator<MouseEvent> listIterator = sequenceOfPoints.listIterator();
                while (listIterator.hasNext()) {
                    MouseEvent event = listIterator.next();
                    Robot robot;
                    if(event.getButton() == 0) {
                        try {
                            robot = new Robot();
                            robot.mouseMove(event.getX(), event.getY());
                        } catch (AWTException e) {
                            e.printStackTrace();
                        }
                    } else if(event.getButton() == 2) {
                        try {
                            robot = new Robot();
                            robot.mouseMove(event.getX(), event.getY());
                            robot.mousePress(InputEvent.BUTTON1_MASK);
                            robot.mouseRelease(InputEvent.BUTTON1_MASK);
                        } catch (AWTException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });


    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }


    public class MouseListenerThread extends Thread {
        MouseAdapter listener;
        public void run() {
            listener = new MouseAdapter() {
                @Override public void mousePressed(MouseEvent event) {
                    sequenceOfPoints.add(event);

                }
                @Override public void mouseMoved(MouseEvent event) {
                    sequenceOfPoints.add(event);

                }
            };
            mouseListener.addMouseListener(listener);
            while(true)
                try { Thread.sleep(100); }
                catch(InterruptedException e) { e.printStackTrace(); }
        }
        public void stopListener() {
            mouseListener.removeMouseListener(listener);
        }
    }
}
