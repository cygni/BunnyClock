import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


class Counter extends Thread {

    JLabel dateLabel;
    JLabel timeLabel;

    public Counter(JLabel dateLabel, JLabel timeLabel) {
        this.dateLabel = dateLabel;
        this.timeLabel = timeLabel;
    }

    public void run() {

        DateTimeFormatter dateFormatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy");

        DateTimeFormatter timeFormatter =
                DateTimeFormatter.ofPattern("HH:mm:ss");

        while (true) {

            LocalDateTime currentDateTime = LocalDateTime.now();

            dateLabel.setText(currentDateTime.format(dateFormatter));
            timeLabel.setText(currentDateTime.format(timeFormatter));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // handle interruption
            }
        }
    }
}


public class watch {

    public static void main(String[] args) {

        JFrame frame = new JFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setUndecorated(true);


        // BACKGROUND IMAGE

        Image backgroundImage =
                        new ImageIcon(watch.class.getResource("/bunnybg.png")).getImage();


        // MAKE IMAGE 80% OF ORIGINAL SIZE

        int width =
                (int) (backgroundImage.getWidth(null) * 0.5);

        int height =
                (int) (backgroundImage.getHeight(null) * 0.5);

        frame.setSize(width, height);


       
        // BACKGROUND PANEL

        JPanel backgroundPanel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                g.drawImage(
                        backgroundImage,
                        0,
                        0,
                        getWidth(),
                        getHeight(),
                        this
                );
            }
        };

        backgroundPanel.setOpaque(false);


        // TRANSPARENT WINDOW

        frame.setBackground(
                new Color(0, 0, 0, 0)
        );

        frame.setContentPane(backgroundPanel);

        final Point mouseDownCompCoords = new Point();

        backgroundPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords.setLocation(e.getPoint());
            }
        });

        backgroundPanel.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();

                frame.setLocation(
                        currCoords.x - mouseDownCompCoords.x,
                        currCoords.y - mouseDownCompCoords.y
                );
            }
        });

        frame.setLocationRelativeTo(null);

        // CUSTOM FONT

        Font customFont = null;

        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, watch.class.getResourceAsStream("/04b_30/04B_30__.TTF"));

            customFont = customFont.deriveFont(Font.BOLD, 40);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // DATE

        JLabel dateLabel = new JLabel();

        dateLabel.setHorizontalAlignment(
                JLabel.CENTER
        );

        dateLabel.setVerticalAlignment(
                JLabel.CENTER
        );

        dateLabel.setFont(customFont.deriveFont(Font.BOLD, 18));

        dateLabel.setForeground(
                Color.decode("#ff5c89")
        );


        // TIME

        JLabel timeLabel = new JLabel();

        timeLabel.setHorizontalAlignment(
                JLabel.CENTER
        );

        timeLabel.setVerticalAlignment(
                JLabel.CENTER
        );

        timeLabel.setFont(customFont.deriveFont(Font.BOLD, 33));

        timeLabel.setForeground(
                Color.decode("#ff5c89")
        );


        // INDIVIDUAL DATE/TIME POSITION

        int dateShift = 0;
        int timeShift = 0;

        dateLabel.setBorder(
                javax.swing.BorderFactory.createEmptyBorder(
                        0,
                        dateShift < 0 ? -dateShift : 0,
                        0,
                        dateShift > 0 ? dateShift : 10
                )
        );

        timeLabel.setBorder(
                javax.swing.BorderFactory.createEmptyBorder(
                        0,
                        timeShift < 0 ? -timeShift : 0,
                        6,
                        timeShift > 0 ? timeShift : 13
                )
        );


        // CLOCK PANEL

        JPanel clockPanel = new JPanel();

        clockPanel.setLayout(
                new BoxLayout(
                        clockPanel,
                        BoxLayout.Y_AXIS
                )
        );

        clockPanel.setOpaque(false);


        // Keep both labels centred horizontally

        dateLabel.setAlignmentX(
                JLabel.CENTER_ALIGNMENT
        );

        timeLabel.setAlignmentX(
                JLabel.CENTER_ALIGNMENT
        );


        // Add the date and time
        clockPanel.add(timeLabel);
        clockPanel.add(dateLabel);
        


        // POSITION CLOCK

        backgroundPanel.setLayout(
                new GridBagLayout()
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.anchor = GridBagConstraints.CENTER;


        // Move the entire clock downward

        gbc.insets =
                new Insets(
                        100,   // top
                        0,     // left
                        0,     // bottom
                        0      // right
                );


        backgroundPanel.add(
                clockPanel,
                gbc
        );


        // SHOW WINDOW

        frame.setVisible(true);


        Counter clock =
                new Counter(
                        dateLabel,
                        timeLabel
                );

        clock.start();
    }
}