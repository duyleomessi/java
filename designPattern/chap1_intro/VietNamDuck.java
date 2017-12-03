public class VietNamDuck extends Duck {
    public VietNamDuck() {
        flyBehavior = new FlyNoWay();
        quackBehavior = new MuteQuack();
    }

    public void display() {
        System.out.println("Im a viet nam duck");
    }
}