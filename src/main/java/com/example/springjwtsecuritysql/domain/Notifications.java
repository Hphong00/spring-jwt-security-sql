package com.example.springjwtsecuritysql.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Notifications {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NOTIFICATIONS_SEQ")
    @SequenceGenerator(name = "NOTIFICATIONS_SEQ", sequenceName = "NOTIFICATIONS_SEQ", allocationSize = 1, initialValue = 1)
    Long id;

    @OneToOne()
    @JoinColumn(name = "sender_id",nullable = false)
    User userSender;

    @OneToOne()
    @JoinColumn(name = "receiver_id",nullable = false)
    User userReceiver;

    @Column(name = "create_date",nullable = false)
    Date createDate;

    @ManyToOne()
    @JoinColumn(name = "type_id",nullable = false)
    TypeNotifications typeNotifications;

}
