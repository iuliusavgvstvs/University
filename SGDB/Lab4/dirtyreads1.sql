--dirty reads:
--
BEGIN TRAN;
UPDATE Client SET data_nasterii='2001-04-13' WHERE id=1;
WAITFOR DELAY '00:00:07';
ROLLBACK TRAN;
/*

	select * from Client

*/



