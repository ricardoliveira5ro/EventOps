CREATE VIEW V_DAILY_EVENT_COUNT AS
SELECT
    DATE(timestamp) AS event_date,
    event_name,
    COUNT(*) AS total
FROM T_EVENT
GROUP BY DATE(timestamp), event_name;

CREATE INDEX IF NOT EXISTS timestampIdx ON T_EVENT(timestamp);
CREATE INDEX IF NOT EXISTS eventNameIdx ON T_EVENT(event_name);
