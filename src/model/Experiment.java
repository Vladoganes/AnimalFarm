package model;

import java.util.Random;

public class Experiment {
    private int currentYear;
    private final int contractPeriod;
    private final double alpha = 0.7; // birthCoefficientFromAdult
    private final double beta = 0.3; // birthCoefficientFromOld
    private final double delta = 0.8; // survivalCoefficientOfYoungAnimal
    private final double ro = 0.4; // deathCoefficientOfOldAnimal
    private final Farm farm;
    private final ContractContainer contracts;

    public Experiment(
            int youngCount,
            int adultCount,
            int oldCount,
            double capital,
            int contractPeriod
    ) {
        this.currentYear = 0;
        this.contractPeriod = contractPeriod;
        this.farm = new Farm(youngCount, adultCount, oldCount, capital);
        this.contracts = new ContractContainer(contractPeriod);
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public int getContractPeriod() {
        return contractPeriod;
    }

    public boolean isExperimentStillGoingOn() {
        // Этот метод возращает true, если ферма еще НЕ банкрот
        // а также если срок контракта еще не закончился, то есть
        // эксперимент может продолжаться.
        return (!farm.isBankrupt() && currentYear < contractPeriod);
    }

    public String[] makeStepAndGetFarmInfo() {
        // Функция, которая обрабатывает полный годовой цикл эксперимента
        // и выводит строку со всей необходимой статистической информацией.
        currentYear += 1;
        if (!farm.isBankrupt()) {
            Random random = new Random();
            // Ниже случайная величина, отвечающая за неблагоприятные события
            // окружающей среды.
            double adverseEventRation = random.nextDouble(0.05, 0.2);
            return farm.makeYearModellingAndGetInfo(
                    contracts.getBy(currentYear - 1),
                    alpha,
                    beta,
                    delta,
                    ro,
                    adverseEventRation
            );
        }
        return new String[25];
    }

    public void currentYearContract(
            int youngAnimalsCountToSell,
            int adultAnimalsCountToSell,
            int oldAnimalsCountToSell,
            double youngAnimalPrice,
            double adultAnimalPrice,
            double oldAnimalPrice,
            double feedPriceToPay,
            double penalty
    ) {
        contracts.putBy(
                currentYear,
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
}
