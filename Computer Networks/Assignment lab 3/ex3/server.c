#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <string.h>
#include <stdlib.h>
#include <arpa/inet.h>
#include <fcntl.h>
#include <unistd.h>
#include <pthread.h>

void* deservire_client (void *arg)
{
  printf ("S-a conectat un client.\n");
  // deservirea clientului
  int l, i;
  int c= *((int*)arg);
  char sir[200], sir2[200];
  recv (c, &l, sizeof (l), MSG_WAITALL);
  recv(c, sir, l, MSG_WAITALL);
  l=l-1;
  for(i=0;i<l;i++){
  		sir2[i]=sir[l-i-1];
	}
	sir2[l]='\0';
	send(c,sir2,l, 0);
  close (c);
  pthread_exit(NULL);
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
	pthread_t tid[40];
	int i=0;
  while (1)
    {
      c = accept (s, (struct sockaddr *) &client, &l);
		if(pthread_create(&tid[i++],NULL, deservire_client, &c)!=0){
			printf("Failed to create thread.\n");
		}
		if(i>=30){
			i=0;
			while(i<30){
				pthread_join(tid[i++],NULL);
      	}
			i=0;
		}
    }
}
