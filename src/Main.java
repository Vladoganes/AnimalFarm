import model.Experiment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private static Experiment experimentModel;

    private static StatisticsWindowFrame statisticsWindowFrame;

    public static void main(String[] args) {
        int startFarmLabelY = 10;
        int startFarmLabelX = 50;
        int startFarmLabelW = 280;
        int startFarmLabelH = 20;
        int farmLabelPanelX = 20;
        int ySizeBetweenStartLabelAndPanel = 20;
        int farmPanelY = startFarmLabelY + startFarmLabelH + ySizeBetweenStartLabelAndPanel;
        int farmLabelPanelW = 280;
        int farmPanelH = 120;
        int xSizeBetweenLabelsAndFields = 10;
        int farmFieldsPanelX = farmLabelPanelX + farmLabelPanelW + xSizeBetweenLabelsAndFields;
        int farmFieldsPanelW = 70;
        int startContractLabelY = farmPanelY + farmPanelH + ySizeBetweenStartLabelAndPanel;
        int startContractLabelH = 20;
        int contractPanelY = startContractLabelY + startContractLabelH + ySizeBetweenStartLabelAndPanel;
        int contractPanelH = 300;
        int ySizeBetweenButtonAndPanel = 30;
        int buttonsStartY = contractPanelY + contractPanelH + ySizeBetweenButtonAndPanel;
        int buttonWidth = farmLabelPanelW + farmFieldsPanelW + xSizeBetweenLabelsAndFields;
        int buttonHeight = 40;
        int ySizeBetweenButtons = 10;
        Font startLabelsFont = new Font("Arial", Font.BOLD, 18);
        Font severalLabelsFont = new Font("Arial", Font.PLAIN, 14);
        Font buttonsFont = new Font("Arial", Font.BOLD, 16);

        JFrame mainWindow = new JFrame("AnimalFarm");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setBounds(100, 0, 2 * startFarmLabelX + buttonWidth, 800);
        mainWindow.setLayout(null);
        mainWindow.setVisible(true);

        JButton startButton = new JButton("Инициализация моделирования");
        startButton.setFont(buttonsFont);
        JButton executeOneStepButton = new JButton("Выполнить 1 шаг моделирования (1 год)");
        executeOneStepButton.setFont(buttonsFont);
        JButton executeAllStepButton = new JButton("Выполнить моделирование до конца");
        executeAllStepButton.setFont(buttonsFont);
        JButton exitButton = new JButton("Выход");
        exitButton.setFont(buttonsFont);

        JLabel startFarmLabel = new JLabel("Начальные данные фермы");
        startFarmLabel.setFont(startLabelsFont);

        JLabel capitalFarmLabel = new JLabel("Капитал (у.е.)");
        capitalFarmLabel.setFont(severalLabelsFont);
        JLabel youngCountFarmLabel = new JLabel("Молодняк (шт.)");
        youngCountFarmLabel.setFont(severalLabelsFont);
        JLabel adultCountFarmLabel = new JLabel("Взрослые животные (шт.)");
        adultCountFarmLabel.setFont(severalLabelsFont);
        JLabel oldCountFarmLabel = new JLabel("Старые животные (шт.)");
        oldCountFarmLabel.setFont(severalLabelsFont);

        JLabel startContractLabel = new JLabel("Данные контракта");
        startContractLabel.setFont(startLabelsFont);

        JLabel periodContractLabel = new JLabel("Срок контракта (лет)");
        periodContractLabel.setFont(severalLabelsFont);
        JLabel youngToSellContractLabel = new JLabel("Нужно продать молодняка (шт.)");
        youngToSellContractLabel.setFont(severalLabelsFont);
        JLabel adultToSellContractLabel = new JLabel("Нужно продать взрослых животных (шт.)");
        adultToSellContractLabel.setFont(severalLabelsFont);
        JLabel oldToSellContractLabel = new JLabel("Нужно продать старых животных (шт.)");
        oldToSellContractLabel.setFont(severalLabelsFont);
        JLabel youngPriceContractLabel = new JLabel("Цена за 1 молодое животное (у.е.)");
        youngPriceContractLabel.setFont(severalLabelsFont);
        JLabel adultPriceContractLabel = new JLabel("Цена за 1 взрослое животное (у.е.)");
        adultPriceContractLabel.setFont(severalLabelsFont);
        JLabel oldPriceContractLabel = new JLabel("Цена за 1 старое животное (у.е.)");
        oldPriceContractLabel.setFont(severalLabelsFont);
        JLabel adultFeedContractLabel = new JLabel("<html>Необходимая цена корма для 1<br/>" +
                "взрослого животного (у.е.)</html>");
        adultFeedContractLabel.setFont(severalLabelsFont);
        JLabel penaltyContractLabel = new JLabel("Неустойка за 1 животное (у.е.)");
        penaltyContractLabel.setFont(severalLabelsFont);

        JSpinner capitalFarmField = new JSpinner(new SpinnerNumberModel(1000, 10, 10_000_000, 10));
        JSpinner youngCountFarmField = new JSpinner(new SpinnerNumberModel(10, 1, 100_000, 1));
        JSpinner adultCountFarmField = new JSpinner(new SpinnerNumberModel(10, 1, 100_000, 1));
        JSpinner oldCountFarmField = new JSpinner(new SpinnerNumberModel(10, 1, 100_000, 1));

        JSpinner periodContractField = new JSpinner(new SpinnerNumberModel(3, 3, 5, 1));
        JSpinner youngToSellContractField = new JSpinner(new SpinnerNumberModel(5, 1, 100_000, 1));
        JSpinner adultToSellContractField = new JSpinner(new SpinnerNumberModel(5, 1, 100_000, 1));
        JSpinner oldToSellContractField = new JSpinner(new SpinnerNumberModel(5, 1, 100_000, 1));
        JSpinner youngPriceContractField = new JSpinner(new SpinnerNumberModel(1000, 100, 100_000, 100));
        JSpinner adultPriceContractField = new JSpinner(new SpinnerNumberModel(1000, 100, 100_000, 100));
        JSpinner oldPriceContractField = new JSpinner(new SpinnerNumberModel(1000, 100, 100_000, 100));
        JSpinner adultFeedContractField = new JSpinner(new SpinnerNumberModel(100, 10, 10_000, 10));
        JSpinner penaltyContractField = new JSpinner(new SpinnerNumberModel(1000, 100, 100_000, 100));

        // Начинаем группировать объекты GUI и располагать на окне
        JPanel farmLabelsPanel = new JPanel(new GridLayout(0, 1));
        farmLabelsPanel.setBounds(farmLabelPanelX, farmPanelY, farmLabelPanelW, farmPanelH);
        farmLabelsPanel.add(capitalFarmLabel);
        farmLabelsPanel.add(youngCountFarmLabel);
        farmLabelsPanel.add(adultCountFarmLabel);
        farmLabelsPanel.add(oldCountFarmLabel);

        JPanel farmFieldsPanel = new JPanel(new GridLayout(0, 1));
        farmFieldsPanel.setBounds(farmFieldsPanelX, farmPanelY, farmFieldsPanelW, farmPanelH);
        farmFieldsPanel.add(capitalFarmField);
        farmFieldsPanel.add(youngCountFarmField);
        farmFieldsPanel.add(adultCountFarmField);
        farmFieldsPanel.add(oldCountFarmField);

        startFarmLabel.setBounds(startFarmLabelX, startFarmLabelY, startFarmLabelW, startFarmLabelH);

        JPanel contractLabelsPanel = new JPanel(new GridLayout(0, 1));
        contractLabelsPanel.setBounds(farmLabelPanelX, contractPanelY, farmLabelPanelW, contractPanelH);
        contractLabelsPanel.add(periodContractLabel);
        contractLabelsPanel.add(youngToSellContractLabel);
        contractLabelsPanel.add(adultToSellContractLabel);
        contractLabelsPanel.add(oldToSellContractLabel);
        contractLabelsPanel.add(youngPriceContractLabel);
        contractLabelsPanel.add(adultPriceContractLabel);
        contractLabelsPanel.add(oldPriceContractLabel);
        contractLabelsPanel.add(adultFeedContractLabel);
        contractLabelsPanel.add(penaltyContractLabel);

        JPanel contractFieldsPanel = new JPanel(new GridLayout(0, 1));
        contractFieldsPanel.setBounds(farmFieldsPanelX, contractPanelY, farmFieldsPanelW, contractPanelH);
        contractFieldsPanel.add(periodContractField);
        contractFieldsPanel.add(youngToSellContractField);
        contractFieldsPanel.add(adultToSellContractField);
        contractFieldsPanel.add(oldToSellContractField);
        contractFieldsPanel.add(youngPriceContractField);
        contractFieldsPanel.add(adultPriceContractField);
        contractFieldsPanel.add(oldPriceContractField);
        contractFieldsPanel.add(adultFeedContractField);
        contractFieldsPanel.add(penaltyContractField);

        startContractLabel.setBounds(startFarmLabelX, startContractLabelY, startFarmLabelW, startFarmLabelH);

        startButton.setBounds(
                farmLabelPanelX,
                buttonsStartY,
                buttonWidth,
                buttonHeight
        );
        executeOneStepButton.setBounds(
                farmLabelPanelX,
                buttonsStartY + buttonHeight + ySizeBetweenButtons,
                buttonWidth,
                buttonHeight
        );
        // Пока не произошла Инициализация модели, нажать эту кнопку нельзя
        executeOneStepButton.setEnabled(false);
        executeAllStepButton.setBounds(
                farmLabelPanelX,
                buttonsStartY + 2*buttonHeight + 2*ySizeBetweenButtons,
                buttonWidth,
                buttonHeight
        );
        // Пока не произошла Инициализация модели, нажать эту кнопку нельзя
        executeAllStepButton.setEnabled(false);
        exitButton.setBounds(
                farmLabelPanelX,
                buttonsStartY + 3*buttonHeight + 3*ySizeBetweenButtons,
                buttonWidth,
                buttonHeight
        );

        // Ниже добавляем действия при нажатии на кнопки
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Если нажали на кнопку "Выход", то приложение должно
                // остановить работу и закрыться.
                mainWindow.setVisible(false);
                if (statisticsWindowFrame != null) {
                    statisticsWindowFrame.setVisible(false);
                    statisticsWindowFrame.dispose();
                }
                mainWindow.dispose();
            }
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Если нажали кнопку "Инициализация моделирования", то
                // можно выполнять моделирование по шагам, либо
                // выполнить моделирование сразу до конца.
                executeOneStepButton.setEnabled(true);
                executeAllStepButton.setEnabled(true);
                startButton.setEnabled(false);
                capitalFarmField.setEnabled(false);
                youngCountFarmField.setEnabled(false);
                adultCountFarmField.setEnabled(false);
                oldCountFarmField.setEnabled(false);
                periodContractField.setEnabled(false);
                experimentModel = new Experiment(
                        (int) youngCountFarmField.getValue(),
                        (int) adultCountFarmField.getValue(),
                        (int) oldCountFarmField.getValue(),
                        (int) capitalFarmField.getValue(),
                        (int) periodContractField.getValue()
                );
                if (statisticsWindowFrame != null) {
                    statisticsWindowFrame.setVisible(false);
                    statisticsWindowFrame.dispose();
                }
                statisticsWindowFrame = new StatisticsWindowFrame();
            }
        });
        executeOneStepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Если нажали кнопку "Выполнить 1 шаг моделирования (1 год)",
                // то просто выполняем для введенных данных полей контракта
                // 1 год моделирования.
                if (experimentModel.isExperimentStillGoingOn()) {
                    experimentModel.currentYearContract(
                            (int) youngToSellContractField.getValue(),
                            (int) adultToSellContractField.getValue(),
                            (int) oldToSellContractField.getValue(),
                            (int) youngPriceContractField.getValue(),
                            (int) adultPriceContractField.getValue(),
                            (int) oldPriceContractField.getValue(),
                            (int) adultFeedContractField.getValue(),
                            (int) penaltyContractField.getValue()
                    );
                    String[] data = experimentModel.makeStepAndGetFarmInfo();
                    statisticsWindowFrame.setVisible(true);
                    statisticsWindowFrame.getModel().addColumn(
                                    StatisticsWindowFrame.columnNames[
                                            experimentModel.getCurrentYear()]
                    );
                    // После того как добавили колонку соответствующего года
                    // заполняем все строки таблицы в соответствии с названиями
                    // строк из StatisticsWindowFrame.rowNames
                    for (int i = 0; i < 25; i++) {
                        statisticsWindowFrame.getStatisticsTable().setValueAt(
                                data[i],
                                i,
                                experimentModel.getCurrentYear()
                        );
                    }
                }
                if (!experimentModel.isExperimentStillGoingOn()) {
                    // Делаем доступными для ввода все поля, которые
                    // связаны с инициализацей Эксперимента и делаем
                    // доступной для нажатия кнопку "Инициализация"
                    startButton.setEnabled(true);
                    capitalFarmField.setEnabled(true);
                    youngCountFarmField.setEnabled(true);
                    adultCountFarmField.setEnabled(true);
                    oldCountFarmField.setEnabled(true);
                    periodContractField.setEnabled(true);
                    // Эти кнопки наоборот нельзя активировать, так как
                    // эксперимент закончился, а новый не инициалиирован
                    executeOneStepButton.setEnabled(false);
                    executeAllStepButton.setEnabled(false);
                }
            }
        });
        executeAllStepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Если нажали кнопку "Выполнить моделирование до конца",
                // то дальше нельзя снова нажать другие кнопки кроме
                // "Инициализация моделирования", потому что текущее
                // моделирование уже закончено целиком.
                // Также делаем доступными для ввода все поля, которые
                // связаны с инициализацей Эксперимента.
                startButton.setEnabled(true);
                capitalFarmField.setEnabled(true);
                youngCountFarmField.setEnabled(true);
                adultCountFarmField.setEnabled(true);
                oldCountFarmField.setEnabled(true);
                periodContractField.setEnabled(true);
                // Эти кнопки наоборот нельзя активировать, так как
                // эксперимент закончился, а новый не инициалиирован
                executeOneStepButton.setEnabled(false);
                executeAllStepButton.setEnabled(false);
                for (int i = experimentModel.getCurrentYear(); i < experimentModel.getContractPeriod(); i++) {
                    if (experimentModel.isExperimentStillGoingOn()) {
                        experimentModel.currentYearContract(
                                (int) youngToSellContractField.getValue(),
                                (int) adultToSellContractField.getValue(),
                                (int) oldToSellContractField.getValue(),
                                (int) youngPriceContractField.getValue(),
                                (int) adultPriceContractField.getValue(),
                                (int) oldPriceContractField.getValue(),
                                (int) adultFeedContractField.getValue(),
                                (int) penaltyContractField.getValue()
                        );
                        String[] data = experimentModel.makeStepAndGetFarmInfo();
                        statisticsWindowFrame.setVisible(true);
                        statisticsWindowFrame.getModel().addColumn(
                                StatisticsWindowFrame.columnNames[
                                        experimentModel.getCurrentYear()]
                        );
                        // После того как добавили колонку соответствующего года
                        // заполняем все строки таблицы в соответствии с названиями
                        // строк из StatisticsWindowFrame.rowNames
                        for (int j = 0; j < 25; j++) {
                            statisticsWindowFrame.getStatisticsTable().setValueAt(
                                    data[j],
                                    j,
                                    experimentModel.getCurrentYear()
                            );
                        };
                    } else {
                        break;
                    }
                }
            }
        });


        mainWindow.add(startFarmLabel);
        mainWindow.add(farmLabelsPanel);
        mainWindow.add(farmFieldsPanel);
        mainWindow.add(startContractLabel);
        mainWindow.add(contractLabelsPanel);
        mainWindow.add(contractFieldsPanel);
        mainWindow.add(startButton);
        mainWindow.add(executeOneStepButton);
        mainWindow.add(executeAllStepButton);
        mainWindow.add(exitButton);

        mainWindow.repaint();

    }
}