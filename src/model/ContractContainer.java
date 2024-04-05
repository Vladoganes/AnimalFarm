package model;

public class ContractContainer {
    private final int size;
    private final Contract[] contracts;
    ContractContainer(int period) {
        this.contracts = new Contract[period];
        this.size = period;
    }

    public void putBy(int index, Contract contract) {
        this.contracts[index] = contract;
    }

    public void putBy(
            int index,
            int youngAnimalsCountToSell,
            int adultAnimalsCountToSell,
            int oldAnimalsCountToSell,
            double youngAnimalPrice,
            double adultAnimalPrice,
            double oldAnimalPrice,
            double feedPriceToPay,
            double penalty
    ) {
        this.contracts[index] = new Contract(
                youngAnimalsCountToSell,
                adultAnimalsCountToSell,
                oldAnimalsCountToSell,
                youngAnimalPrice,
                adultAnimalPrice,
                oldAnimalPrice,
                feedPriceToPay,
                penalty
        );
    }

    public Contract getBy(int index) {
        if (index < size) {
            return contracts[index];
        }
        return null;
    }
}
