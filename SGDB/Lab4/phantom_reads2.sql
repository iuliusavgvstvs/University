--phantom reads:
--
BEGIN TRAN;
WAITFOR DELAY '00:00:03';
INSERT INTO Client (nume, prenume, data_nasterii) VALUES ('nume1','prenume1', '1999-11-11');
COMMIT TRAN;























