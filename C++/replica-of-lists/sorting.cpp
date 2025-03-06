// #include <algorithm>
// #include <vector>
// #include <iostream>


// using namespace std;



// int main() {

//     int n ; // Allocate memory for the array size

//     cout << "Donner la taille du tableau : ";
//     cin >> n;

//     int *A = new int[n]; // Allocate an array of size *n

//     // Additional code for using the dynamic array goes here

//     delete[] A; // Free the allocated memory for the array

// }



#include <iostream>
using namespace std;

struct Element {
    int info;
    Element* next;
};

// Function to insert at the end of a singly linked list
void insertEnd(Element*& head, int value) {
    Element* newNode = new Element;
    newNode->info = value;
    newNode->next = nullptr;

    if (head == nullptr) {
        head = newNode;
    } else {
        Element* current = head;
        while (current->next != nullptr) {
            current = current->next;
        }
        current->next = newNode;
    }
}

// Function to create a list with a given size
void createList(Element*& head, int size) {
    int value;
    for (int i = 0; i < size; i++) {
        cout << "Enter value for element " << (i + 1) << ": ";
        cin >> value;
        insertEnd(head, value);
    }
}

// Function to concatenate two lists (sacrificing D2, making D1 point to D2's elements)
void concatenateWithSacrifice(Element*& D1, Element*& D2) {
    if (D1 == nullptr) {
        D1 = D2;
    } else {
        Element* current = D1;
        while (current->next != nullptr) {
            current = current->next;
        }
        current->next = D2;  // Link end of D1 to start of D2
    }
    D2 = nullptr;  // Clear D2 to show it’s been sacrificed
}

// Function to display a list
void displayList(Element* head) {
    Element* current = head;
    while (current != nullptr) {
        cout << "[" << current->info << "] -> ";
        current = current->next;
    }
    cout << "nullptr" << endl;
}

// Main function to test the concatenate with sacrifice function
int main() {
    int n1, n2;
    Element* D1 = nullptr;
    Element* D2 = nullptr;

    cout << "Enter the number of elements for List 1: ";
    cin >> n1;
    createList(D1, n1);

    cout << "Enter the number of elements for List 2: ";
    cin >> n2;
    createList(D2, n2);

    cout << "\nList 1 before concatenation: ";
    displayList(D1);
    cout << "List 2 before concatenation: ";
    displayList(D2);

    // Concatenate with sacrifice
    concatenateWithSacrifice(D1, D2);

    cout << "\nList 1 after concatenation (sacrificing List 2): ";
    displayList(D1);
    cout << "List 2 after concatenation (should be empty): ";
    displayList(D2);

    return 0;
}
