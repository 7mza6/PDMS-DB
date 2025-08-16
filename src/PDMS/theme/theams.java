/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PDMS.theme;

import com.formdev.flatlaf.FlatClientProperties;
import com.raven.chart.Chart;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Gaming
 */
public class theams {
    
    public static void Table_style(JTable tbl) {
        tbl.getTableHeader().putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");
        tbl.putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");
        ((DefaultTableCellRenderer) tbl.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.LEFT);
    }
    
    public static void Chart_style(Chart chart ) {
       chart.putClientProperty(FlatClientProperties.STYLE, 
        "background: lighten(@background,5%); " + 
        "foreground: @foreground; " + 
        "arc: 10; "  
);
    }
}

