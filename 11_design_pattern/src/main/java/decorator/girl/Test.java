package decorator.girl;

/**
 * 【例1】用装饰器模式实现游戏角色“莫莉卡·安斯兰”的变身。
 *
 * 分析：在《恶魔战士》中，游戏角色“莫莉卡·安斯兰”的原身是一个可爱少女，可以变身为
 * 1.头顶及背部延伸出蝙蝠状飞翼的女妖，
 * 2.穿着漂亮外衣的少女。
 * 在本实例中的“莫莉卡”原身（Original）有 setImage(String t) 方法决定其显示方式，
 * 而其 变身(Changer) “蝙蝠状女妖”和“着装少女”可以用 setChanger() 方法来改变其外观，
 * 原身与变身后的效果用 display() 方法来显示
 */
public class Test {

    public static void main(String[] args) {
        Morrigan m0 = new Original();
        m0.display();

        Morrigan m1 = new Succubus(m0);
        m1.display();

        Morrigan m2 = new Girl(m0);
        m2.display();

        Morrigan m3 = new Girl(m0);
        m3.display();
    }

}
