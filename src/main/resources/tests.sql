-- (1) Write MySQL query to find IPs that made more than a certain number of requests for a given time period.
--     Ex: Write SQL to find IPs that made more than 100 requests starting from 2017-01-01.13:00:00 to 2017-01-01.14:00:00.

select
  ip,
  count(*) as requests
from
  http_logs
where
  created_on between '2017-01-01 13:00:00' and '2017-01-01 14:00:00'
group by
  ip
having
  count(*) > 100
order by
  count(*)
;

-- (2) Write MySQL query to find requests made by a given IP.
select
  *
From
  http_logs
where
  ip = '192.168.85.243'
;