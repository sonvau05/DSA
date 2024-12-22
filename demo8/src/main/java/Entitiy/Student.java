package Entity;

public class Student {
    private String id;
    private String name;
    private double mathMark;
    private double literatureMark;
    private double englishMark;

    public Student(String id, String name, double mathMark, double literatureMark, double englishMark) {
        this.id = id;
        this.name = name;
        this.mathMark = mathMark;
        this.literatureMark = literatureMark;
        this.englishMark = englishMark;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMathMark() {
        return mathMark;
    }

    public double getLiteratureMark() {
        return literatureMark;
    }

    public double getEnglishMark() {
        return englishMark;
    }

    public double averageMark() {
        return (mathMark + literatureMark + englishMark) / 3;
    }
}
