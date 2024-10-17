#include <algorithm>
#include <vector>
#include <iostream>

int main() {
    std::vector<int> numbers = {5, 2, 8, 1, 3};
    std::sort(numbers.begin(), numbers.end());

    
    for (int num : numbers) {
        std::cout << num << " ";
    }
    std::cout << std::endl;


    
    return 0;
}
