package com.lpy.mail;

import com.lpy.model.AyUser;

import javax.mail.MessagingException;
import java.util.List;

public interface SendJunkMailService {
    boolean sendJunkMail(List<AyUser> ayUserList) throws MessagingException;
}
