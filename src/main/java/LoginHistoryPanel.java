import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LoginHistoryPanel extends JPanel {
    private JTable loginHistoryTable;
    private DefaultTableModel model;

    public LoginHistoryPanel() {
        setLayout(new BorderLayout());

        model = createTableModel();
        loginHistoryTable = new JTable(model);
        loginHistoryTable.setAutoCreateRowSorter(true);

        add(new JScrollPane(loginHistoryTable), BorderLayout.CENTER);
    }

    private DefaultTableModel createTableModel() {
        String[] columns = {"Thời gian", "Tên đăng nhập", "Họ tên"};
        Object[][] data = {
                {"2024-11-15 08:00:00", "hollow_knight", "Hollow Knight"},
                {"2024-11-15 09:15:00", "silksong", "Silksong"},
                {"2024-11-14 17:30:00", "sealed_vessel", "Sealed Vessel"},
                {"2024-11-13 11:45:00", "isma_grove", "Isma's Grove"},
                {"2024-11-12 14:20:00", "dryya_stand", "Dryya's Stand"},
                {"2024-11-11 16:10:00", "hegemol_watch", "Hegemol's Watch"},
                {"2024-11-10 19:30:00", "ogrim_valor", "Ogrim's Valor"},
                {"2024-11-09 13:00:00", "zemer_lament", "Ze'mer's Lament"},
                {"2024-11-08 09:50:00", "knight_ascent", "Knight's Ascent"},
                {"2024-11-07 15:40:00", "hornet_trial", "Hornet's Trial"},
        };

        return new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
    }
}
