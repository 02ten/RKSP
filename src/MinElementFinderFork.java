import java.util.concurrent.RecursiveTask;

public class MinElementFinderFork extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 10; // Пороговое значение для выполнения последовательного поиска

    private int[] arr;
    private int start;
    private int end;

    public MinElementFinderFork(int[] arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int length = end - start + 1;

        // Если размер массива меньше порогового значения, выполняем последовательный поиск минимума
        if (length <= THRESHOLD) {
            int min = arr[start];
            for (int i = start + 1; i <= end; i++) {
                if (arr[i] < min) {
                    min = arr[i];
                }
            }
            return min;
        }

        // Если размер массива больше порогового значения, разбиваем его на подзадачи
        int middle = start + (end - start) / 2;
        MinElementFinderFork leftTask = new MinElementFinderFork(arr, start, middle);
        MinElementFinderFork rightTask = new MinElementFinderFork(arr, middle + 1, end);

        // Запускаем подзадачи параллельно
        leftTask.fork();
        int rightResult = rightTask.compute();
        int leftResult = leftTask.join();

        // Находим минимум из двух результатов
        return Math.min(leftResult, rightResult);
    }
}
