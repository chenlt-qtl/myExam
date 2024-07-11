package com.betta.atm;

import java.util.*;

public class Atm {

    private List<Account> accountList = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    private Account current;

    /**
     * ATM启动方法，展示欢迎界面
     */
    public void start() {

        while (true) {
            System.out.println("===欢迎使用ATM系统===");
            System.out.println("1. 用户登录");
            System.out.println("2. 用户开户");
            System.out.println("3. 退出系统");
            System.out.println("请输入命令:");
            int command = scanner.nextInt();
            switch (command) {
                case 1:
                    login();
                    break;
                case 2:
                    createAccount();
                    break;
                case 3:
                    System.out.println("再见");
                    return;
                default:
                    System.out.println("没有该操作~~~~");
            }
        }
    }

    /**
     * 用户开户功能
     * 就是新增一个账户，也就是往系统的账户集合中添加一个账户对象。
     * 账户的要求
     * 用户信息包含:姓名、性别、密码、每次取现额度、卡号。
     * <p>
     * 注意:卡号由系统生成，要求是8位的数字组成的(且卡号不能重复)
     */
    private void createAccount() {
        System.out.println("==系统开户操作==");
        Account account = new Account();
        System.out.println("请输入帐号名称:");
        String name = scanner.next();
        account.setName(name);
        while (true) {
            System.out.println("请输入性别:");
            char sex = scanner.next().charAt(0);
            if (sex == '男' || sex == '女') {
                account.setSex(sex);
                break;
            } else {
                System.out.println("您输入的性别有误，请输入'男'或者'女'：");
            }
        }
        while (true) {
            System.out.println("请您输入您的帐户密码");
            String psd = scanner.next();
            System.out.println("请再次帐户密码");
            String psd2 = scanner.next();
            if (!psd2.equals(psd)) {
                System.out.println("两次密码不一致");
            } else {
                account.setPsd(psd);
                break;
            }
        }

        System.out.println("请您输入您的取现额度");
        double limit = scanner.nextDouble();
        account.setLimit(limit);

        //为帐户生成一个卡号
        account.setId(genId());
        //把帐户存到集合中去
        accountList.add(account);
        System.out.println("恭喜您！" + account.getName() + "开户成功，您的卡号是" +
                account.getId());
    }

    /**
     * 生成8位卡号，卡号不能重复
     *
     * @return
     */
    private String genId() {
        while (true) {
            StringBuffer stringBuffer = new StringBuffer();
            Random random = new Random();
            for (int i = 0; i < 4; i++) {
                int nextInt = random.nextInt(10);
                stringBuffer.append(nextInt);
            }
            String result = stringBuffer.toString();
            if (Objects.isNull(getAccount(result))) {
                return result;
            }
        }
    }

    private void login() {
        System.out.println("==系统登录操作==");
        if (accountList.size() <= 0) {
            System.out.println("您好，当前系统无帐户，请先开户。");
            return;
        }
        while (true) {
            System.out.println("请您输入登录卡号:");
            String id = scanner.next();
            Account account = getAccount(id);
            if (Objects.isNull(account)) {
                System.out.println("系统中不存在该帐户卡号");
            } else {
                while (true) {
                    System.out.println("请输入密码");
                    String pwd = scanner.next();
                    if (!pwd.equals(account.getPsd())) {
                        System.out.println("您输入的登录密码有误");
                    } else {
                        current = account;
                        System.out.println("恭喜您," + account.getName() + ",您已进入系统，" +
                                "您的卡号是:" + account.getId());
                        showUserCommand();
                        return;
                    }
                }
            }

        }
    }

    private void showUserCommand(){
        while (true) {
            System.out.println(current.getName()+",请选择要操作的功能");
            System.out.println("1.查询帐户");
            System.out.println("2.取款");
            System.out.println("3.存款");
            System.out.println("4.转帐");
            System.out.println("5.修改密码");
            System.out.println("6.退出");
            System.out.println("7.注销用户");
            System.out.println("请输入命令:");
            int command = scanner.nextInt();
            switch (command){
                case 1:
                    printAccount();
                    break;
                case 2:
                    drawMoney();
                    break;
                case 3:
                    depositMoney();
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    System.out.println(current.getName()+",您退出系统成功");
                    current = null;
                    return;
                case 7:
                    break;
                default:
                    System.out.println("您输入的命令有误");
            }
        }

    }

    /**
     * 取款
     */
    private void drawMoney() {
        System.out.println("==取款服务==");
        if(current.getMoney()<=0){
            System.out.println("无法取款，帐户余额为0");
            return;
        }
        while (true) {
            System.out.println("请输入要取出的金额：");
            double money = scanner.nextDouble();
            if(money>current.getMoney()){
                System.out.println("余额不足，您帐户的余额是："+current.getMoney());
            }else if(money>current.getLimit()){
                System.out.println("超过限额, 您每次最多可取"+current.getLimit());
            }else{
                current.setMoney(current.getMoney()-money);
                System.out.println("您取款"+money+"成功!现在余额是："+current.getMoney());
                return;
            }
        }
    }

    //存款
    private void depositMoney() {
        System.out.println("==存款服务==");
        System.out.println("请输入要存的金额：");
        double money = scanner.nextDouble();
        current.setMoney(current.getMoney()+money);
        System.out.println("恭喜您，存款成功!现在余额是："+current.getMoney());
    }

    /**
     * 打印帐户信息
     */
    private void printAccount() {
        System.out.println("====当前您的帐户信息如下====");
        System.out.println("卡号："+current.getId());
        System.out.println("帐号名称："+current.getName());
        System.out.println("性别："+current.getSex());
        System.out.println("余额："+current.getMoney());
        System.out.println("取现额度："+current.getLimit());
    }

    /**
     * 根据ID查询帐号
     *
     * @param id
     * @return
     */
    private Account getAccount(String id) {
        for (Account account : accountList) {
            if (account.getId().equals(id)) {
                return account;
            }
        }
        return null;
    }
}
