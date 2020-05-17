alter Procedure adaugare @metoda varchar(100), @cantitate varchar(100), @administrare varchar(20), @tip varchar(20), @nume varchar(20), @licenta varchar(20), @numeprodus varchar(20), @descriere varchar(20), @datap date, @datai date, @datae date
AS 
Begin
   BEGIN TRY
    BEGIN TRANSACTION;
	SAVE TRANSACTION MySavePoint;
	Declare @ok int;
	Declare @idadm int;
	Declare @idprod int;
	Declare @idtm int;
	Declare @idprc int;
	Declare @idadministrare int;
	Declare @idproducator int;
	Declare @idtip int;
	Set @ok=1;

	exec checkString @name = @metoda
	exec checkString @name = @cantitate
	print('inainte de primul insert')
	Select @idadm =(Max(id)+1) From Administrare;
	Insert into Administrare(id,metoda_administrare, cantitate_recomandata) values ( @idadm, @metoda, @cantitate)
	print('dupa primul insert')
	Set @ok=2;
	SAVE TRANSACTION MySavePoint2;

	Select @idtm =(Max(id)+1) From TipMedicament;
	exec checkString @name = @tip
		print('inainte de al 2-lea insert')
	Insert into TipMedicament(administrare, tip, idadministrare) values (@administrare, @tip, @idadm)
		print('dupa al 2-lea insert')
	Set @ok=3;
	SAVE TRANSACTION MySavePoint3;

	Select @idprod =(Max(id)+1) From Producator;
	exec checkString @name = @nume
	exec checkString @name = @licenta
	print('inainte de al 3-lea insert')
	Insert into Producator( nume, licenta) values (@nume, @licenta)
			print('dupa al 3-lea insert')

	Set @ok=4;
	SAVE TRANSACTION MySavePoint4;
		Select @idproducator =(Max(id)+1) From Produs;
	print('inainte de al 4-lea insert')
	exec checkString @name = @numeprodus
	exec checkString @name = @descriere
	Insert into Produs(id, nume,descriere, idproducator, data_producere, data_import, data_expirare, idtip) values (@idproducator,@numeprodus, @descriere, @idprod, @datap, @datai, @datae, @idtm)
	print('dupa al 4-lea insert')
	Set @ok=5;
	SAVE TRANSACTION MySavePoint5;
 END TRY
    BEGIN CATCH
	print(@ok);
	print('catch executed');
		if @ok = 1
			ROLLBACK TRANSACTION MySavePoint;
		if @ok = 2
			ROLLBACK TRANSACTION MySavePoint2;
		if @ok = 3
			ROLLBACK TRANSACTION MySavePoint3;
		if @ok = 4
			ROLLBACK TRANSACTION MySavePoint4;
		if @ok = 5
			ROLLBACK TRANSACTION MySavePoint5;

	End CATCH
	COMMIT TRANSACTION;
End





create Procedure adaugare2 @metoda varchar(100), @cantitate varchar(100), @administrare varchar(20), @tip varchar(20), @nume varchar(20), @licenta varchar(20), @numeprodus varchar(20), @descriere varchar(20), @datap date, @datai date, @datae date
AS 
Begin
   BEGIN TRY
    BEGIN TRANSACTION;
	SAVE TRANSACTION MySavePoint;
	Declare @ok int;
	Declare @idadm int;
	Declare @idprod int;
	Declare @idtm int;
	Declare @idprc int;
	Declare @idadministrare int;
	Declare @idproducator int;
	Declare @idtip int;
	Set @ok=1;

	exec checkString @name = @metoda
	exec checkString @name = @cantitate
	exec checkString @name = @tip
	exec checkString @name = @nume
	exec checkString @name = @licenta
	exec checkString @name = @numeprodus
	exec checkString @name = @descriere


	print('inainte de primul insert')
	Select @idadm =(Max(id)+1) From Administrare;
	Insert into Administrare(id,metoda_administrare, cantitate_recomandata) values ( @idadm, @metoda, @cantitate)
	print('dupa primul insert')

	Select @idtm =(Max(id)+1) From TipMedicament;

		print('inainte de al 2-lea insert')
	Insert into TipMedicament(administrare, tip, idadministrare) values (@administrare, @tip, @idadm)
		print('dupa al 2-lea insert')


	Select @idprod =(Max(id)+1) From Producator;

	print('inainte de al 3-lea insert')
	Insert into Producator( nume, licenta) values (@nume, @licenta)
	print('dupa al 3-lea insert')


	Select @idproducator =(Max(id)+1) From Produs;
	print('inainte de al 4-lea insert')
	Insert into Produs(id, nume,descriere, idproducator, data_producere, data_import, data_expirare, idtip) values (@idproducator,@numeprodus, @descriere, @idprod, @datap, @datai, @datae, @idtm)
	print('dupa al 4-lea insert')

 END TRY
    BEGIN CATCH
	print(@ok);
	print('catch executed');
			ROLLBACK TRANSACTION MySavePoint;
	End CATCH
	COMMIT TRANSACTION;
End

CREATE procedure checkString
(
    @name varchar(100)
)
as
begin
    if @name = ''
        THROW 51000, 'Invalid name', 1;
    return 1
end;


exec adaugare @metoda = 'merge', @cantitate = 'merge', @administrare = 'merge', @tip = 'merge', @nume = 'merge', @licenta = 'merge', @numeprodus = 'merge', @descriere = 'merge', @datap = '1975-10-01', @datai = '1975-10-02', @datae = '1975-10-03'

exec adaugare @metoda = 'merge2', @cantitate = 'merge2', @administrare = 'merge2', @tip = 'merge2', @nume = '', @licenta = 'numerge', @numeprodus = 'numerge', @descriere = 'numerge', @datap = '1975-10-01', @datai = '1975-10-02', @datae = '1975-10-03'

exec adaugare2 @metoda = 'merge22', @cantitate = 'merge22', @administrare = 'merge22', @tip = 'merge22', @nume = 'merge22', @licenta = 'merge22', @numeprodus = 'merge22', @descriere = 'merge22', @datap = '1975-10-01', @datai = '1975-10-02', @datae = '1975-10-03'

exec adaugare2 @metoda = 'da', @cantitate = 'da', @administrare = 'da', @tip = 'da', @nume = '', @licenta = 'nu', @numeprodus = 'nu', @descriere = 'nu', @datap = '1975-10-01', @datai = '1975-10-02', @datae = '1975-10-03'


select * from Administrare
select * from TipMedicament
select * from Producator
select * from Produs
