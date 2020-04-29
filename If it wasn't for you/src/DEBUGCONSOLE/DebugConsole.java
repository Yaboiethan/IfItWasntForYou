package DEBUGCONSOLE;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class DebugConsole extends JFrame
{
    //Various Variables for the log
    private JTextArea textField;
    private JTextArea viewArea;
    //Used to keep track of what commands are valid
    private final String[] commands = {"!help", "!toggleColliders", "!toggleMouseCoors", "!showCenter", "!showPosition"};
    private final String[] descriptions = {"Displays all commands", "Toggles Collider visuals",
            "Toggle Mouse Coordinates", "Displays center of object", "Shows object Position"};

    //Static debug variables to enact changes
    public static boolean SHOW_COLLIDERS;
    public static boolean SHOW_MOUSE_COORS;
    public static boolean SHOW_OBJECT_ORIGIN;
    public static boolean SHOW_OBJECT_POSITION;


    //FOR USE OF TESTING DEBUG LOG
    public static void main(String[] args)
    {
        DebugConsole TestDb = new DebugConsole();
        TestDb.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public DebugConsole()
    {
        Initailize();
    }

    public void Initailize()
    {
        //Set up the JFrame
        setTitle("Debug Console");
        setSize(300,300);
        setResizable(false);

        //Set up the JScrollPanel
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);

        //Set up the JPanel
        JPanel mainArea = new JPanel();
        mainArea.setLayout(new BorderLayout());
        mainArea.setSize(300,300);
        mainArea.setBackground(Color.BLACK);
        scrollPane.setViewportView(mainArea);

        //Set up the textArea
        textField = new JTextArea();
        textField.setBorder(BorderFactory.createCompoundBorder());
        textField.setBackground(Color.DARK_GRAY);
        textField.setForeground(Color.GREEN);
        textField.setPreferredSize(new Dimension(300,50));
        mainArea.add(textField, BorderLayout.SOUTH);


        //Set up display area
        JScrollPane northPane = new JScrollPane();
        northPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainArea.add(northPane, BorderLayout.NORTH);

        viewArea = new JTextArea();
        viewArea.setPreferredSize(new Dimension(300,200));
        viewArea.setEditable(false);
        viewArea.setBackground(Color.BLACK);
        viewArea.setForeground(Color.GREEN);
        //Set up initial text
        viewArea.setText("Welcome to the debug console, type a command: \n  Type '!help' to view commands");
        northPane.setViewportView(viewArea);


        //Set up the enter command thing
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int key = e.getKeyCode();
                if(key == KeyEvent.VK_ENTER)
                {
                    AddToViewArea(textField.getText());
                }
            }
        });

        revalidate();
    }

    private void AddToViewArea(String s)
    {
        int coFound = -1;
        //Check if invalid command
        for(int i = 0; i < commands.length; i++)
        {
            if(commands[i].equals(s))
            {
                coFound = i;
                break;
            }
        }

        if(coFound != -1)
        {
            EnactCommand(coFound);
        }
        else
        {
            AddTextToView("INVALID COMMAND");
        }
        textField.setText("");
    }

    public void AddTextToView(String s)
    {
        viewArea.setText(viewArea.getText() + '\n' + s);
    }

    private void EnactCommand(int index)
    {
        switch(index)
        {
            case 0: //Help
                AddTextToView("Available Commands: ");
                for(int i = 0; i < commands.length; i++)
                {
                    AddTextToView("    " + commands[i] + " - " + descriptions[i]);
                }
                break;

            case 1: //Toggle Colliders
                SHOW_COLLIDERS = !SHOW_COLLIDERS;
                AddTextToView("Colliders Visible = " + SHOW_COLLIDERS);
                break;

            case 2: //Mouse Coordinates
                SHOW_MOUSE_COORS = !SHOW_MOUSE_COORS;
                AddTextToView("Mouse Coors. = " + SHOW_MOUSE_COORS);
                break;

            case 3: //Object Origin
                SHOW_OBJECT_ORIGIN = !SHOW_OBJECT_ORIGIN;
                AddTextToView("Object Origin = " + SHOW_OBJECT_ORIGIN);
                break;

            case 4: //Object Position
                SHOW_OBJECT_POSITION = !SHOW_OBJECT_POSITION;
                AddTextToView("Object Position = " + SHOW_OBJECT_POSITION);
                break;
        }
    }
}
