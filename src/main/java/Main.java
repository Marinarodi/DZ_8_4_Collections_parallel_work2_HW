
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    private static BlockingQueue<String> a_Queue = new ArrayBlockingQueue<>(100);
    private static BlockingQueue<String> b_Queue = new ArrayBlockingQueue<>(100);
    private static BlockingQueue<String> c_Queue = new ArrayBlockingQueue<>(100);

    private static final int Quantity_Text = 10_000;
    private static final int lenght_Text = 100_000;


    public static void main(String[] args) throws InterruptedException {

        Thread threadCountA = new Thread(() -> {
            for (int i = 0; i < Quantity_Text; i++) {
                try {
                    a_Queue.put(generateText("abc", lenght_Text));
                    b_Queue.put(generateText("abc", lenght_Text));
                    c_Queue.put(generateText("abc", lenght_Text));
                } catch (InterruptedException e) {
                    return;
                }
            }
        });
        threadCountA.start();

        new Thread(() -> {
            MaxTextABC<String, Integer> maxTextABC;
            try {
                maxTextABC = getMax(a_Queue, 'a');
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Текст в котором максимально встречается 'а' (" + maxTextABC.getCount() + "):\n" + maxTextABC.getText() + "\n");
        }).start();

        Thread threadCountB = new Thread(() -> {
            MaxTextABC<String, Integer> maxTextABC;
            try {
                maxTextABC = getMax(b_Queue, 'b');
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Текст в котором максимально встречается 'b' (" + maxTextABC.getCount() + "):\n" + maxTextABC.getText() + "\n");
        });
        threadCountB.start();

        Thread threadCountC = new Thread(() -> {
            MaxTextABC<String, Integer> maxTextABC;
            try {
                maxTextABC = getMax(c_Queue, 'c');
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Текст в котором максимально встречается 'c' (" + maxTextABC.getCount() + "):\n" + maxTextABC.getText() + "\n");
        });
        threadCountC.start();

        threadCountA.join();
        threadCountB.join();
        threadCountC.join();
    }


    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static MaxTextABC<String, Integer> getMax(BlockingQueue<String> maxABC, char abc) throws InterruptedException {
        MaxTextABC<String, Integer> maxTextABC = new MaxTextABC<>();
        maxTextABC.setCount(0);
        for (int i = 0; i < Quantity_Text; i++) {
            int maxInText = 0;
            String textABC = maxABC.take();
            char[] text = textABC.toCharArray();
            for (char s : text) {
                if (s == abc) {
                    maxInText++;
                }
            }
            if (maxInText > maxTextABC.getCount()) {
                maxTextABC.setCount(maxInText);
                maxTextABC.setText(textABC);
            }
        }
        return maxTextABC;
    }
}
