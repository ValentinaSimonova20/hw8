package org.example;


import java.util.List;

public class HomeWork {

    /**
     * <h1>Задание 1.</h1>
     * Решить задачу
     * <a href="https://acm.timus.ru/problem.aspx?space=1&num=1316">https://acm.timus.ru/problem.aspx?space=1&num=1316</a>
     */
    public Double getProfit(List<String> actionList) {
        Treap treap = new Treap();
        Double result = 0.0;
        int currentCustomerIndex = 0;
        for (String action : actionList) {
            String[] actionArray = action.split(" ");
            switch (actionArray[0]) {
                case "BID": {
                    treap.add(Double.valueOf(actionArray[1]), currentCustomerIndex);
                    currentCustomerIndex++;
                    break;
                }
                case "DEL": {
                    treap.remove(Double.valueOf(actionArray[1]));
                    break;
                } case "SALE": {
                    result += treap.getProfit(Integer.parseInt(actionArray[2]), Double.valueOf(actionArray[1]));
                    break;
                }
            }

        }
        return result;
    }

    /**
     * <h1>Задание 2.</h1>
     * Решить задачу <br/>
     * <a href="https://informatics.msk.ru/mod/statements/view.php?id=1974&chapterid=2782#1">https://informatics.msk.ru/mod/statements/view.php?id=1974&chapterid=2782#1</a><br/>
     */
    public List<Integer> getLeaveOrder(List<String> actionList) {
        return null;
    }

}
