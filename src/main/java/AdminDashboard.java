import javax.swing.*;

public class AdminDashboard {

    public AdminDashboard() {
        JFrame frame = new JFrame();
        frame.setTitle("Phân hệ dành cho người quản trị");
        frame.setSize(960, 540);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Quản lý người dùng", new UserManagementPanel());
        tabbedPane.addTab("Lịch sử đăng nhập", new LoginHistoryPanel());
        tabbedPane.addTab("Nhóm chat", new GroupChatPanel());
        tabbedPane.addTab("Báo cáo spam", new SpamReportPanel());
        tabbedPane.addTab("Người dùng đăng ký mới", new NewUserRegistrationPanel());
        tabbedPane.addTab("Biểu đồ người đăng ký mới", new RegistrationChartPanel());
        tabbedPane.addTab("Danh sách bạn bè", new UserFriendListPanel());
        tabbedPane.addTab("Người dùng hoạt động", new ActiveUserPanel());
        tabbedPane.addTab("Biểu đồ người hoạt động", new ActiveUserChartPanel());
        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new AdminDashboard();
    }
}
