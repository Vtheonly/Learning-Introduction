#include <iostream>
using namespace std;

// Node structure for a singly linked list
typedef struct Node
{
    int data;          // Data stored in the node
    struct Node *next; // Pointer to the next node
} Node;

// Function to display a linked list
void displayList(Node *head)
{
    while (head != NULL)
    {
        cout << head->data << " --> ";
        head = head->next;
    }
    cout << "\n";
}

// Function to create a singly linked list with 'x' nodes, each containing 'y * i' as data
Node *createLinkedList(int x, int y)
{
    Node *head = NULL;
    Node *current = NULL;
    for (int i = 1; i <= x; i++)
    {
        Node *newNode = new Node;
        newNode->data = i * y;
        newNode->next = NULL;
        if (head == NULL)
        {
            head = newNode;
            current = head;
        }
        else
        {
            current->next = newNode;
            current = current->next;
        }
    }
    return head;
}

// Function to reverse a singly linked list
Node *reverseLinkedList(Node *head)
{
    Node *current = head;
    Node *previous = NULL;
    Node *temp = NULL;

    while (current != NULL)
    {
        temp = current->next;
        current->next = previous;
        previous = current;
        current = temp;
    }
    head = previous;
    return head;
}

// Main function
void HMapper(int n, int m)
{
    Node **arr = new Node *[n];

    // Create and display n linked lists
    for (int i = 0; i < n; i++)
    {
        arr[i] = createLinkedList(m, i + 1);
        displayList(arr[i]);
    }

    cout << "\n --------- \n"
         << endl;

    // Reverse and display the n linked lists
    for (int i = 0; i < n; i++)
    {
        arr[i] = reverseLinkedList(arr[i]);
        displayList(arr[i]);
    }

    // Deallocate memory for the linked lists
    for (int i = 0; i < n; i++)
    {
        delete arr[i];
    }
    delete[] arr;
}

int main()
{
    // HMapper(n, m): n is the number of lists, m is the number of nodes in each list
    HMapper(2, 6);
    return 0;
}
