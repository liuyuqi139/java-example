package feature;

/**
 *
 */
public interface InterfaceTest {
    default void eat() {
        System.out.println("吃饭");
    }

    void sleep();

    void study();

    abstract class Person implements InterfaceTest {
        abstract void play();

        @Override
        public void sleep() {
            System.out.println("睡觉");
        }
    }

    class Man extends Person {
        @Override
        public void study() {
            System.out.println("男人学习");
        }

        @Override
        void play() {
            System.out.println("男人玩");
        }
    }

    static void main(String[] args) {
        Person person = new Man();
        person.sleep();
        person.eat();
        person.study();
        person.play();
        Man man = new Man();
        man.sleep();
        man.eat();
        man.study();
        man.play();
    }
}
