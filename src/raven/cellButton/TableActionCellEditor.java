package raven.cellButton;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 *
 * @author RAVEN
 */
public class TableActionCellEditor extends DefaultCellEditor {

    private TableActionEvent event;
    boolean isVisable = true;
    boolean isAssign = false;

    
    public TableActionCellEditor(TableActionEvent event,boolean isVisable,boolean isAssign) {
        super(new JCheckBox());
        this.event = event;
        this.isVisable = isVisable;
        this.isAssign = isAssign;

        
    }
    public TableActionCellEditor(TableActionEvent event,boolean isVisable) {
        super(new JCheckBox());
        this.event = event;
        this.isVisable = isVisable;

        
    }
        public TableActionCellEditor(boolean isAssign,TableActionEvent event) {
        super(new JCheckBox());
        this.event = event;
        this.isAssign = isAssign;
        
    }
    
    public TableActionCellEditor(TableActionEvent event) {
        super(new JCheckBox());
        this.event = event;
        isVisable = true;
        isAssign=false;
    }

    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row, int column) {
        PanelAction action = new PanelAction();
        if(isAssign){
            action.setAssign(true);
        }
        if(!isVisable){
            action.setVisible(false);
        }
        action.initEvent(event, row);
        action.setBackground(jtable.getSelectionBackground());
        return action;
    }
}
