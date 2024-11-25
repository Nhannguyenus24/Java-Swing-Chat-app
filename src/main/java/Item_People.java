import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Font;

public class Item_People extends javax.swing.JPanel {

    private boolean mouseOver;
    private final Model_User_Account user;
    private String status;

    public Item_People(Model_User_Account user, String status) {
        this.user = user;
        this.status = status;
        initComponents();
        lb.setText(user.getUserName());
        activeStatus.setActive(user.isStatus());
        init();
    }

    private void init() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                setBackground(new Color(230, 230, 230));
                mouseOver = true;
            }

            @Override
            public void mouseExited(MouseEvent me) {
                setBackground(new Color(242, 242, 242));
                mouseOver = false;
            }
        });
    }

    private void initComponents() {
        lb = new javax.swing.JLabel();
        lbStatus = new javax.swing.JLabel();
        activeStatus = new ActiveStatus();
        btnAccept = new javax.swing.JButton();
        btnReject = new javax.swing.JButton();
        btnAddFriend = new javax.swing.JButton();

        setBackground(new java.awt.Color(242, 242, 242));

        lb.setFont(new java.awt.Font("sanserif", Font.PLAIN, 14));
        lb.setText(user.getUserName());

        lbStatus.setFont(new java.awt.Font("sanserif", Font.ITALIC, 12));
        lbStatus.setForeground(new java.awt.Color(117, 117, 117));
        switch (status) {
            case "friend":
                if (user.isStatus()) {
                    lbStatus.setText("online");
                    lbStatus.setForeground(Color.GREEN);
                }
                else {
                    lbStatus.setText("offline");
                    lbStatus.setForeground(Color.GRAY);
                }
                break;
            case "group":
                lbStatus.setText("group");
                break;
            case "notification":
                lbStatus.setText("request");
                break;
            default:
                lbStatus.setText("new user");
                break;
        }

        activeStatus.setActive(true);

        btnAccept.setText("Accept");
        btnReject.setText("Reject");
        btnAddFriend.setText("Add Friend");

        btnAccept.addActionListener(e -> onAccept());
        btnReject.addActionListener(e -> onReject());
        btnAddFriend.addActionListener(e -> onAddFriend());

        updateLayoutBasedOnStatus();
    }

    private void updateLayoutBasedOnStatus() {
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);

        switch (status) {
            case "notification":
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(lbStatus)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnAccept)
                                        .addComponent(btnReject)
                                        .addGap(5, 5, 5)
                                )
                                .addComponent(lb, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(lb)
                                                        .addGap(6, 6, 6)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(btnReject)
                                                                .addComponent(btnAccept)
                                                                .addComponent(lbStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                        .addGap(3, 3, 3))
                );
                break;

            case "public":
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(lbStatus)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnAddFriend)
                                        .addGap(5, 5, 5)
                                )
                                .addComponent(lb, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(lb)
                                                        .addGap(6, 6, 6)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(btnAddFriend)
                                                                .addComponent(lbStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                        .addGap(3, 3, 3))
                );
                break;

            default: // "friend" or "group"
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lb, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(lbStatus)
                                                .addGap(3, 3, 3)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                );

                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(lb)
                                                        .addGap(6, 6, 6)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(lbStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                        .addGap(3, 3, 3))
                );
                break;
        }
    }

    private void onAccept() {
        System.out.println("Accepted " + user.getUserName());
    }

    private void onReject() {
        System.out.println("Rejected " + user.getUserName());
    }

    private void onAddFriend() {
        System.out.println("Add friend " + user.getUserName());
    }

    private ActiveStatus activeStatus;
    private javax.swing.JLabel lb;
    private javax.swing.JLabel lbStatus;
    private javax.swing.JButton btnAccept;
    private javax.swing.JButton btnReject;
    private javax.swing.JButton btnAddFriend;

    public static class ActiveStatus extends Component {
        public void setActive(boolean active) {
            this.active = active;
            repaint();
        }

        private boolean active;

        public ActiveStatus() {
            setPreferredSize(new Dimension(8, 8));
        }

        @Override
        public void paint(Graphics g) {
            if (active) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(62, 165, 49));
                g2.fillOval(0, (getHeight() / 2) - 4, 8, 8);
            }
        }
    }
}
