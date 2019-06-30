package com.lzl.design_pattern.V_Command;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author lizanle
 * @Date 2019/4/17 14:50
 */
public class Main extends JFrame implements ActionListener,MouseMotionListener,WindowListener {
    private CommandStack history = new CommandStack();
    private DrawCanvas drawCanvas = new DrawCanvas(400,400,history);
    private JButton clearButton = new JButton("clear");
    private JButton undoButton = new JButton("undo");

    public Main(String title) throws HeadlessException {
        super(title);
        this.addWindowListener(this);
        clearButton.addActionListener(this);
        undoButton.addActionListener(this);
        drawCanvas.addMouseMotionListener(this);

        Box buttonBox = new Box(BoxLayout.X_AXIS);
        buttonBox.add(clearButton);
        buttonBox.add(undoButton);

        Box mainBox = new Box(BoxLayout.Y_AXIS);
        mainBox.add(buttonBox);
        mainBox.add(drawCanvas);

        getContentPane().add(mainBox);
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == clearButton){
            history.clear();
            drawCanvas.repaint();
        }else if(e.getSource() == undoButton){
            history.undo();
            drawCanvas.repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Command command = new DrawCommand(drawCanvas,e.getPoint());
        history.append(command);
        command.execute();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    public static void main(String[] args) {
        new Main("command pattern example");
    }
}
