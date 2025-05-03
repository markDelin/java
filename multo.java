
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class multo extends JPanel {
    private List<LyricLine> lyrics;
    private int currentLine = 0;
    private int currentChar = 0;
    private Timer charTimer;
    private Timer lineTimer;
    private JLabel lyricLabel;
    
    public multo() {
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());
        
        // Initialize lyrics data
        lyrics = new ArrayList<>();
        lyrics.add(new LyricLine("Di mo ba ako lilisanin?", 100, 1200));
        lyrics.add(new LyricLine("Hindi pa ba sapat pagpapahirap sa 'kin?", 100, 900));
        lyrics.add(new LyricLine("Hindi na ba ma-mamamayapa?", 100, 2000));
        lyrics.add(new LyricLine("Hindi na ba ma-mamamayapa?", 100, 1000));
        
        // Create label for lyrics
        lyricLabel = new JLabel();
        lyricLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        lyricLabel.setForeground(Color.WHITE);
        add(lyricLabel);
        
        // Timer for character-by-character display
        charTimer = new Timer(lyrics.get(currentLine).charDelay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentChar < lyrics.get(currentLine).text.length()) {
                    String displayedText = "<html><center><b>" + 
                        lyrics.get(currentLine).text.substring(0, currentChar + 1) + 
                        "</b></center></html>";
                    lyricLabel.setText(displayedText);
                    currentChar++;
                } else {
                    charTimer.stop();
                    // Start line timer to wait before next line
                    lineTimer.setInitialDelay(lyrics.get(currentLine).lineDelay);
                    lineTimer.start();
                }
            }
        });
        
        // Timer for delay between lines
        lineTimer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lineTimer.stop();
                currentLine++;
                if (currentLine < lyrics.size()) {
                    currentChar = 0;
                    lyricLabel.setText("");
                    fadeInLabel();
                    charTimer.setDelay(lyrics.get(currentLine).charDelay);
                    charTimer.start();
                }
            }
        });
        
        // Start the animation
        fadeInLabel();
        charTimer.start();
    }
    
    private void fadeInLabel() {
        lyricLabel.setForeground(new Color(255, 255, 255, 0));
        Timer fadeTimer = new Timer(30, null);
        fadeTimer.addActionListener(new ActionListener() {
            float alpha = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                alpha += 0.05f;
                if (alpha >= 1) {
                    alpha = 1;
                    fadeTimer.stop();
                }
                lyricLabel.setForeground(new Color(255, 255, 255, (int)(alpha * 255)));
            }
        });
        fadeTimer.start();
    }
    
    private class LyricLine {
        String text;
        int charDelay; // milliseconds per character
        int lineDelay; // milliseconds after line completes
        
        public LyricLine(String text, int charDelay, int lineDelay) {
            this.text = text;
            this.charDelay = charDelay;
            this.lineDelay = lineDelay;
        }
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Lyrics Player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        
        multo player = new multo();
        frame.add(player);
        
        frame.setVisible(true);
    }
}
