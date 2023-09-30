package edu.school21.viewmodels.handlers;

import edu.school21.enums.RotationPeriod;
import edu.school21.viewmodels.handlers.wrappers.HistoryWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class History {
    private static final Integer MAX_HISTORY_COUNT = 10;
    private static final String HISTORY_FILE_NAME = "history/history.xml";
    private final ObservableList<String> history = FXCollections.observableArrayList();
    private final MultipleSelectionModel<String> historySelectionModel;

    private static final Logger rootLog = LogManager.getRootLogger();
    private static Logger log;

    public History(final ListView<String> listHistory) {
        listHistory.setItems(history);
        historySelectionModel = listHistory.getSelectionModel();
    }

    public String getSelectedItem() {
        return historySelectionModel.getSelectedItem();
    }

    public void addExpressionToHistory(final String expression) {
        if (history.size() == MAX_HISTORY_COUNT) {
            history.remove(MAX_HISTORY_COUNT - 1);
        }
        history.add(0, expression);
        log.info(expression);
    }

    public void clear() {
        history.clear();
    }

    public void logError(final String message) {
        log.error(message);
    }

    public void logInfo(final String message) {
        log.info(message);
    }

    /**
     * Возвращает имя логера в верхнем регистре
     */
    public String getLoggerName() {
        return log.getName().toUpperCase();
    }

    /**
     * Изменяет логер с учетом периода ротации
     *
     * @param rotationPeriod период ротации
     */
    public void changeLoggerByRotationPeriod(final RotationPeriod rotationPeriod) {
        log = LogManager.getLogger(rotationPeriod.getName());
    }

    /**
     * Загружает историю и период ротации логов из файла history.xml
     */
    public void loadHistoryFromFile() {
        try {
            final JAXBContext context = JAXBContext.newInstance(HistoryWrapper.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();

            final InputStream stream = new FileInputStream(HISTORY_FILE_NAME);
            final HistoryWrapper historyWrapper = (HistoryWrapper) unmarshaller.unmarshal(stream);

            final List<String> data = historyWrapper.getHistory();
            if (data != null) {
                clear();
                history.addAll(data);
            }
            log = LogManager.getLogger(historyWrapper.getPeriodRotation());
        } catch (final Exception e) {
            rootLog.debug(e.getMessage());
            showAlertErrorWindow("Could not load data from resource " + HISTORY_FILE_NAME);
        }
    }

    /**
     * Сохраняет историю и период ротации логов в файл history.xml.
     */
    public void saveHistoryToFile() {
        try {
            final JAXBContext context = JAXBContext.newInstance(HistoryWrapper.class);
            final Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            final HistoryWrapper historyWrapper = new HistoryWrapper();
            historyWrapper.setHistory(new ArrayList<>(history));
            historyWrapper.setPeriodRotation(log.getName());

            final OutputStream out = new FileOutputStream(HISTORY_FILE_NAME);
            marshaller.marshal(historyWrapper, out);
        } catch (final Exception e) {
            rootLog.debug(e.getMessage());
            showAlertErrorWindow("Could not save data to resource " + HISTORY_FILE_NAME);
        }
    }

    private void showAlertErrorWindow(final String message) {
        final Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
