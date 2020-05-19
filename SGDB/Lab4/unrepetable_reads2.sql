--unrepetable reads:
--
BEGIN TRAN;
WAITFOR DELAY '00:00:03';
UPDATE Client SET nume='gigica' WHERE id= 5;
COMMIT TRAN;








