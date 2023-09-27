import java.util.Random;
import java.util.concurrent.*;
class FileProcessor implements Runnable {
    private BlockingQueue<File> queue;
    private String fileType;

    public FileProcessor(BlockingQueue<File> queue, String fileType) {
        this.queue = queue;
        this.fileType = fileType;
    }

    @Override
    public void run() {
        while (true) {
            try {
                File file = queue.take();
                if (file.getFileType().equals(fileType)) {
                    long processingTime = file.getSize() * 7L; // Время обработки файла
                    Thread.sleep(processingTime);
                    System.out.println("Обработан файл типа " + fileType + " размером " + file.getSize());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}