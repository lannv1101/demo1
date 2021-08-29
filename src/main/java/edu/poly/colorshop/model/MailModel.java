package edu.poly.colorshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailModel {
    String from = "Color Shop <colorshop@gmail.com>";
    String to;
    String subject;
    String body;
    String[] cc;
    String[] bb;
    String[] files;

    public MailModel(String to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

}
