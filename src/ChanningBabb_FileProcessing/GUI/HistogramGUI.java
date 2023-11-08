package ChanningBabb_FileProcessing.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ChanningBabb_FileProcessing.Histogram;

public class HistogramGUI {
    private JFrame frame;
    private JTextArea textArea;
    private JPanel panel;
    private JLabel[] labels;
    private JSlider[] sliders;

    public HistogramGUI(Histogram[] histograms) {
        frame = new JFrame("Histograms");
        textArea = new JTextArea();
        panel = new JPanel();
        labels = new JLabel[histograms.length*3]; // *3 because we will need an additional label for each histogram for the scale and the histogram itself
        sliders = new JSlider[histograms.length];
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(panel);
        frame.add(scrollPane, BorderLayout.CENTER);
        // create new label for each histogram
        for (int i = 0; i < histograms.length; i++) {
            Histogram histogram = histograms[i];
            JLabel label = new JLabel();
            JLabel scaleLabel = new JLabel();
            JLabel histogramLabel = new JLabel();
            labels[i*3] = label;
            labels[i*3+1] = scaleLabel;
            labels[i*3+2] = histogramLabel;
            label.setFont(new Font("Arial", Font.BOLD, 16));
            try {
                label.setText(histogram.getMeasuringUnit());
                scaleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
                scaleLabel.setText("Scale: " + histogram.getScale());
            } catch (NullPointerException e) {
                label.setText("Histogram error");
                scaleLabel.setText("Scale: 1");
            }
            histogramLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            histogramLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            histogramLabel.setPreferredSize(new Dimension(300, 200));
            try {
                histogramLabel.setText("<html>" + histogram.displayHistogramGUI() + "</html>");
            } catch (NullPointerException e) {
                histogramLabel.setText("Histogram error");
            }
            JPanel histogramPanel = new JPanel();
            histogramPanel.setLayout(new BorderLayout());
            histogramPanel.add(scaleLabel, BorderLayout.NORTH);
            histogramPanel.add(histogramLabel, BorderLayout.CENTER);
            histogramPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, histogramPanel.getPreferredSize().height));
            panel.add(label);
            panel.add(histogramPanel);
            panel.add(Box.createRigidArea(new Dimension(0, 10))); // add some spacing between histograms
            
            // create new slider for each histogram
            JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 25, histogram.getScale());
            slider.setMajorTickSpacing(5);
            slider.setMinorTickSpacing(1);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
            slider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    JSlider source = (JSlider)e.getSource();
                    if (!source.getValueIsAdjusting()) {
                        int scale = (int)source.getValue();
                        histogram.setScale(scale);
                        scaleLabel.setText("Scale: " + histogram.getScale());
                        try {
                            histogramLabel.setText("<html>" + histogram.displayHistogramGUI() + "</html>");
                        } catch (NullPointerException ex) {
                            histogramLabel.setText("Histogram error");
                        }
                    }
                }
            });
            sliders[i] = slider;
            panel.add(slider);
        }

        frame.setSize(800, 800);
        frame.setVisible(true);
    }

}
