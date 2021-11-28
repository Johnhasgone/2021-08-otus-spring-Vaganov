import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        int [] arr = {0, 2, 2, 3, 4, 1000, 1, 1};
        getDuplicateNum(arr).ifPresentOrElse(System.out::println, () -> System.out.println("Дубликат не найден"));
    }

    private static Optional<Integer> getDuplicateNum(int[] array) {
        if (array == null) {
            return Optional.empty();
        }
        Integer duplicate = null;
        doubleLoop:
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j ++) {
                if (array[i] == array[j]) {
                    duplicate = array[i];
                    break doubleLoop;
                }
            }
        }

//        Set<Integer> set = new HashSet<>();
//        for (int j : array) {
//            if (set.contains(j)) {
//                duplicate = j;
//                break;
//            }
//            set.add(j);
//        }
        return Optional.ofNullable(duplicate);
    }
}