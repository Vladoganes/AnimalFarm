import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatisticsWindowFrame extends JFrame {
    public static final Object[][] rowNames = new String[][] {
            {"<html>Было <br/>Молодых (шт.)<html>"},
            {"<html>Было <br/>Взрослых (шт.)<html>"},
            {"<html>Было <br/>Старых (шт.)<html>"},
            {"<html>Стало <br/>Молодых (шт.)<html>"},
            {"<html>Стало <br/>Взрослых (шт.)<html>"},
            {"<html>Стало <br/>Старых (шт.)<html>"},
            {"<html>Было <br/>Денег (у.е.)<html>"},
            {"<html>Стало <br/>Денег (у.е.)<html>"},
            {"<html>Чистая прибыль<br/> (у.е.)<html>"},
            {"Расходы (у.е.)"},
            {"<html>Рентабельность<br/> (%)<html>"},
            {"Банкрот ?"},
            {"<html>Стоимость <br/>корма (у.е.)<html>"},
            {"<html>Новое поколение <br/>Молодых (шт.)<html>"},
            {"<html>Новое поколение <br/>Взрослых (шт.)<html>"},
            {"<html>Новое поколение <br/>Старых (шт.)<html>"},
            {"<html>Умерло Молодых <br/>из-за корма (шт.)<html>"},
            {"<html>Умерло Взрослых <br/>из-за корма (шт.)<html>"},
            {"<html>Умерло Старых <br/>из-за корма (шт.)<html>"},
            {"<html>Умерло Молодых <br/>из-за природных <br/>условий (шт.)<html>"},
            {"<html>Умерло Взрослых <br/>из-за природных <br/>условий (шт.)<html>"},
            {"<html>Умерло Старых <br/>из-за природных <br/>условий (шт.)<html>"},
            {"<html>Неустойка всего <br/>за продажу <br/>Молодых (у.е.)<html>"},
            {"<html>Неустойка всего <br/>за продажу <br/>Взрослых (у.е.)<html>"},
            {"<html>Неустойка всего <br/>за продажу <br/>Старых (у.е.)<html>"}
    };

    public static final Object[] columnNames = new String[] {
            "<html>Все <br/>параметры<html>",
            "1 год",
            "2 год",
            "3 год",
            "4 год",
            "5 год"
    };

    private JPanel tableContainer;
    private JLabel tableName;
    private DefaultTableModel model;
    private JTable statisticsTable;
    private JButton hideButton;

    Font titleFont = new Font("Arial", Font.BOLD, 28);
    Font tableColumnTitlesFont = new Font("Arial", Font.BOLD, 20);
    Font severalTextFont = new Font("Comic Sans MS", Font.PLAIN, 16);

    public StatisticsWindowFrame() {
        super("AnimalFarm: Statistics");
        setBounds(580, 100, 900, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        tableContainer = new JPanel(new BorderLayout());
        hideButton = new JButton("Закрыть");
        hideButton.setFont(tableColumnTitlesFont);
        hideButton.addActionListener(e -> setVisible(false));
        tableName = new JLabel("Статистика по ферме", JLabel.CENTER);
        tableName.setFont(titleFont);
        model = new DefaultTableModel();
        statisticsTable = new JTable(model);
        model.addColumn(columnNames[0]);
        for (Object[] rowName : rowNames) {
            model.addRow(rowName);
        }
        statisticsTable.setCellSelectionEnabled(true);
        Color shampine = new Color(240, 231, 185);
        statisticsTable.setBackground(shampine);

        statisticsTable.getTableHeader().setFont(tableColumnTitlesFont);
        statisticsTable.setRowHeight(80);
        statisticsTable.setFont(severalTextFont);
        tableContainer.add(tableName, BorderLayout.NORTH);
        tableContainer.add(new JScrollPane(
                statisticsTable,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
                BorderLayout.CENTER
        );
        tableContainer.add(hideButton, BorderLayout.SOUTH);
        add(tableContainer);
        setVisible(true);
        repaint();
    }

    public static void main(String[] args) {
        new StatisticsWindowFrame();
    }

    public JPanel getTableContainer() {
        return tableContainer;
    }

    public JLabel getTableName() {
        return tableName;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public JTable getStatisticsTable() {
        return statisticsTable;
    }
}
