import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class Chat_Date extends JLayeredPane {

    private JLabel lbDate;

    public Chat_Date() {
        initComponents();
    }

    public void setDate(String date) {
        lbDate.setText(date);
    }

    private void initComponents() {
        lbDate = new JLabel();
        Line line1 = new Line();
        Line line2 = new Line();

        lbDate.setForeground(new java.awt.Color(191, 191, 191));
        lbDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbDate.setText("06/06/2021");

        line1.setForeground(new java.awt.Color(191, 191, 191));
        line2.setForeground(new java.awt.Color(191, 191, 191));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(line1, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbDate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(line2, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(line2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(lbDate)
                                                .addComponent(line1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(10, 10, 10))
        );
    }
    public static class Line extends JLabel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(getForeground());
            g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
        }
    }
}
