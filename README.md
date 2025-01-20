# Market Research Analysis Tool

## Overview
The Market Research Analysis Tool for processing and analyzing customer purchase data collected over time. The program reads data from a CSV file, processes it using Java Streams, and provides insights based on specified queries. This project emphasizes efficient data processing by leveraging Java Streams API.

## Features
You can find the questions the program addresses below:

1. What is the total quantity of products purchased by the customers whose their names start with ’A’?

2. What was the price of most expensive product sold?

3. What was the date of the highest paid purchase by a customer?

4. What was the most popular product before 2000, in terms of total number of purchases whose include that item?

5. What was the least popular product after and including 2000, in terms of total number of items purchased?

6. What was the most popular product among teens considering number of purchases including that item? (Hint: find the product with the smallest average customer age)

7. What was the most inflated product on the data? When calculating only consider the oldest and the newest purchase and not the purchases between. As an exercise try to solve this question by reading the stream only once.

## Input Data Format
The program processes a CSV file with the following structure:

- **Fields**:
  - Customer name (anonymized)
  - Age of the customer on the purchase date
  - Purchase date
  - Base price and quantity purchased for each product:
    - Bread
    - Milk (1 liter)
    - Eggs (1 pack)
    - Potatoes (1 kg)
    - Tomatoes (1 kg)

- **CSV Header**:
  ```
  name,age,date,price of bread,quantity of bought bread,...,price of tomatoes,quantity of bought tomatoes
  ```

- Empty fields indicate no purchase of that product on the respective date.

## Usage
Run the program with the following command-line syntax:

```bash
java YourProgramName <csv_file_name> <question_no>
```

- **csv_file_name**: Path to the CSV file containing the purchase data.
- **question_no**: Query number (1-7) corresponding to the desired analysis.

### Example
To calculate the total quantity of products purchased by customers whose names start with 'A':
```bash
java MarketResearch data.csv 1
```

## Implementation Details
- **Streams**:
  - NOTE TO SELF: Avoid collecting the entire stream into memory; process data lazily to minimize memory usage.

## Assumptions
- Purchase dates range from 1970 to 2020.
- The input file strictly adheres to the specified format.
