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

    public String payFeedPriceAndGetInfo() {
        // Функция, которая выплачивает деньги за корм для животных
        // по текущему контракту.
        // Если денег не хватает на весь корм, то происходит падеж
        // скота.
        // Функция возвращает строку, в которой подробно описывается
        // информация по откорму животных в этом году.
        String info = "";
        double feedPrice = countFeedPrice(currentContract.getFeedPriceForAdultToPay());
        info += "\nПроисходит процесс откорма животных!\n";
        info += "До откорма денег у фирмы: " +
                new DecimalFormat("#0.00").format(capital) + " у.е.\n";
        info += "Для полного откорма животных в этом году нужно потратить: " +
                new DecimalFormat("#0.00").format(feedPrice) + " у.е.\n";
        if (isEnoughMoneyForFeed(currentContract.getFeedPriceForAdultToPay())) {
            info += "Денег хватает!\n";
            currentYearCost += feedPrice;
            capital -= feedPrice;
        } else {
            double lifestockRatio = 1.0 - (capital / feedPrice);
            info += "Денег на закупку корма не хватает!\n";
            info += doLifestockDeathAndGetInfo(lifestockRatio);
            currentYearCost += capital;
            capital = 0.0; // заплатили столько, сколько было денег
        }
        info += "Наличные деньги фирмы после закупки корма составляют: " +
                new DecimalFormat("#0.00").format(capital) + " у.е.\n";
        return info;
    }

    public String payPenaltyAndGetInfo(int count) {
        // Функция, которая вызывается при невыполнении условий
        // контракта, где count - число непроданных животных.
        // Можно вызывать отдельно по каждому поколению, но можно
        // и для всех непроданных животных вместе.
        double penalty = currentContract.getPenalty();
        double resultPenalty = 0.0;
        String info = "";
        for (int i = 0; i < count; i++) {
            resultPenalty += penalty;
        }
        info += "неустойка = " + new DecimalFormat("#0.00").format(resultPenalty) + " у.е.\n";
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
        return info;
    }

    public String doLifestockDeathAndGetInfo(double lifestockRatio) {
        // Функция, которая выполняет частичный падеж скота
        // пропорционально для всех поколений животных и
        // возвращает статистическую информацию о падеже в виде строки.
        // Эта функция также вызывается и при небоагоприятных
        // событиях окружающей среды.
        String info = "";
        info += "\nПроисходит процесс пропорционального падежа скота!\n";
        info += "До падежа на ферме было:\n";
        info += "Молодняка: " + youngCount + ", " +
                "Взрослых: " + adultCount + ", " +
                "Старых: " + oldCount + ".\n";
        int youngDeath = (int) (youngCount * lifestockRatio);
        int adultDeath = (int) (adultCount * lifestockRatio);
        int oldDeath = (int) (oldCount * lifestockRatio);
        youngCount -= youngDeath;
        adultCount -= adultDeath;
        oldCount -= oldDeath;

        info += "Посе падежа на ферме стало:\n";
        info += "Молодняка: " + youngCount + ", " +
                "Взрослых: " + adultCount + ", " +
                "Старых: " + oldCount + ".\n";
        return info;
    }

    public String makeAllNewGenerationAndGetInfo(
            double birthCoefficientFromAdult,
            double birthCoefficientFromOld,
            double survivalCoefficientOfYoungAnimal,
            double deathCoefficientOfOldAnimal
    ) {
        // Функция, которая генерирует обновляет поколения животных
        // на ферме и возвращает статистическую информацию о генерации животных
        // в виде строки.
        String info = "";
        info += "\nПроисходит процесс обновления поколений всех животных!\n";
        info += "Было на ферме:\n";
        info += "Молодняка: " + youngCount + ", " +
                "Взрослых: " + adultCount + ", " +
                "Старых: " + oldCount + ".\n";
        youngCount = nextYoungGeneration(birthCoefficientFromAdult, birthCoefficientFromOld);
        adultCount = nextAdultGeneration(survivalCoefficientOfYoungAnimal);
        oldCount = nextOldGeneration(deathCoefficientOfOldAnimal);
        info += "Стало на ферме:\n";
        info += "Молодняка: " + youngCount + ", " +
                "Взрослых: " + adultCount + ", " +
                "Старых: " + oldCount + ".\n";
        return info;
    }

    public String sellAnimalsByCurrentContractAndGetInfo() {
        // Функция, которая осуществляет продажу животных
        // на ферме по текущему контракту и возвращает статистическую
        // информацию о продаже животных в виде строки.
        String info = "";
        info += "\nПроисходит процесс продажи животных!\n";
        info += "До продажи у фермы Было наличных денег: " +
                new DecimalFormat("#0.00").format(capital) + " у.е.\n\n";
        int youngToSell = currentContract.getYoungAnimalsCountToSell();
        int adultToSell = currentContract.getAdultAnimalsCountToSell();
        int oldToSell = currentContract.getOldAnimalsCountToSell();

        info += "До продажи Было на ферме:\n";
        info += "Молодняка: " + youngCount + ", " +
                "Взрослых: " + adultCount + ", " +
                "Старых: " + oldCount + ".\n";

        info += "Требуется продать:\n";
        info += "Молодняка: " + youngToSell + ", " +
                "Взрослых: " + adultToSell + ", " +
                "Старых: " + oldToSell + ".\n";

        capital += Math.min(youngToSell, youngCount) * currentContract.getYoungAnimalPrice() +
                Math.min(adultToSell, adultCount) * currentContract.getAdultAnimalPrice() +
                Math.min(oldToSell, oldCount) * currentContract.getOldAnimalPrice();
        currentYearProfit += Math.min(youngToSell, youngCount) * currentContract.getYoungAnimalPrice() +
                Math.min(adultToSell, adultCount) * currentContract.getAdultAnimalPrice() +
                Math.min(oldToSell, oldCount) * currentContract.getOldAnimalPrice();

        if (youngCount < youngToSell) {
            int youngCountNotSold = youngToSell - youngCount;
            info += "За нарушение контракта по Молодняку: ";
            info += payPenaltyAndGetInfo(youngCountNotSold);
            if (isBankrupt) {
                info += "Ферма не смогла выплатить неустойку за " +
                        "продажу Молодых животных!\n";
            }
        }
        if (adultCount < adultToSell) {
            int adultCountNotSold = adultToSell - adultCount;
            info += "За нарушение контракта по Взрослым животным: ";
            info += payPenaltyAndGetInfo(adultCountNotSold);
            if (isBankrupt) {
                info += "Ферма не смогла выплатить неустойку за " +
                        "продажу Взрослых животных!\n";
            }
        }
        if (oldCount < oldToSell) {
            int oldCountNotSold = oldToSell - oldCount;
            info += "За нарушение контракта по Старым животным: ";
            info += payPenaltyAndGetInfo(oldCountNotSold);
            if (isBankrupt) {
                info += "Ферма не смогла выплатить неустойку за " +
                        "продажу Старых животных!\n";
            }
        }

        if (!isBankrupt) {
            youngCount -= Math.min(youngToSell, youngCount);
            adultCount -= Math.min(adultToSell, adultCount);
            oldCount -= Math.min(oldToSell, oldCount);

            info += "После продажи Стало на ферме:\n";
            info += "Молодняка: " + youngCount + ", " +
                    "Взрослых: " + adultCount + ", " +
                    "Старых: " + oldCount + ".\n";

            info += "После продажи у фермы Стало наличных денег: " +
                    new DecimalFormat("#0.00").format(capital) + " у.е.\n";
        } else {
            youngCount = 0;
            adultCount = 0;
            oldCount = 0;
            info += "Ферма не смогла выполнить контракт по Продаже\n";
            info += "Ферма - БАНКРОТ!\n";
        }
        return info;
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

    public String makeYearModellingAndGetInfo(
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
        // за весь год в виде строки.
        String info = "";
        this.currentContract = currentContract;
        this.currentYearCost = 0.0;
        this.currentYearProfit = 0.0;
        info += "\nВ начале года Состояние фермы:\n" + getAllCurrentFarmInfo() + "\n";
        info += payFeedPriceAndGetInfo();
        info += makeAllNewGenerationAndGetInfo(
                birthCoefficientFromAdult,
                birthCoefficientFromOld,
                survivalCoefficientOfYoungAnimal,
                deathCoefficientOfOldAnimal
        );
        info += sellAnimalsByCurrentContractAndGetInfo();
        info += "\nПроисходят неблагоприятные события окружающей среды!\n";
        info += doLifestockDeathAndGetInfo(adverseEventRation);
        info += "\nВ конце года Состояние фермы:\n" + getAllCurrentFarmInfo() + "\n";
        info += getCurrentYearProfitabilityInfo();
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
