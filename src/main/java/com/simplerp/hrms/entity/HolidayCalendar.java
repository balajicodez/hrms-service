package com.simplerp.hrms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "holiday_calendar")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HolidayCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
