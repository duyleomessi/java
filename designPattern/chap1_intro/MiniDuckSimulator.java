public class MiniDuckSimulator {
    public static void main(String[] args) {
        Duck mallard = new MallardDuck();
        mallard.display();
        mallard.performFly();
        mallard.performQuack();
        System.out.println("");

        Duck vietNamDuck = new VietNamDuck();
        vietNamDuck.display();
        vietNamDuck.performFly();
        vietNamDuck.performQuack();
        System.out.println("");

        Duck strategyDuck = new StrategyDuck();
        strategyDuck.display();
        strategyDuck.setFlyBehavior(new FlyWithWings());
        strategyDuck.performFly();
        strategyDuck.setQuackBehavior(new MuteQuack());
        strategyDuck.performQuack();
        System.out.println("");
    }
}