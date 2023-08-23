import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingThreadExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Swing Thread Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);

            JButton startButton = new JButton("Start Thread");
            JTextArea textArea = new JTextArea();

            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // When the button is clicked, create and start a new thread
                    Thread workerThread = new WorkerThread(textArea);
                    workerThread.start();
                }
            });

            JPanel panel = new JPanel();
            panel.add(startButton);

            frame.add(panel, BorderLayout.NORTH);
            frame.add(new JScrollPane(textArea), BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }
}

class WorkerThread extends Thread {
    private JTextArea textArea;
    int i= 0;

    public WorkerThread(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void run() {
        for (i = 1; i <= 10; i++) {
            // Simulate some work
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Update the UI with the current progress
            SwingUtilities.invokeLater(() -> {
                textArea.append("Task " + i + " completed\n");
            });
        }
    }
}

