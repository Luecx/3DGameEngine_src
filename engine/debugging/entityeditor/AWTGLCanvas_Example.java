package engine.debugging.entityeditor;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.AWTGLCanvas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AWTGLCanvas_Example extends JFrame
{
    private JPanel          contentPane;
    private MyAWTGLCanvas   mCanvas;

    /**
     * Create the frame.
     */
    public AWTGLCanvas_Example()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        try
        {
            mCanvas = new MyAWTGLCanvas();
        }
        catch (LWJGLException e)
        {
            e.printStackTrace();
        }
        contentPane.add(mCanvas, BorderLayout.CENTER);
        this.setVisible(true);
        mCanvas.start();
    }

    public static void main(String[] args) {
        AWTGLCanvas_Example example = new AWTGLCanvas_Example();
    }

}