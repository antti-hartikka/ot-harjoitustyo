package trainerapp.ui;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import trainerapp.domain.DataService;
import trainerapp.domain.Session;

public class Statistics {

    private final DataService dataService;

    public Statistics(DataService dataService) {
        this.dataService = dataService;
    }

    public LineChart<String, Number> getChart(String username) {
        CategoryAxis x = new CategoryAxis();
        NumberAxis y = new NumberAxis();

        x.setLabel("time");
        y.setLabel("average miss (semitones)");

        LineChart<String, Number> chart = new LineChart<>(x, y);
        chart.setTitle("progress of user " + username);
        chart.setLegendVisible(false);
        chart.setCreateSymbols(false);

        XYChart.Series<String, Number> data = new XYChart.Series<>();
        for (Session s : dataService.getSessions(username)) {
            data.getData().add(new XYChart.Data<>(s.getDate().toString(), s.getAverageMiss()));
        }

        chart.getData().add(data);
        return chart;
    }

}
