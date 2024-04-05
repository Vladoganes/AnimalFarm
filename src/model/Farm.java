package model;

import java.text.DecimalFormat;

public class Farm {
    private int youngCount;
    private int adultCount;
    private int oldCount;
    private double capital;
    private boolean isBankrupt;
    private double currentYearProfit;
    private double currentYearCost;
    private Contract currentContract;

    public Farm(int youngCount, int adultCount, int oldCount, double capital) {
        this.youngCount = youngCount;
        this.adultCount = adultCount;
        this.oldCount = oldCount;
        this.capital = capital;
        this.currentContract = null;
        this.isBankrupt = false;
    }

    public int getYoungCount() {
        return youngCount;
    }

    public void setYoungCount(int youngCount) {
        this.youngCount = youngCount;
    }

    public int getAdultCount() {
        return adultCount;
    }

    public void setAdultCount(int adultCount) {
        this.adultCount = adultCount;
    }

    public int getOldCount() {
        return oldCount;
    }

    public void setOldCount(int oldCount) {
        this.oldCount = oldCount;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public void setCurrentContract(Contract currentContract) {
        this.currentContract = currentContract;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

    private int nextYoungGeneration(
            double birthCoefficientFromAdult,
            double birthCoefficientFromOld
    ) {
        // Функция, которая по входным параметрам определяет
        // новое число молодняка на ферме
        double alpha = birthCoefficientFromAdult;
        double beta = birthCoefficientFromOld;
        return (int)(alpha * adultCount + beta * oldCount);
    }

    private int nextAdultGeneration(double survivalCoefficientOfYoungAnimal) {
        // Функция, которая по входным параметрам определяет
        // новое число взрослых животных на ферме
        double delta = survivalCoefficientOfYoungAnimal;
        return (int)(delta * youngCount);
    }

    private int nextOldGeneration(double deathCoefficientOfOldAnimal) {
        // Функция, которая по входным параметрам определяет
        // новое число старых животных на ферме
        double ro = deathCoefficientOfOldAnimal;
        return (int) (adultCount + (1.0 - ro) * oldCount);
    }

    private double countFeedPrice(double yearFeedPriceForAdult) {
        // Функция, которая возвращает на какую сумму нужно закупить
        // корм для всех животных на ферме
        double p = yearFeedPriceForAdult;
        return (p * ( (youngCount / 2.0) + (double) adultCount + (oldCount / 3.0)));
    }

    public boolean isEnoughMoneyForFeed(double yearFeedPriceForAdult) {
        // Функция, которая возвращает хватит ли ферме денег, чтобы
        // купить весь корм, который нужен для животных
        return (countFeedPrice(yearFeedPriceForAdult) <= capital);
    }

    public int[] payFeedPrice() {
        // Функция, которая выплачивает деньги за корм для животных
        // по текущему контракту.
        // Если денег не хватает на весь корм, то происходит падежь
        // скота.
        // Функция возвращает число животных, которые погибли из-за
        // процесса кормления.
        int[] result = {0, 0, 0};
        double feedPrice = countFeedPrice(currentContract.getFeedPriceForAdultToPay());
        if (isEnoughMoneyForFeed(currentContract.getFeedPriceForAdultToPay())) {
            currentYearCost += feedPrice;
            capital -= feedPrice;
        } else {
            // Корма на всех не хватило, должен произойти пропорциональный
            // нехватке корма падеж скота.
            double lifestockRatio = 1.0 - (capital / feedPrice);
            result = doLifeStockDeath(lifestockRatio);
            currentYearCost += capital;
            feedPrice = capital;
            capital = 0.0; // заплатили столько, сколько было денег
        }
        return result;
    }

    public double payPenalty(int count) {
        // Функция, которая вызывается при невыполнении условий
        // контракта, где count - число непроданных животных.
        // Можно вызывать отдельно по каждому поколению, но можно
        // и для всех непроданных животных вместе.
        double penalty = currentContract.getPenalty();
        double resultPenalty = 0.0;
        for (int i = 0; i < count; i++) {
            resultPenalty += penalty;
        }
        if (capital < resultPenalty) {
            // Эта ситуация означает, что ферма сейчас обанкротится,
            // так как она не может выплатить всю неустойку (не хватит
            // денег). Поэтому в расходы идет не суммарная неустойка,
            // а только те деньги, которые были в денежном капитале фермы.
            currentYearCost += capital;
            capital = 0.0;
            isBankrupt = true;
        } else {
            currentYearCost += resultPenalty;
            capital -= resultPenalty;
        }
        return resultPenalty;
    }

    public int[] doLifeStockDeath(double lifestockRatio) {
        // Функция, которая выполняет частичный падеж скота
        // пропорционально для всех поколений животных и
        // возвращает количество животных каждого типа,
        // которые умерли из-за нехватки корма.
        // На позиции 0 - количество Молодых
        // на позиции 1 - количество Взрослых
        // на позиции 2 - количество Старых
        // Эта функция также вызывается и при небоагоприятных
        // событиях окружающей среды.
        int[] diedAnimals = {0, 0, 0};
        int youngDeath = (int) (youngCount * lifestockRatio);
        int adultDeath = (int) (adultCount * lifestockRatio);
        int oldDeath = (int) (oldCount * lifestockRatio);
        diedAnimals[0] = youngDeath;
        diedAnimals[1] = adultDeath;
        diedAnimals[2] = oldDeath;
        youngCount -= youngDeath;
        adultCount -= adultDeath;
        oldCount -= oldDeath;

        return diedAnimals;
    }

    public int[] makeAllNewGeneration(
            double birthCoefficientFromAdult,
            double birthCoefficientFromOld,
            double survivalCoefficientOfYoungAnimal,
            double deathCoefficientOfOldAnimal
    ) {
        // Функция, которая генерирует обновляет поколения животных
        // на ферме и возвращает статистическую информацию о генерации животных,
        // то есть возвращает количество животных каждого типа, которые
        // получились при обновлении поколения.
        // На позиции 0 - число новых Молодых животных
        // На позиции 1 - число новых Взрослых животных
        // На позиции 2 - число новых Старых животных
        // На позиции 3 - число умерших Старых животных
        int[] result = {0, 0, 0, 0};
        int newYoungCount = nextYoungGeneration(birthCoefficientFromAdult, birthCoefficientFromOld);
        result[0] = newYoungCount;
        int newAdultCount = nextAdultGeneration(survivalCoefficientOfYoungAnimal);
        result[1] = newAdultCount;
        int newOldCount = nextOldGeneration(deathCoefficientOfOldAnimal);
        result[2] = newOldCount;
        result[3] = (int) (deathCoefficientOfOldAnimal * oldCount);
        youngCount = newYoungCount;
        adultCount = newAdultCount;
        oldCount = newOldCount;

        return result;
    }

    public double[] sellAnimalsByCurrentContractAndGetInfo() {
        // Функция, которая осуществляет продажу животных
        // на ферме по текущему контракту и возвращает статистическую
        // информацию о неустойках по продаже животных.
        // На позиции 0 - неустойка по продаже Молодых
        // На поизции 1 - неустойка по продаже Взрослых
        // На позиции 2 - неустойка по продаже Старых
        double[] result = {0.0, 0.0, 0.0};

        int youngToSell = currentContract.getYoungAnimalsCountToSell();
        int adultToSell = currentContract.getAdultAnimalsCountToSell();
        int oldToSell = currentContract.getOldAnimalsCountToSell();

        capital += Math.min(youngToSell, youngCount) * currentContract.getYoungAnimalPrice() +
                Math.min(adultToSell, adultCount) * currentContract.getAdultAnimalPrice() +
                Math.min(oldToSell, oldCount) * currentContract.getOldAnimalPrice();
        currentYearProfit += Math.min(youngToSell, youngCount) * currentContract.getYoungAnimalPrice() +
                Math.min(adultToSell, adultCount) * currentContract.getAdultAnimalPrice() +
                Math.min(oldToSell, oldCount) * currentContract.getOldAnimalPrice();

        if (youngCount < youngToSell) {
            int youngCountNotSold = youngToSell - youngCount;
            result[0] = payPenalty(youngCountNotSold);
        }
        if (adultCount < adultToSell) {
            int adultCountNotSold = adultToSell - adultCount;
            result[1] = payPenalty(adultCountNotSold);
        }
        if (oldCount < oldToSell) {
            int oldCountNotSold = oldToSell - oldCount;
            result[2] = payPenalty(oldCountNotSold);
        }

        if (!isBankrupt) {
            youngCount -= Math.min(youngToSell, youngCount);
            adultCount -= Math.min(adultToSell, adultCount);
            oldCount -= Math.min(oldToSell, oldCount);
        } else {
            youngCount = 0;
            adultCount = 0;
            oldCount = 0;
        }
        return result;
    }

    public double countFullCost() {
        // Функция, которая считает полную стоимость капитала фермы.
        // То есть наличные деньги и стоимость всех животных
        // по текущему контракту.
        return (capital +
                currentContract.getYoungAnimalPrice() * youngCount +
                currentContract.getAdultAnimalPrice() * adultCount +
                currentContract.getOldAnimalPrice() * oldCount);
    }

    public String[] makeYearModellingAndGetInfo(
            Contract currentContract,
            double birthCoefficientFromAdult,
            double birthCoefficientFromOld,
            double survivalCoefficientOfYoungAnimal,
            double deathCoefficientOfOldAnimal,
            double adverseEventRation
    ) {
        // Эта функция, которая выполняет весь цикл работы фермы за год.
        // Задает контракт, по которому работает ферма, выполняет откорм,
        // частичный падеж скота, рост нового поколения, продажу животных
        // на бирже. Возвращает полную детальную информацию о работе фермы
        // за весь год в виде массива строк для передачи в таблицу.
        String[] info = new String[25];
        this.currentContract = currentContract;
        this.currentYearCost = 0.0;
        this.currentYearProfit = 0.0;
        info[0] = Integer.toString(youngCount);
        info[1] = Integer.toString(adultCount);
        info[2] = Integer.toString(oldCount);
        info[12] = new DecimalFormat("#0.00").format(
                countFeedPrice(currentContract.getFeedPriceForAdultToPay())
        );
        info[6] = new DecimalFormat("#0.00").format(capital);

        // Кормление животных
        int[] diedInFeedingAnimals = payFeedPrice();


        info[16] = Integer.toString(diedInFeedingAnimals[0]);
        info[17] = Integer.toString(diedInFeedingAnimals[1]);
        info[18] = Integer.toString(diedInFeedingAnimals[2]);

        // Генерация нового поколения
        int[] newGeneration = makeAllNewGeneration(
                birthCoefficientFromAdult,
                birthCoefficientFromOld,
                survivalCoefficientOfYoungAnimal,
                deathCoefficientOfOldAnimal
        );


        // Продажа животных по текущему контракту
        double[] allPenalties = sellAnimalsByCurrentContractAndGetInfo();


        info[22] = new DecimalFormat("#0.00").format(allPenalties[0]);
        info[23] = new DecimalFormat("#0.00").format(allPenalties[1]);
        info[24] = new DecimalFormat("#0.00").format(allPenalties[2]);


        // Смерть животных из-за природных условий
        int[] diedInAdverseEventsAnimals = doLifeStockDeath(adverseEventRation);
        info[19] = Integer.toString(diedInAdverseEventsAnimals[0]);
        info[20] = Integer.toString(diedInAdverseEventsAnimals[1]);
        info[21] = Integer.toString(diedInAdverseEventsAnimals[2]);
        info[3] = Integer.toString(youngCount);
        info[4] = Integer.toString(adultCount);
        info[5] = Integer.toString(oldCount);
        info[7] = new DecimalFormat("#0.00").format(capital);
        info[8] = new DecimalFormat("#0.00").format(currentYearProfit - currentYearCost);
        info[9] = new DecimalFormat("#0.00").format(currentYearCost);
        info[10] = new DecimalFormat("#0.00").format(countCurrentYearProfitability());
        info[11] = Boolean.toString(isBankrupt);

        info[13] = Integer.toString(newGeneration[0]);
        info[14] = Integer.toString(newGeneration[1]);
        info[15] = Integer.toString(newGeneration[2]);

        return info;
    }

    public double countCurrentYearProfitability() {
        // Эта функция выводит рентабильность фермы по
        // окончании текущего года. Рентабельность выситывается по
        // формуле:
        // Рентабельность = (Прибыль / Расходы) * 100 %.
        // То есть возвращает результат в процентах.
        return (currentYearProfit - currentYearCost) / currentYearCost * 100;
    }

    public String getCurrentYearProfitabilityInfo() {
        String info = "Рентабельность за текущий год = ";
        info += new DecimalFormat("#0.00").format(countCurrentYearProfitability());
        info += " %\n";
        info += "Чистая прибыль за этот год: " +
                new DecimalFormat("#0.00").format(currentYearProfit - currentYearCost) +
                " у.е.\n";
        info += "Расходы за этот год: " +
                new DecimalFormat("#0.00").format(currentYearCost) +
                " у.е.\n";
        return info;
    }

    public String getAllCurrentFarmInfo() {
        String info = "";
        info += "Денежный капитал: " + new DecimalFormat("#0.00").format(capital) + " у.е.\n";
        info += "Молодых животных: " + youngCount + " шт.\n";
        info += "Взрослых животных: " + adultCount + " шт.\n";
        info += "Старых животных: " + oldCount + " шт.\n";
        info += "Общий капитал фермы по контракту текущего года: " +
                new DecimalFormat("#0.00").format(countFullCost()) + " у.е.\n";
        return info;
    }
}
