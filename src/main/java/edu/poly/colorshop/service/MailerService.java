package edu.poly.colorshop.service;

import javax.mail.MessagingException;

import edu.poly.colorshop.model.MailModel;

public interface MailerService {

	void send(String to, String subject, String body) throws MessagingException;

	void send(MailModel mail) throws MessagingException;

}
