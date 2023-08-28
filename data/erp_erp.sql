


































/*create table wx_app_pay_record
(
    order_money INT,
    pay_time    DATETIME,
    is_refund   TINYINT(1)
);

SELECT SUM(order_money) AS total_money,
       NULL             AS ass_money
FROM wx_app_pay_record
WHERE pay_time >= '2023-04-12'
  AND pay_time <= '2023-04-13'
UNION ALL
SELECT SUM(order_money) AS ass_money,
       NULL             AS total_money
FROM wx_app_pay_record
WHERE pay_time >= '2023-04-12'
  AND pay_time <= '2023-04-13'
  AND is_refund = 0;


SELECT SUM(CASE WHEN is_refund !=0 THEN order_money ELSE 0 END) AS total_money,
       SUM(CASE WHEN is_refund = 0 THEN order_money ELSE 0 END) AS ass_money
FROM wx_app_pay_record
WHERE pay_time >= '2023-04-12' AND pay_time <= '2023-04-13';*/


