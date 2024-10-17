// #include <pthread.h>
// #include <stdio.h>
// #include <unistd.h>
//
// void* thread_function(void* arg) {
//     printf("Thread %ld: started\n", pthread_self());
//     sleep(1);
//     printf("Thread %ld: exiting\n", pthread_self());
//     pthread_exit((void*) 42); //
// }
//
// int main() {
//     pthread_t tid;
//     void* status;
//     printf("Main thread %ld: start\n", pthread_self());
//     pthread_create(&tid, NULL, thread_function, NULL);
//     printf("Main thread %ld: created thread %ld\n", pthread_self(), tid);
//     pthread_join(tid, &status);
//     printf("Main thread %ld: thread %ld exited with status %ld\n", pthread_self(), tid, (long) status);
//     printf("Main thread %ld: end\n", pthread_self());
//     return 0;
// }
//
//



#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>
#define BUFFER_SIZE 256

int main(void) {
    pid_t pid_fils;
    int tube[2];
    char bufferR[BUFFER_SIZE], bufferW[BUFFER_SIZE];

    puts("Creation d'un tube");
    if (pipe(tube) != 0) {
        fprintf(stderr, "Erreur dans pipe\n");
        exit(1);
    }

    pid_fils = fork();
    if (pid_fils == -1) {
        fprintf(stderr, "Erreur dans fork\n");
        exit(1);
    }

    if (pid_fils == 0) { /* Processus fils */
        printf("Fermeture sortie dans le fils (pid = %d)\n", getpid());
        close(tube[1]);  // Close the write end of the pipe in the child
        read(tube[0], bufferR, BUFFER_SIZE);  // Read from the pipe
        printf("Le fils (%d) a lu : %s\n", getpid(), bufferR);
        close(tube[0]); // Close the read end after reading
    } else { /* Processus père */
        printf("Fermeture entrée dans le père (pid = %d)\n", getpid());
        close(tube[0]);  // Close the read end of the pipe in the parent
        sprintf(bufferW, "Message du père (%d) au fils", getpid());
        write(tube[1], bufferW, BUFFER_SIZE);  // Write to the pipe
        close(tube[1]);  // Close the pipe's write end after writing
        wait(NULL);  // Wait for child process to finish
    }

    return 0;
}