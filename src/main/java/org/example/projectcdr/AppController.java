package org.example.projectcdr;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javafx.scene.control.TextField;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

public class AppController {
    final int QUEUE_SIZE = 200;
    final int N_PRODUCERS = 2;
    final int N_CONSUMERS = 3;

    LineQueue queue = new LineQueue(QUEUE_SIZE);
    AtomicLong sequence = new AtomicLong(0);
    CountDownLatch doneProducers = new CountDownLatch(N_PRODUCERS);

//    private ConcurrentHashMap<String, Instant> producerTime = new ConcurrentHashMap<>();
//    private ConcurrentHashMap<String, AtomicLong> counterProducer = new ConcurrentHashMap<>();
//    private ConcurrentHashMap<String, AtomicLong> counterConsumer = new ConcurrentHashMap<>();
//    private ConcurrentHashMap<String, AtomicLong> consumerTime = new ConcurrentHashMap<>();
//
//
//    private ObservableList<TableDetail> producerDetail = FXCollections.observableArrayList();
//    private ObservableList<TableDetail> consumerDetail = FXCollections.observableArrayList();

//    @FXML
//    private TableView<TableDetail> producerView;
//    @FXML
//    private TableColumn<TableDetail, String> colProdName;
//    @FXML
//    private TableColumn<TableDetail, String> colProdStartTime;
//    @FXML
//    private TableColumn<TableDetail, Number> colProdRecords;
//
//    @FXML
//    private TableView<TableDetail> consumerView;
//    @FXML
//    private TableColumn<TableDetail, String> colConsName;
//    @FXML
//    private TableColumn<TableDetail, String> colConsStartTime;
//    @FXML
//    private TableColumn<TableDetail, Number> colConsRecords;
//    @FXML
//    private TableColumn<TableDetail, Number> colConsMinutes;

//    @FXML
//    public void initialize() {
//        colProdName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
//        colProdStartTime.setCellValueFactory(cellData -> cellData.getValue().startTimeProperty());
//        colProdRecords.setCellValueFactory(cellData -> cellData.getValue().recordsProperty());
//
//        colConsName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
//        colConsStartTime.setCellValueFactory(cellData -> cellData.getValue().startTimeProperty());
//        colConsRecords.setCellValueFactory(cellData -> cellData.getValue().recordsProperty());
//        colConsMinutes.setCellValueFactory(cellData -> cellData.getValue().minutesProperty());
//
//        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> this.updateTableView()));
//    }
//
//    private void updateTableView() {
//
//    }
//
//    private void createProducerTableView() {
//
//    }
//
//    private void createConsumerTableView() {
//
//    }


    @FXML
    protected TextField txtSearch;

    @FXML
    protected Label lblSearch;

    @FXML
    protected void handleSearchRegister() {
        String search = txtSearch.getText();

        String sql = "SELECT " +
                " account_number, " +
                " SUM(duration_minutes) AS total_minutes, " +
                " SUM(duration_minutes * tariff) AS total_cost " +
                "FROM cdr " +
                "WHERE account_number = ? " +
                "GROUP BY account_number";


        try(Connection connection = Database.getConnection()) {
            PreparedStatement query = connection.prepareStatement(sql);
            query.setString(1, search);

            try (ResultSet resultSet = query.executeQuery()) {
                while (resultSet.next()) {
                    String account = resultSet.getString("account_number");
                    int totalMinutes = resultSet.getInt("total_minutes");
                    double totalCost = resultSet.getDouble("total_cost");

                    this.lblSearch.setText("La cuenta: " + account + " Hablo un total de " + totalMinutes + " Minutos" + " con un costo de " + "Q." + totalCost);
                    System.out.printf("Account: %s, Total Minutes: %d, Total Cost: %.2f%n", account, totalMinutes, totalCost);
                    this.txtSearch.setText("");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @FXML
    protected void handleOpenFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.csv")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            System.out.println("Archivo cargado: " + selectedFile.getAbsolutePath());
            this.readFile(selectedFile);
        }
    }

    private void readFile(File file) throws IOException {
        try (SharedFileReader reader = new SharedFileReader(file.getPath())) {
            Thread[] consumers = new Thread[N_CONSUMERS];
            for (int i = 0; i < N_CONSUMERS; i++) {
                consumers[i] = new Thread(new Consumer(queue), "@Consumer" + (i + 1));
                consumers[i].start();
            }


            Thread[] producers = new Thread[N_PRODUCERS];
            for (int i = 0; i < N_PRODUCERS; i++) {
                producers[i] = new Thread(() -> {
                   try {
                       new Productor(reader, queue, sequence).run();
                   } finally {
                       doneProducers.countDown();
                   }
                }, "@Productor" + (i + 1));
                producers[i].start();
            }

            doneProducers.await();

            for (int i = 0; i < N_CONSUMERS; i++) {
                queue.put(LineItem.poison());
            }

            for (Thread consumer : consumers) {
                consumer.join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Finalizado");
    }
}
