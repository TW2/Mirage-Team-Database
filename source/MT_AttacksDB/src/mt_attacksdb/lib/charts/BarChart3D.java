/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mt_attacksdb.lib.charts;

import java.awt.Color;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Yves
 */
public class BarChart3D extends JPanel {
    
    /**
* Create a new pie chart with the data
* @param theme The title of this chart
* @param report The categories with their percent or number
*/
    public BarChart3D(String theme, Map<String, Integer> report){
        //Set the chart data
        DefaultCategoryDataset bardataset = new DefaultCategoryDataset();
        for(String key : report.keySet()){
            bardataset.setValue(report.get(key), key, key);
        }
        
        //Set the chart configuration
        JFreeChart chart = ChartFactory.createBarChart3D(theme, "", "Lines", bardataset, PlotOrientation.VERTICAL, true, true, false);
        ImageIcon iicon = new ImageIcon(getClass().getResource("thousand sunny.jpg"));
        chart.getPlot().setBackgroundImage(iicon.getImage());
        ChartPanel cp = new ChartPanel(chart);
        
        //Add the chart to our panel
        setLayout(null);
        cp.setSize(400, 200);
        add(cp);
        
        //Our panel
        setSize(400,200);
        setBackground(Color.white);
    }
    
}
