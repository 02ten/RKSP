import java.util.Random;
import java.util.concurrent.*;
class FileGenerator implements Runnable {
    private BlockingQueue<File> queue;
    private Random random = new Random();

    public FileGenerator(BlockingQueue<File> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(random.nextInt(901) + 100); // Задержка от 100 до 1000 мс
                String[] fileTypes = {"XML", "JSON", "XLS"};
                String randomType = fileTypes[random.nextInt(fileTypes.length)];
                int randomSize = random.nextInt(91) + 10; // Размер файла от 10 до 100
                File file = new File(randomType, randomSize);
                queue.put(file);
                System.out.println("Сгенерирован файл: " + file.getFileType() + " размером " + file.getSize());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}