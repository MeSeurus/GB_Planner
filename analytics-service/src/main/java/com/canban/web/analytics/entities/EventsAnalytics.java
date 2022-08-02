package com.canban.web.analytics.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events_analytics")
public class EventsAnalytics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "event_title")
    private String eventTitle;

    @Column(name = "event_username")
    private String eventUsername;

    @Column(name = "event_begin_date")
    private LocalDateTime eventBeginDate;

    @Column(name = "event_end_date")
    private LocalDateTime eventEndDate;

    @ElementCollection
    @Column(name = "added_usernames")
    private Set<String> users;

    @CreationTimestamp
    @Column(name = "created_at")
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    public EventsAnalytics(Long eventId, String eventTitle, String eventUsername, LocalDateTime eventBeginDate, LocalDateTime eventEndDate, Set<String> users) {
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.eventUsername = eventUsername;
        this.eventBeginDate = eventBeginDate;
        this.eventEndDate = eventEndDate;
        this.users = users;
    }
}
