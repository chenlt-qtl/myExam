package com.betta.enumDemo;

public enum B {
    X("张三") {
        @Override
        public void go() {
            System.out.println(getTitle() + "跑起来");
        }
    }, Y("李四") {
        @Override
        public void go() {
            System.out.println(getTitle() + "走起来");
        }
    };
    private String title;

    public abstract void go();

    B(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
