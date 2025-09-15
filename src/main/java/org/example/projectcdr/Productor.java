package org.example.projectcdr;


import java.util.Arrays;

public class Productor implements Runnable {
    private final String[] cdrInFileListContent;
    private final Buffer buffer;

    public Productor(Buffer buffer, String[] cdrInFileListContent) {
        this.cdrInFileListContent = cdrInFileListContent;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // TODO: maybe should create a count class, to manage access to cdr array
                int position = 0;

                if (position == this.cdrInFileListContent.length) break;
                CDRRegister register =  this.createRegisterCDR(this.cdrInFileListContent[position]);
                this.buffer.put(register);
                Thread.sleep(100);
            }
        } catch (Exception e) {

        }
    }

    private CDRRegister createRegisterCDR(String register) {
        String[] fields = register.split(",");
        String accountNumber = fields[0];
        String phone = fields[1];
        String timestamp = fields[2];
        String duration = fields[3];

        CDRRegister cdrRegister = new CDRRegister();
        cdrRegister.setAccountNumber(accountNumber);
        cdrRegister.setPhone(phone);
        cdrRegister.setTimestamp(timestamp);
        cdrRegister.setDuration(duration);

        return cdrRegister;
    }
}
