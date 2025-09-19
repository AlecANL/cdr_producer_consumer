package org.example.projectcdr;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Consumer implements Runnable {
    private final LineQueue queue;


    public Consumer(LineQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        String me = Thread.currentThread().getName();

        try(Connection connection = Database.getConnection()) {
            // permite lanzar query's en lote
            connection.setAutoCommit(false);

            String sql = "INSERT INTO cdr (account_number, call_date, duration_minutes, tariff) VALUES (?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                while (true) {
                    LineItem it = this.queue.take();

                    if (it.poison) {
                        statement.executeBatch();
                        connection.commit();
                        break;
                    }

                    String[] fragments = it.text.split(",");
                    String account = fragments[0].trim();
                    String phoneFrom = fragments[1].trim();
                    String phoneTo = fragments[2].trim();
                    String timestamp = fragments[3].trim();
                    String duration = fragments[4].trim();
                    String tariff = fragments[5].trim();
                    String from = fragments[6].trim();

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    LocalDateTime ldt = LocalDateTime.parse(timestamp, formatter);
                    Timestamp ts = Timestamp.valueOf(ldt);

                    statement.setString(1, account);
                    statement.setString(2, ts.toString());
                    statement.setInt(3, Integer.parseInt(duration));
                    statement.setDouble(4, Double.parseDouble(tariff));
                    statement.addBatch();

                    System.out.println("@Consumer:: " + account + " | " + phoneFrom + " | " + phoneTo + " | " + ts + " | " + duration + " | " + tariff);
                }
            } catch (InterruptedException e) {
                connection.rollback();
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

//        try {
//            while (true) {
//                LineItem it = this.queue.take();
//
//                if (it.poison) {
//                    System.out.printf("[%s] finalizo%n", me);
//                    break;
//                }
//
//                String[] fragments = it.text.split(",");
//                String account = fragments[0].trim();
//                String phoneFrom = fragments[1].trim();
//                String phoneTo = fragments[2].trim();
//                String timestamp = fragments[3].trim();
//                String duration = fragments[4].trim();
//                String tariff = fragments[5].trim();
//                String from = fragments[6].trim();
//
//                System.out.printf("[%s] consumio seq=%d de %s :: %s%n", me, it.seq, it.producer, it.text);
//            }
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
    }
}
