SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
BEGIN TRAN;
UPDATE Client SET nume='Alin' WHERE prenume='Ion';
WAITFOR DELAY '00:00:05';
UPDATE Producator SET licenta = 'l100' WHERE nume='Sun Pharma';
COMMIT TRAN;


--    select * from CLient
--    select * from Producator

















