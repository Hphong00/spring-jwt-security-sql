package com.example.springjwtsecuritysql.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity(name = "type_notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TypeNotifications {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TYPE_NOTIFICATIONS_SEQ")
    @SequenceGenerator(name = "TYPE_NOTIFICATIONS_SEQ", sequenceName = "TYPE_NOTIFICATIONS_SEQ", allocationSize = 1, initialValue = 1)
    Long id;

    @Column(name = "code")
    String code;

    @Column(name = "description")
    String description;

    @Column(name = "is_delete ")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isDelete ;
}
