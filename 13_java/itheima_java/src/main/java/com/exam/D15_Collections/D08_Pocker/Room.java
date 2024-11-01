package com.exam.D15_Collections.D08_Pocker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Room {

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
