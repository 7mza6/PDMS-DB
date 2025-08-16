package raven.cellButton;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author RAVEN
 */
public class TableActionCellRender extends DefaultTableCellRenderer {
    boolean isVisable = true;
    boolean isAssign = false;

    public TableActionCellRender() {
        
    }

    public TableActionCellRender(boolean isVisable,boolean isAssign) {
        this.isVisable = isVisable;
        this.isAssign = isAssign;
        
    }
    

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean isSeleted, boolean bln1, int row, int column) {
        Component com = super.getTableCellRendererComponent(jtable, o, isSeleted, bln1, row, column);
        PanelAction action = new PanelAction();
        if(isAssign){
        action.setAssign(true);
        }
        if(!isVisable){
        action.setVisible(false);
        }
        action.putClientProperty(FlatClientProperties.STYLE, 
        "background: lighten(@background,5%); " + 
        "foreground: @foreground; " + 
        "arc: 10; "  
        );
        
        return action;
    }
    
}
