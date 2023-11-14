import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;

public class Game extends JPanel {

    private int rightToTry = 15;
    private String[] cities = {"Antalya", "Bursa", "Malatya", "İstanbul", "Balıkesir", "İzmir", "Muğla", "Mersin", "Çanakkale", "Hakkari"};
    private Random randWord = new Random();
    private String randCity = cities[randWord.nextInt(10)];
    private char[] randCityCharacters = randCity.toCharArray();
    private String userAnswer = "";
    private JLabel[] unknownString;
    private Timer timer;
    private int timeleft = 180;
    private JTextField answer;
    private JLabel remainTime;
    private JButton button;
    private Boolean gameEnd;

    public Game() {
        remainTime = new JLabel("Kalan Süre: " + timeleft + " saniye");
        remainTime.setBounds(500, 400, 200, 30);
        add(remainTime);

        unknownString = new JLabel[randCity.length()]; // Rastgele şehir adı için " _ " ekleme
        for (int i = 0; i < randCity.length(); i++) {
            unknownString[i] = new JLabel("_");
            unknownString[i].setBounds(100 + i * 50, 200, 50, 50);
            add(unknownString[i]);
        }

        answer = new JTextField("Tahmininizi girin: ");
        answer.setBounds(50, 300, 50, 200);
        add(answer);

        button = new JButton("Tahmin Et");
        button.setBounds(300, 350, 90, 30);
        button.addActionListener((ActionListener) e -> {
            userAnswer = answer.getText();
            if (userAnswer.isEmpty() || userAnswer.length() > 1) {
                JOptionPane.showMessageDialog(null, "Lütfen sadece bir harf girin.");
            } else {
                checkAnswer(userAnswer.charAt(0));
            }
        });
        add(button);

        startTimer();
    }

    private void checkAnswer(char guess) {
        boolean correctGuess = false;

        for (int j = 0; j < randCity.length(); j++) {
            if (guess == randCityCharacters[j]) {
                unknownString[j].setText(String.valueOf(guess));
                correctGuess = true;
            }
        }

        if (!correctGuess) {
            rightToTry--;
            JOptionPane.showMessageDialog(null, "Bilemediniz. Kalan deneme hakkınız: " + rightToTry);
        }

        if (rightToTry == 0) {
            JOptionPane.showMessageDialog(null, "Kaybettiniz! Doğru cevap: " + randCity);
            gameEnd = false;
        }
        int ctrl=0;
        for(int i=0;i<randCity.length();i++){
            if(unknownString[i].getText().equals("_")){
                ctrl ++;
            }
        }
        if(ctrl==0){
            JOptionPane.showMessageDialog(null, "Tebrikler! Adamı kurtardınız. Doğru cevap: " + randCity  );
            gameEnd = true;
             JOptionPane.showMessageDialog(null, "Puanınız\n"+ Point(gameEnd) );
        }
        
    }

    private void startTimer() {
        timer = new Timer(1000, e -> {
            if (timeleft >= 0) {
                remainTime.setText("Kalan Süre: " + timeleft + " saniye");
                timeleft--;
            } else {
                timer.stop();
                JOptionPane.showMessageDialog(null, "Süreniz doldu! Puanınız: " + 10);
            }
        });
        timer.start();
    }

    private int Point (boolean gameEnd)
    {

        if (gameEnd) {
            if (timeleft<180 && timeleft>160 ){
                return 100;
            }
            else if(timeleft<160 && timeleft>120){
                return 80;
            }
            else if(timeleft<120 && timeleft>50){
                return 50;
            }
              else if(timeleft<50){
                return 30;
            }
           
        }
         
        return 10; 
         

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Adam Asmaca");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new Game());
            frame.setSize(800, 600);
            frame.setVisible(true);
        });
    }
}