package General;

import Entity.Student;
import javafx.collections.ObservableList;

public class BubbleSort {
    public static void sortByAverageMark(ObservableList<Student> students) {
        int n = students.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (students.get(j).averageMark() > students.get(j + 1).averageMark()) {
                    Student temp = students.get(j);
                    students.set(j, students.get(j + 1));
                    students.set(j + 1, temp);
                }
            }
        }
    }

}
