#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>

int
main ()
{
  int s;
  struct sockaddr_in server, client;
  int c, l, i, j, lun, lun2;
  char sir[200], sir2[200];

  s = socket (AF_INET, SOCK_STREAM, 0);
  if (s < 0)
    {
      printf ("Eroare la crearea socketului server\n");
      return 1;
    }

  memset (&server, 0, sizeof (server));
  server.sin_port = htons (1234);
  server.sin_family = AF_INET;
  server.sin_addr.s_addr = INADDR_ANY;

  if (bind (s, (struct sockaddr *) &server, sizeof (server)) < 0)
    {
      printf ("Eroare la bind\n");
      return 1;
    }

  listen (s, 5);

  l = sizeof (client);
  memset (&client, 0, sizeof (client));

  while (1)
    {
      c = accept (s, (struct sockaddr *) &client, &l);
      printf ("S-a conectat un client.\n");
      // deservirea clientului
      recv (c, &lun, sizeof (lun), MSG_WAITALL);
      recv (c, sir, lun, MSG_WAITALL);
		sir[lun]='\0';
		recv(c, &i, sizeof(i), MSG_WAITALL);
		recv(c,&l,sizeof(l),MSG_WAITALL);
		if(sir[0]=='\0'){
			char err[]="Sirul este vid!";
			lun2=strlen(err);
			strcpy(sir2,err);
		}
		else if(i<0||i>lun-1){
			char err[]="Index invalid!!!";
			lun2=strlen(err);;
			strcpy(sir2,err);
		}
		else if(l<1||l>lun-1){
			char err[]="Lungime invalida!";
			lun2=strlen(err);
			strcpy(sir2,err);
		}
		else if(l+i>lun){
			char err[]="Index si lungine invalide!";
			lun2=strlen(err);;
			strcpy(sir2,err);
		}
		else{
      	j = i;
			lun2=0;
			while(l>0){
				sir2[lun2++]=sir[j++];
				l--;
			}
      	sir2[lun2]='\0';
			lun2=strlen(sir2);
		}
      send (c, &lun2, sizeof (lun2), 0);
      send (c, sir2, lun2, 0);
      close (c);
      // sfarsitul deservirii clientului;
    }
}
