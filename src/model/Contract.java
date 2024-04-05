package model;

public class Contract {
    // Сумма, на которую ферма обязуется закупить корма
    private final double feedPriceForAdultToPay;

    // Ниже количество животных всех возрастов,
    // которых должна продать ферма по истечении срока этого контракта
    private final int youngAnimalsCountToSell;
    private final int adultAnimalsCountToSell;
    private final int oldAnimalsCountToSell;

    // Ниже цены продажи животных соответстующих возрастов,
    // установленные контрактом на этот год
    private final double youngAnimalPrice;
    private final double adultAnimalPrice;
    private final double oldAnimalPrice;

    // Неустойка, которую платит ферма, если не выполняет контракт
    private final double penalty;

    public Contract(
            int youngAnimalsCountToSell,
            int adultAnimalsCountToSell,
            int oldAnimalsCountToSell,
            double youngAnimalPrice,
            double adultAnimalPrice,
            double oldAnimalPrice,
            double feedPriceForAdultToPay,
            double penalty
    ) {
        this.feedPriceForAdultToPay = feedPriceForAdultToPay;
        this.youngAnimalsCountToSell = youngAnimalsCountToSell;
        this.adultAnimalsCountToSell = adultAnimalsCountToSell;
        this.oldAnimalsCountToSell = oldAnimalsCountToSell;
        this.youngAnimalPrice = youngAnimalPrice;
        this.adultAnimalPrice = adultAnimalPrice;
        this.oldAnimalPrice = oldAnimalPrice;
        this.penalty = penalty;
    }


    public double getFeedPriceForAdultToPay() {
        return feedPriceForAdultToPay;
    }

    public int getYoungAnimalsCountToSell() {
        return youngAnimalsCountToSell;
    }

    public int getAdultAnimalsCountToSell() {
        return adultAnimalsCountToSell;
    }

    public int getOldAnimalsCountToSell() {
        return oldAnimalsCountToSell;
    }

    public double getYoungAnimalPrice() {
        return youngAnimalPrice;
    }

    public double getAdultAnimalPrice() {
        return adultAnimalPrice;
    }

    public double getOldAnimalPrice() {
        return oldAnimalPrice;
    }

    public double getPenalty() {
        return penalty;
    }
}
