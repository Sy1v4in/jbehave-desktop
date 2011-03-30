package desktop.example.calculator;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class SampleUtils {

  public static void show(JComponent panel, String title) throws Exception {
    JFrame frame = createFrame(panel);
    frame.setTitle(title);
    show(frame);
  }

  public static void show(Window window) throws Exception {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    window.pack();
    window.setVisible(true);
  }

  public static JFrame createFrame(JComponent component) {
    JFrame frame = new JFrame();
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
    frame.getContentPane().add(component);
    return frame;
  }
}
