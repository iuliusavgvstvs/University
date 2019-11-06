#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <pthread.h>
#include <stdlib.h>

void deservire_client(int c){
		char sir[200], cminim;
		int nrminim=200, lpoz=0, poz[200], i, j, lun;
 		printf ("S-a conectat un client.\n");
      // deservirea clientului
      recv (c, &lun, sizeof (lun), MSG_WAITALL);
      recv (c, sir, lun, MSG_WAITALL);
      sir[lun-1] = '\0';
		printf("Sir: %s. lungime: %d\n", sir, lun);
		for(i=0;i<lun-1;i++){
			int ap=0;
			char cr=sir[i];
			for(j=0;j<lun-1;j++){
				if(cr==sir[j]){
				ap++;
				}
			}
			if(ap<nrminim){
				nrminim=ap;
				cminim=cr;
			}
		}
		lpoz=0;
		for(i=0;i<lun-1;i++)
			if(sir[i]==cminim){
				poz[lpoz]=i;
				printf("%d ", poz[lpoz]);
				lpoz++;
			}
      send (c, &cminim, sizeof (cminim), 0);
		printf("S-a trimis caracterul minim %c\n",cminim);
		send(c, &lpoz, sizeof(lpoz), 0);
		printf("S-a trimis lungimea vectorului %d\n", lpoz);
		send(c, poz,sizeof(int)* lpoz, 0);
		printf("S-a trimis vectorul.\n");
      close (c);
		exit(0);
      // sfarsitul deservirii clientului;
}

int
main ()
{
  int s;
  struct sockaddr_in server, client;
  int c, l;

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
		if(fork()==0){
			deservire_client(c);
			return 0;
		}
    }
}
