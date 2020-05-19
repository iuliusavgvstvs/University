--phantom reads:
--
SET TRANSACTION ISOLATION LEVEL read uncommitted;
--SET TRANSACTION ISOLATION LEVEL serializable;
BEGIN TRAN;
SELECT * FROM Client WHERE id BETWEEN 1 AND 8000;
WAITFOR DELAY '00:00:06';
SELECT * FROM Client WHERE id BETWEEN 1 AND 8000;
COMMIT TRAN;


--delete from Client where id>500
--select * from Client



