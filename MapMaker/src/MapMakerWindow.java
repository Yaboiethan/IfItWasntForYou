import javafx.scene.control.Tooltip;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapMakerWindow extends JFrame
{
    public MapMakerWindow()
    {
        super();
        Initialize();
    }

    private void Initialize() //Prepare frame and components
    {
        //Customize the JFrame
        setSize(new Dimension(620,800));
        setLayout(null);
        setTitle("Sprite Map Maker");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create the mapview JPanel
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setSize(new Dimension(460,600));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Map View"));
        add(scrollPane);

        GridPanel mapView = new GridPanel();
        scrollPane.setViewportView(mapView);

        //region Zoombar
        JPanel southPanel = new JPanel();
        southPanel.setBorder(BorderFactory.createTitledBorder("Log"));
        southPanel.setBounds(0,600,610, 200);
        add(southPanel);
        JSlider zoomSlider = new JSlider(JSlider.HORIZONTAL, 25, 175, 100);
        zoomSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                mapView.setZoomFactor((double) zoomSlider.getValue() / 100);
            }
        });
        //Add slider labels
        zoomSlider.setMajorTickSpacing(25);
        zoomSlider.setMinorTickSpacing(25);
        zoomSlider.setPaintTicks(true);
        zoomSlider.setPaintLabels(true);
        southPanel.add(new JLabel("Zoom"));
        southPanel.add(zoomSlider);
        revalidate();
        //endregion

        //Add JMenu
        JMenuBar menuBar = new JMenuBar();

        //region FileMenu
        JMenu file = new JMenu("File");
        //Add items
        JMenuItem save = new JMenuItem("Save");
        JMenuItem saveAs = new JMenuItem("Save As");
        JMenuItem load = new JMenuItem("Load");
        //Add listeners

        //Add to file
        file.add(save);
        file.add(saveAs);
        file.add(load);
        menuBar.add(file);
        //endregion

        //region GridMenu
        JMenu grid = new JMenu("Grid");
        JMenuItem resetZoom = new JMenuItem("Reset View");
        resetZoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zoomSlider.setValue(100);
            }
        });
        grid.add(resetZoom);
        menuBar.add(grid);
        //endregion

        //region HelpMenu
        JMenu help = new JMenu("Help");

        menuBar.add(help);
        //endregion

        //region LayerTab
        LayersTab layerPanel = new LayersTab();
        layerPanel.setBounds(460, 0, 150, 600);
        layerPanel.setBorder(BorderFactory.createTitledBorder("Layers & Tiles"));
        add(layerPanel);
        //endregion


        //Ready for viewing
        setJMenuBar(menuBar);
        setVisible(true);
    }
}

class MapMakerRunner
{
    public static void main(String[] args)
    {
        MapMakerWindow window = new MapMakerWindow();
    }
}
