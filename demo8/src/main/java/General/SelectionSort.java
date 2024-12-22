package General;

import Entity.Student;

import java.util.List;

public class SelectionSort {
    public static void sortByAverageMark(List<Student> students) {
        int n = students.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (students.get(j).averageMark() < students.get(minIndex).averageMark()) {
                    minIndex = j;
                }
            }
            Student temp = students.get(minIndex);
            students.set(minIndex, students.get(i));
            students.set(i, temp);
        }
    }
}
