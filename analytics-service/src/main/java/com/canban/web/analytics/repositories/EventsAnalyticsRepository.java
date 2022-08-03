package com.canban.web.analytics.repositories;

import com.canban.web.analytics.entities.EventsAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventsAnalyticsRepository extends JpaRepository<EventsAnalytics, Long> {


    @Query(
            value = "select * from events_analytics " +
                    "where (CURRENT_DATE -  event_begin_date) < '1 MONTH' " +
                    "order by (event_end_date - event_begin_date) desc limit 1",
            nativeQuery = true
    )
    Optional<EventsAnalytics> getTheLongestEventForLastMonth();

    @Query(
            value = "select * from events_analytics " +
                    "where (CURRENT_DATE -  event_begin_date) < '1 WEEK' " +
                    "order by (event_end_date - event_begin_date) desc limit 1",
            nativeQuery = true
    )
    Optional<EventsAnalytics> getTheLongestEventForLastWeek();
}
