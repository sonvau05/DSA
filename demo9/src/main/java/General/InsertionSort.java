package General;

import Entity.Student;

import java.util.List;

public class InsertionSort {
    public static void sortByAverageMark(List<Student> students) {
        int n = students.size();
        for (int i = 1; i < n; i++) {
            Student key = students.get(i);
            int j = i - 1;
            while (j >= 0 && students.get(j).averageMark() > key.averageMark()) {
                students.set(j + 1, students.get(j));
                j = j - 1;
            }
            students.set(j + 1, key);
        }
    }
}
