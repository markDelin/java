import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class multo extends JPanel {
    private List<LyricLine> lyrics;
    private int currentLine = 0;
    private int currentChar = 0;
    private Timer charTimer;
    private Timer lineTimer;
    private JLabel lyricLabel;
    private Random random = new Random();
    private Color currentColor = Color.WHITE;
    
    public multo() {
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());
        
        // Initialize lyrics data
        lyrics = new ArrayList<>();
        lyrics.add(new LyricLine("Di mo ba ako lilisanin?", 80, 1200));
        lyrics.add(new LyricLine("Hindi pa ba sapat pagpapahirap sa 'kin?", 70, 900));
        lyrics.add(new LyricLine("Hindi na ba ma-mamamayapa?", 90, 2000));
        lyrics.add(new LyricLine("Hindi na ba ma-mamamayapa?", 100, 1000));
        
        // Create label for lyrics
        lyricLabel = new JLabel();
        lyricLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        lyricLabel.setForeground(currentColor);
        add(lyricLabel);
        
        // Timer for character-by-character display WITH ANIMATION
        charTimer = new Timer(lyrics.get(currentLine).charDelay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentChar < lyrics.get(currentLine).text.length()) {
                    // Display text character by character
                    String displayedText = lyrics.get(currentLine).text.substring(0, currentChar + 1);
                    lyricLabel.setText(displayedText);
                    
                    // Animation for each character
                    animateCharacterAppearance();
                    
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
                    changeTextColor(); // Change color for new line
                    charTimer.setDelay(lyrics.get(currentLine).charDelay);
                    charTimer.start();
                }
            }
        });
        
        // Start the animation
        changeTextColor();
        charTimer.start();
    }
    
    private void animateCharacterAppearance() {
        // Scale animation
        final int originalSize = 28;
        lyricLabel.setFont(new Font("SansSerif", Font.BOLD, originalSize + 5));
        
        Timer scaleTimer = new Timer(50, new ActionListener() {
            int count = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                count++;
                int newSize = originalSize + 5 - count * 2;
                if (newSize < originalSize) newSize = originalSize;
                
                lyricLabel.setFont(new Font("SansSerif", Font.BOLD, newSize));
                
                if (count >= 3) {
                    ((Timer)e.getSource()).stop();
                }
            }
        });
        scaleTimer.start();
        
        // Color flash animation
        Color originalColor = lyricLabel.getForeground();
        lyricLabel.setForeground(new Color(
            Math.min(255, originalColor.getRed() + 50),
            Math.min(255, originalColor.getGreen() + 50),
            Math.min(255, originalColor.getBlue() + 50)
        ));
        
        Timer colorTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lyricLabel.setForeground(originalColor);
                ((Timer)e.getSource()).stop();
            }
        });
        colorTimer.start();
    }
    
    private void changeTextColor() {
        Color[] colors = {
            new Color(255, 100, 100), // light red
            new Color(100, 255, 100), // light green
            new Color(100, 100, 255), // light blue
            new Color(255, 255, 100), // yellow
            new Color(255, 100, 255)  // pink
        };
        
        currentColor = colors[random.nextInt(colors.length)];
        lyricLabel.setForeground(currentColor);
        
        // Fade in effect for new line
        lyricLabel.setForeground(new Color(
            currentColor.getRed(),
            currentColor.getGreen(),
            currentColor.getBlue(),
            0
        ));
        
        Timer fadeTimer = new Timer(30, new ActionListener() {
            int alpha = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                alpha += 15;
                if (alpha >= 255) {
                    alpha = 255;
                    ((Timer)e.getSource()).stop();
                }
                lyricLabel.setForeground(new Color(
                    currentColor.getRed(),
                    currentColor.getGreen(),
                    currentColor.getBlue(),
                    alpha
                ));
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
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        
        multo player = new multo();
        frame.add(player);
        
        frame.setVisible(true);
    }
}
