package com.betta.d20_collection;

import java.util.*;

/**
 * 总共有54张牌
 * 点数:"3" "4" "5" "6" "7" "8" "9" "10" "J” "Q" "K" "A" "2
 * 花色:"♠" "♥" "♦" "♣"
 * 大小王:♀♂
 * 斗地主:发出51张牌，剩下3张做为底牌，
 */
public class PokerDemo {
    public static void main(String[] args) {
        Room room = new Room();
        room.start();

    }
}

class Room {

    private List<Card> allCard = new ArrayList<>();

    public Room() {
        //准备一副牌
        String[] numbers = {"3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "2"};
        String[] colors = {"♠", "♥", "♦", "♣"};
        for (int i = 0; i < numbers.length; i++) {
            for (String color : colors) {
                allCard.add(new Card(numbers[i], color, i + 1));
            }
        }

        //大小王♀♂
        allCard.add(new Card("", "♀", numbers.length + 1));
        allCard.add(new Card("", "♂", numbers.length + 2));

        System.out.println("新牌:" + allCard);
    }

    public void start() {
        //洗牌
        Collections.shuffle(allCard);
        System.out.println("洗牌后：" + allCard);

        //发牌
        List<Card> player1 = new ArrayList<>();
        List<Card> player2 = new ArrayList<>();
        List<Card> player3 = new ArrayList<>();

        for (int i = 0; i < allCard.size() - 3; i++) {
            if (i % 3 == 0) {
                player1.add(allCard.get(i));
            }
            if (i % 3 == 1) {
                player2.add(allCard.get(i));
            }
            if (i % 3 == 2) {
                player3.add(allCard.get(i));
            }
        }

        //地主得到底牌
        player2.addAll(allCard.subList(allCard.size() - 3, allCard.size()));

        sortCards(player1);
        sortCards(player2);
        sortCards(player3);
        System.out.println(player1.size() + ":" + player1);
        System.out.println(player2.size() + ":" + player2);
        System.out.println(player3.size() + ":" + player3);
    }

    /**
     * 对牌进行排序
     */
    public void sortCards(List<Card> list) {
        Collections.sort(list, (o1, o2) -> o1.getLevel() - o2.getLevel());
    }

}

class Card {
    /**
     * 名字
     */
    private String name;
    /**
     * 花色
     */
    private String color;

    /**
     * 等级，越大等级越高
     */
    private int level;

    public Card(String name, String color, int level) {
        this.name = name;
        this.color = color;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return name + color + level;
    }
}