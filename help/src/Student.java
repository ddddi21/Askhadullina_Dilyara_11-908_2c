import java.util.Comparator;
import java.util.PriorityQueue;

public class Student {
    int age;
    int course;
    String name;

     static Comparator comparator_age = (o1, o2) -> {
         if (o1 instanceof Student && o2 instanceof Student) {
             return Integer.compare(((Student) o2).age, ((Student) o1).age);
         }
         return 0;
     };

    static Comparator comparator_course = (o1, o2) -> {
        if (o1 instanceof Student && o2 instanceof Student) {
            return Integer.compare(((Student) o2).course, ((Student) o1).course);
        }
        return 0;
    };

    static Comparator comparator_name = (o1, o2) -> {
        if (o1 instanceof Student && o2 instanceof Student) {
            return ((Student) o1).name.compareTo(((Student) o2).name);
        }
        return 0;
    };

    @Override
    public String toString() {
        return name + " is " + age + " old and at " + course + " course";
    }

    public static void main(String[] args) {
        Student s1 = new Student();
        s1.age = 20;
        s1.course = 3;
        s1.name = "Bob";

        Student s2 = new Student();
        s2.age = 18;
        s2.course = 5;
        s2.name = "Ada";

        Student s3 = new Student();
        s3.age = 30;
        s3.course = 2;
        s3.name = "Da";

        PriorityQueue priorityQueue_age = new PriorityQueue(comparator_age);
        PriorityQueue priorityQueue_course = new PriorityQueue(comparator_course);
        PriorityQueue priorityQueue_name = new PriorityQueue(comparator_name);

        priorityQueue_age.add(s1);
        priorityQueue_age.add(s2);
        priorityQueue_course.add(s1);
        priorityQueue_course.add(s2);
        priorityQueue_name.add(s1);
        priorityQueue_name.add(s2);
        priorityQueue_age.add(s3);
        priorityQueue_course.add(s3);
        priorityQueue_name.add(s3);

        for (int i = 0; i <3 ; i++) {
            System.out.println("age: " + priorityQueue_age.poll());
        }

        for (int i = 0; i <3 ; i++) {
            System.out.println("course: " + priorityQueue_course.poll());
        }

        for (int i = 0; i <3 ; i++) {
            System.out.println("name: " + priorityQueue_name.poll());
        }
    }
}
