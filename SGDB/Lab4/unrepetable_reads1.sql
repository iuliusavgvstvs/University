--unrepetable reads:
--
--SET TRANSACTION ISOLATION LEVEL read uncommitted
SET TRANSACTION ISOLATION LEVEL repeatable read;
BEGIN TRAN;
SELECT * FROM Client;
WAITFOR DELAY '00:00:06';
SELECT * FROM Client;
COMMIT TRAN;








