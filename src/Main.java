import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5};

        // Создаем ForkJoinPool
        ForkJoinPool pool = new ForkJoinPool();

        // Создаем задачу для поиска минимального элемента
        MinElementFinderFork task = new MinElementFinderFork(arr, 0, arr.length - 1);

        // Запускаем задачу и получаем результат
        int min = pool.invoke(task);

        // Завершаем пул
        pool.shutdown();

        System.out.println("Минимальный элемент: " + min);
    }
    //Задание 1. Последовательное выполнение. Вариант минимальное значение в массиве.
    public static int sequentially(int[] array) {
        if (array.length == 0)
            return -1;
        int min = array[0];
        for (int i : array) {
            if (min > i) {
                min = i;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return min;
    }
    //Задание 1.2. Использование многопоточности
    public static int findMinElement(int[] arr, int numThreads) throws InterruptedException, ExecutionException {
        if (arr.length == 0) {
            throw new IllegalArgumentException("Массив пуст");
        }
        if (numThreads <= 0) {
            throw new IllegalArgumentException("Количество потоков должно быть положительным числом");
        }
        // Создаем пул потоков и список для будущих результатов
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        List<Future<Integer>> futures = new ArrayList<>();

        // Разделяем массив на части
        int chunkSize = arr.length / numThreads;
        int remainder = arr.length % numThreads;

        // Запускаем потоки для поиска минимального элемента в каждой части
        int startIndex = 0;
        for (int i = 0; i < numThreads; i++) {
            int endIndex = startIndex + chunkSize + (i == numThreads - 1 ? remainder : 0);
            int[] chunk = Arrays.copyOfRange(arr, startIndex, endIndex);

            Callable<Integer> task = () -> {
                int min = chunk[0];
                for (int j = 1; j < chunk.length; j++) {
                    if (chunk[j] < min) {
                        min = chunk[j];
                    }
                }
                return min;
            };

            Future<Integer> future = executor.submit(task);
            futures.add(future);

            startIndex = endIndex;
        }

        // Находим минимум среди результатов выполнения задач
        int min = Integer.MAX_VALUE;
        for (Future<Integer> future : futures) {
            int result = future.get();
            if (result < min) {
                min = result;
            }
        }

        // Завершаем пул потоков
        executor.shutdown();

        return min;
    }
    //Задание 2.
    public static void task2(){
        Scanner scanner = new Scanner(System.in);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        while(true){
            System.out.println("Введите число");
            String input = scanner.nextLine();
            int number = Integer.parseInt(input);
            Future<Integer> future = executorService.submit(() -> {
               Thread.sleep(ThreadLocalRandom.current().nextInt(1000,5000));
               return (int) Math.pow(number,2);
            });
            try {
                int squareNumber = future.get();
                System.out.println(squareNumber);
            }catch (InterruptedException | ExecutionException e){
                e.printStackTrace();
            }
        }

    }
}

