package design_pattern.strategy;

public class AnimalPlay {

    public static void main(String[] args) {

        Animal sparky = new Dog();
        Animal tweety = new Bird();

        System.out.println("Dog: " + sparky.tryToFly()); // Dog: I can't fly

        System.out.println("Bird: " + tweety.tryToFly()); // Bird: Flying High

        // This allows dynamic changes for flyingType

        tweety.setFlyingAbility(new CantFly());

        System.out.println("Bird: " + sparky.tryToFly()); // Bird: I can't fly

    }

}