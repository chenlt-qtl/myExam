package strategy;

/**
 *
 *   * 应用实例： 1、诸葛亮的锦囊妙计，每一个锦囊就是一个策略。
 *   2、旅行的出游方式，选择骑自行车、坐汽车，每一种旅行方式都是一个策略。
 *   3、JAVA AWT 中的 LayoutManager。
 *
 * 优点： 1、算法可以自由切换。 2、避免使用多重条件判断。 3、扩展性良好。
 *
 * 缺点： 1、策略类会增多。 2、所有策略类都需要对外暴露。
 */
public class Test {
    public static void main(String[] args) {
        Context context1 = new Context(new Add());
        System.out.println("1+2="+context1.exec(1,2));

        Context context2 = new Context(new Sub());
        System.out.println("8-2="+context2.exec(8,2));
    }
}
